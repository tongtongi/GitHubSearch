package com.example.reposearchapp.di

import android.app.Application
import com.example.reposearchapp.core.RepoSearchApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        AppModule::class,
        DataModule::class,
        SchedulerModule::class
    ]
)

interface AppComponent : AndroidInjector<RepoSearchApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}