package com.example.reposearchapp.di

import com.example.reposearchapp.util.scheduler.DefaultSchedulerProvider
import com.example.reposearchapp.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SchedulerModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideScheduler(): SchedulerProvider = DefaultSchedulerProvider()
}