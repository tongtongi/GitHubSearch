package com.example.reposearchapp.di

import android.app.Application
import android.content.Context
import com.example.reposearchapp.util.ApolloWrapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideApolloClient() = ApolloWrapper()
    }
}