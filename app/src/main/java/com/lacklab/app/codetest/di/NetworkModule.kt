package com.lacklab.app.codetest.di

import com.lacklab.app.codetest.MainApplication
import com.lacklab.app.codetest.MainApplication_HiltComponents
import com.lacklab.app.codetest.api.MigoincService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providePublicService(): MigoincService {
//        val url = if (MainApplication.connectType == "MOBILE") {
//            "https://code-test.migoinc-dev.com/"
//        } else {
//            "https://192.168.2.2/"
//        }
        val url = "https://code-test.migoinc-dev.com/"
        return MigoincService.create(url)
    }

    fun providePrivateService(): MigoincService {
        val url = "https://192.168.2.2/"
        return MigoincService.create(url)
    }
}