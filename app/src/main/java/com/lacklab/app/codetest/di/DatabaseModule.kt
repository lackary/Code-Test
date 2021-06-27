package com.lacklab.app.codetest.di

import android.content.Context
import com.lacklab.app.codetest.dao.AppDatabase
import com.lacklab.app.codetest.dao.PassDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePassDao(appDatabase: AppDatabase): PassDao {
        return appDatabase.passDao()
    }

}