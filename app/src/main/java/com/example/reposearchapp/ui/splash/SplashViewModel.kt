package com.example.reposearchapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.core.BaseViewModel

class SplashViewModel : BaseViewModel() {

    private val navigateToSearchRepo = MutableLiveData<Event<Boolean>>()
    fun getNavigateToSearchRepo(): LiveData<Event<Boolean>> = navigateToSearchRepo

    fun onSplashAnimationEnd() {
        navigateToSearchRepo.value = Event(true)
    }
}