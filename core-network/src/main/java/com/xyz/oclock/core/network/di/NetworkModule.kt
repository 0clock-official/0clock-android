package com.xyz.oclock.core.network.di

import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.xyz.oclock.core.network.model.interceptor.HttpRequestInterceptor
import com.xyz.oclock.core.network.service.SignUpClient
import com.xyz.oclock.core.network.service.SignUpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.0clock.xyz/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpClient(signUpService: SignUpService): SignUpClient {
        return SignUpClient(signUpService)
    }
}