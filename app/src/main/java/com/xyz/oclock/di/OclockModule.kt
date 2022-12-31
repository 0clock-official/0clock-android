package com.xyz.oclock.di

import android.content.Context
import com.xyz.oclock.OClockApplication
import com.xyz.oclock.common.utils.LogoutHelper
import com.xyz.oclock.common.utils.ResourceProvider
import com.xyz.oclock.core.data.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OclockModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : OClockApplication {
        return app as OClockApplication
    }

//    @Singleton
//    @Provides
//    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences {
//        return SharedPreferences(app)
//    }

    @Singleton
    @Provides
    fun provideResourceProvider(@ApplicationContext app: Context): ResourceProvider {
        return ResourceProvider(app)
    }

    @Singleton
    @Provides
    fun provideLogoutHelper(
        @ApplicationContext app: Context,
        tokenRepository: TokenRepository
    ) : LogoutHelper {
        return LogoutHelper(tokenRepository, app)
    }
}
