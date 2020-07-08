package com.example.reposearchapp.ui.search

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class SearchRepoModule {

    @Provides
    fun provideViewModel(activity: SearchRepoActivity): SearchRepoViewModel =
        ViewModelProviders.of(activity).get(SearchRepoViewModel::class.java)
}