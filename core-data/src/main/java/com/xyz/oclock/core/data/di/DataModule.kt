package com.xyz.oclock.core.data.di

import com.xyz.oclock.core.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsSignUpRepository(
        signUpRepositoryImpl: SignUpRepositoryImpl
    ): SignUpRepository

    @Binds
    fun bindsLocalRepository(
        localRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    fun bindsDeviceStateRepository(
        deviceStateRepositoryImpl: DeviceStateRepositoryImpl
    ): DeviceStateRepository

}
