package com.example.reposearchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.commons.Status
import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.data.model.PageInfoDomainModel
import com.example.reposearchapp.data.model.RepositoryDetailDomainModel
import com.example.reposearchapp.data.model.SubscriberDomainModel
import com.example.reposearchapp.ui.details.RepoDetailsViewModel
import com.example.reposearchapp.ui.model.PageInfoUIModel
import com.example.reposearchapp.ui.model.RepositoryDetailUIModel
import com.example.reposearchapp.ui.model.SubscriberUIModel
import com.example.reposearchapp.util.scheduler.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

private const val CURRENT_END_CURSOR = "currentEndCursor"
private const val HAS_NEXT_PAGE = false
private const val REPO_NAME = "repoName"
private const val SUBSCRIBER_NAME = "subscriberName"
private const val SUBSCRIBER_AVATAR_URL = "subscriberAvatarUrl"
private const val OWNER_NAME = "ownerName"
private const val OWNER_AVATAR_URL = "ownerAvatarUrl"
private const val REPOSITORY_SUBSCRIBERS_COUNT = 1

@RunWith(MockitoJUnitRunner::class)
class RepoDetailsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataSource: DataSource

    private lateinit var viewModel: RepoDetailsViewModel

    private var lastStatus: Event<Status<RepositoryDetailUIModel, Int>>? = null

    @Before
    fun setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        viewModel = RepoDetailsViewModel(dataSource, TestSchedulerProvider())

        viewModel.getStatus().observeForever {
            lastStatus = it
        }
    }

    @Test
    fun `should set status to Success if request is successful`() {
        whenever(dataSource.getRepositoryDetail(REPO_NAME, OWNER_NAME, null)).thenReturn(
            Observable.just(getRepositoryDetailDomainModel())
        )

        // WHEN
        viewModel.sendRequest(name = REPO_NAME, owner = OWNER_NAME)

        // THEN
        Assert.assertEquals(
            (viewModel.getStatus().value?.peekContent() as Status.Success).data,
            getExpectedRepositoryDetailUIModel()
        )
    }

    @Test
    fun `should set status to ERROR if request fails`() {
        // GIVEN
        whenever(dataSource.getRepositoryDetail(REPO_NAME, OWNER_NAME, null)).thenReturn(
            Observable.error(RuntimeException())
        )

        // WHEN
        viewModel.sendRequest(name = REPO_NAME, owner = OWNER_NAME)

        // THEN
        Assert.assertEquals(
            (viewModel.getStatus().value?.peekContent() as Status.Error).error,
            R.string.something_went_wrong_error
        )
    }

    private fun getRepositoryDetailDomainModel() = RepositoryDetailDomainModel(
        pageInfo = PageInfoDomainModel(
            currentEndCursor = CURRENT_END_CURSOR,
            hasNextPage = HAS_NEXT_PAGE
        ),
        repositorySubscribersCount = REPOSITORY_SUBSCRIBERS_COUNT,
        repositoryOwnerAvatar = OWNER_AVATAR_URL,
        subscribers = listOf(
            SubscriberDomainModel(
                name = SUBSCRIBER_NAME,
                avatarUrl = SUBSCRIBER_AVATAR_URL
            )
        )
    )

    private fun getExpectedRepositoryDetailUIModel() = RepositoryDetailUIModel(
        pageInfo = PageInfoUIModel(
            currentEndCursor = CURRENT_END_CURSOR,
            hasNextPage = HAS_NEXT_PAGE
        ),
        repositorySubscribersCount = REPOSITORY_SUBSCRIBERS_COUNT,
        repositoryOwnerAvatar = OWNER_AVATAR_URL,
        subscribers = listOf(
            SubscriberUIModel(
                name = SUBSCRIBER_NAME,
                avatarUrl = SUBSCRIBER_AVATAR_URL
            )
        )
    )
}