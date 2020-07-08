package com.example.reposearchapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.core.BaseViewModel

class SearchRepoViewModel : BaseViewModel() {

    val searchQuery = MutableLiveData<String?>()

    private val navigateToRepoList = MutableLiveData<Event<Boolean>>()
    fun getNavigateToRepoList(): LiveData<Event<Boolean>> = navigateToRepoList

    fun onFindButtonClicked() {
        if (searchQuery.value.isNullOrEmpty()) {
            navigateToRepoList.value = Event(false)
        } else {
            navigateToRepoList.value = Event(true)
        }
    }
}