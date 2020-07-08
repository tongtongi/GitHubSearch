package com.example.reposearchapp.ui.details

import androidx.lifecycle.ViewModelProviders
import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.util.createFactory
import com.example.reposearchapp.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class RepoDetailsModule {

    @Provides
    fun provideViewModel(
        activity: RepoDetailsActivity,
        dataSource: DataSource,
        scheduler: SchedulerProvider
    ): RepoDetailsViewModel {
        val factory = createFactory { RepoDetailsViewModel(dataSource, scheduler) }
        return ViewModelProviders.of(activity, factory).get(RepoDetailsViewModel::class.java)
    }
}