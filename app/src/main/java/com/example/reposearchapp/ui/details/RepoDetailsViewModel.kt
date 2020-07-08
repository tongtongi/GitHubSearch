package com.example.reposearchapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reposearchapp.R
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.commons.Status
import com.example.reposearchapp.core.BaseViewModel
import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.data.model.RepositoryDetailDomainModel
import com.example.reposearchapp.ui.model.PageInfoUIModel
import com.example.reposearchapp.ui.model.RepositoryDetailUIModel
import com.example.reposearchapp.ui.model.SubscriberUIModel
import com.example.reposearchapp.ui.model.toUIModel
import com.example.reposearchapp.util.scheduler.SchedulerProvider
import javax.inject.Inject

class RepoDetailsViewModel @Inject constructor(
    private val dataSource: DataSource,
    private val scheduler: SchedulerProvider
) : BaseViewModel() {

    private val status = MutableLiveData<Event<Status<RepositoryDetailUIModel, Int>>>()
    fun getStatus(): LiveData<Event<Status<RepositoryDetailUIModel, Int>>> = status

    private var subscribersList = listOf<SubscriberUIModel>()

    private var currentEndCursor: String? = null
    private var hasNextPage: Boolean = true

    fun sendRequest(name: String, owner: String) {
        if (hasNextPage) {
            dataSource.getRepositoryDetail(name, owner, currentEndCursor)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .subscribe({ handleResponse(it) }, { handleError() })
                .autoDispose()
        }
    }

    private fun handleResponse(response: RepositoryDetailDomainModel) {
        val repositoryDetail = response.toUIModel(subscribersList)
        updateEndCursor(repositoryDetail.pageInfo)
        subscribersList = repositoryDetail.subscribers
        status.postValue(Event(Status.Success(repositoryDetail)))
    }

    private fun updateEndCursor(pageInfo: PageInfoUIModel) {
        currentEndCursor = pageInfo.currentEndCursor
        hasNextPage = pageInfo.hasNextPage
    }

    private fun handleError() {
        status.postValue(Event(Status.Error(R.string.something_went_wrong_error)))
    }
}