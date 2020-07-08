package com.example.reposearchapp.ui.splash

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun provideViewModel(activity: SplashActivity): SplashViewModel =
        ViewModelProviders.of(activity).get(SplashViewModel::class.java)
}