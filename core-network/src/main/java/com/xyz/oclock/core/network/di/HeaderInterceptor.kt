package com.xyz.oclock.core.network.di

import com.xyz.oclock.core.database.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val sf: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val auth = sf.getAccessToken()
        auth?.let {
            builder.addHeader("Authorization", auth)
        }

        return chain.proceed(builder.build())
    }
}

class ResponseInterceptor(val sf: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            400 -> {
                // todo Control Error
            }
            401 -> {
                // todo Control Error
            }
            402 -> {
                // todo Control Error
            }
        }
        return response
    }
}