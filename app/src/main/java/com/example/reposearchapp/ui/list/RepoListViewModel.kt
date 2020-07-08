package com.example.reposearchapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reposearchapp.R
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.commons.Status
import com.example.reposearchapp.core.BaseViewModel
import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.data.model.ResponseDomainModel

import com.example.reposearchapp.ui.model.PageInfoUIModel
import com.example.reposearchapp.ui.model.RepositoryUIModel
import com.example.reposearchapp.ui.model.toUIModel
import com.example.reposearchapp.util.scheduler.SchedulerProvider
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
    private val dataSource: DataSource,
    private val scheduler: SchedulerProvider
) : BaseViewModel() {

    private val status = MutableLiveData<Event<Status<List<RepositoryUIModel>, Int>>>()
    fun getStatus(): LiveData<Event<Status<List<RepositoryUIModel>, Int>>> = status

    private val dataList = mutableListOf<RepositoryUIModel>()

    private var currentEndCursor: String? = null
    private var hasNextPage: Boolean = true

    fun sendRequest(searchQuery: String) {
        status.postValue(Event(Status.Progress()))
        if (hasNextPage) {
            dataSource.getRepositoryList(searchQuery, currentEndCursor)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .subscribe({ handleResponse(it) }, { handleError() })
                .autoDispose()
        }
    }

    private fun handleResponse(response: ResponseDomainModel) {
        updateEndCursor(response.pageInfo.toUIModel())

        val repositoryList = response.repositoryList.map { it.toUIModel() }
        dataList.addAll(repositoryList)
        status.postValue(Event(Status.Success(dataList.toList())))
    }

    private fun updateEndCursor(pageInfo: PageInfoUIModel) {
        currentEndCursor = pageInfo.currentEndCursor
        hasNextPage = pageInfo.hasNextPage
    }

    private fun handleError() {
        status.postValue(Event(Status.Error(R.string.something_went_wrong_error)))
    }
}