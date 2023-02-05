package com.xyz.oclock.core.network.di

import com.xyz.oclock.core.database.SharedPreferences
import com.xyz.oclock.core.network.model.response.OClockResponse
import com.xyz.oclock.core.network.model.response.TokenResponse
import com.xyz.oclock.core.network.service.CommonService
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val sp: SharedPreferences
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            sp.getRefreshToken()
        }
        return runBlocking {
            val newToken = getNewToken("Bearer $token")
            newToken.body()?.let {
                val accessToken = it.data?.accessToken
                val refreshToken = it.data?.refreshToken
                sp.setAccessToken(accessToken?: "")
                sp.setRefreshToken(refreshToken?: "")
                response.request.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): retrofit2.Response<OClockResponse<TokenResponse>> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkModule.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(NetworkModule.moshi))
            .client(okHttpClient)
            .build()
        val service = retrofit.create(CommonService::class.java)
        return service.getNewToken(refreshToken)
    }
}