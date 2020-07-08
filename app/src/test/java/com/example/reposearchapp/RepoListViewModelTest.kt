package com.example.reposearchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.commons.Status
import com.example.reposearchapp.data.DataSource
import com.example.reposearchapp.data.model.OwnerDomainModel
import com.example.reposearchapp.data.model.PageInfoDomainModel
import com.example.reposearchapp.data.model.RepositoryDomainModel
import com.example.reposearchapp.data.model.ResponseDomainModel
import com.example.reposearchapp.ui.list.RepoListViewModel
import com.example.reposearchapp.ui.model.OwnerUIModel
import com.example.reposearchapp.ui.model.RepositoryUIModel
import com.example.reposearchapp.util.scheduler.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
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
private const val ID = "id"
private const val REPO_NAME = "repoName"
private const val DESCRIPTION = "description"
private const val IS_PRIVATE_REPO = false
private const val FORK_COUNT = 10
private const val OWNER_NAME = "ownerName"
private const val OWNER_AVATAR_URL = "ownerAvatarUrl"

@RunWith(MockitoJUnitRunner::class)
class RepoListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataSource: DataSource

    private lateinit var viewModel: RepoListViewModel

    private var lastStatus: Event<Status<List<RepositoryUIModel>, Int>>? = null

    @Before
    fun setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        viewModel = RepoListViewModel(dataSource, TestSchedulerProvider())

        viewModel.getStatus().observeForever {
            lastStatus = it
        }
    }

    @Test
    fun `should set status to Success if request is successful`() {
        whenever(dataSource.getRepositoryList(eq(any()), null)).thenReturn(
            Observable.just(getResponseDomainModel())
        )

        // WHEN
        viewModel.sendRequest(searchQuery = "repo in:name")

        // THEN
        Assert.assertEquals(
            (viewModel.getStatus().value?.peekContent() as Status.Success).data,
            getExpectedRepositoryList()
        )
    }

    @Test
    fun `should set status to ERROR if request fails`() {
        // GIVEN
        whenever(dataSource.getRepositoryList(eq(any()), null)).thenReturn(
            Observable.error(
                RuntimeException()
            )
        )

        // WHEN
        viewModel.sendRequest(searchQuery = "tongtongi in:name")

        // THEN
        Assert.assertEquals(
            (viewModel.getStatus().value?.peekContent() as Status.Error).error,
            R.string.something_went_wrong_error
        )
    }

    private fun getResponseDomainModel() = ResponseDomainModel(
        pageInfo = PageInfoDomainModel(
            currentEndCursor = CURRENT_END_CURSOR,
            hasNextPage = HAS_NEXT_PAGE
        ),
        repositoryList = listOf(
            RepositoryDomainModel(
                id = ID,
                name = REPO_NAME,
                description = DESCRIPTION,
                isPrivateRepo = IS_PRIVATE_REPO,
                forkCount = FORK_COUNT,
                owner = OwnerDomainModel(
                    name = OWNER_NAME,
                    avatarUrl = OWNER_AVATAR_URL
                )
            )
        )
    )

    private fun getExpectedRepositoryList() = listOf(
        RepositoryUIModel(
            id = ID,
            name = REPO_NAME,
            description = DESCRIPTION,
            isPrivateRepo = IS_PRIVATE_REPO,
            forkCount = FORK_COUNT,
            owner = OwnerUIModel(
                name = OWNER_NAME,
                avatarUrl = OWNER_AVATAR_URL
            )
        )
    )
}