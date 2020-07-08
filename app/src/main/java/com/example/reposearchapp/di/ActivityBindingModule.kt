package com.example.reposearchapp.di

import com.example.reposearchapp.ui.details.RepoDetailsActivity
import com.example.reposearchapp.ui.details.RepoDetailsModule
import com.example.reposearchapp.ui.list.RepoListActivity
import com.example.reposearchapp.ui.list.RepoListModule
import com.example.reposearchapp.ui.search.SearchRepoActivity
import com.example.reposearchapp.ui.search.SearchRepoModule
import com.example.reposearchapp.ui.splash.SplashActivity
import com.example.reposearchapp.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun splashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchRepoModule::class])
    abstract fun searchRepoActivity(): SearchRepoActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [RepoListModule::class])
    abstract fun repoListActivity(): RepoListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [RepoDetailsModule::class])
    abstract fun repoDetailsActivity(): RepoDetailsActivity

}