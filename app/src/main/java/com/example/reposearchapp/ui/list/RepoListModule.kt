package com.example.reposearchapp.ui.list

import androidx.lifecycle.ViewModelProviders
import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.util.createFactory
import com.example.reposearchapp.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class RepoListModule {

    @Provides
    fun provideViewModel(
        activity: RepoListActivity,
        dataSource: DataSource,
        scheduler: SchedulerProvider
    ): RepoListViewModel {
        val factory = createFactory { RepoListViewModel(dataSource, scheduler) }
        return ViewModelProviders.of(activity, factory).get(RepoListViewModel::class.java)
    }
}