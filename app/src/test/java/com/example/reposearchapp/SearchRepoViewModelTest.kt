package com.example.reposearchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.ui.search.SearchRepoViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchRepoViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchRepoViewModel

    @Before
    fun setUp() {
        viewModel = SearchRepoViewModel()
    }

    @Test
    fun `should navigate to repository list screen when search query is provided`() {
        // GIVEN
        viewModel.searchQuery.value = "tongtongi in:name"

        // WHEN
        viewModel.onFindButtonClicked()

        // THEN
        assertEquals(
            Event(true).peekContent(),
            viewModel.getNavigateToRepoList().value?.peekContent()
        )
    }

    @Test
    fun `should show warning when search query is not provided`() {
        // GIVEN
        viewModel.searchQuery.value = null

        // WHEN
        viewModel.onFindButtonClicked()

        // THEN
        assertEquals(
            Event(false).peekContent(),
            viewModel.getNavigateToRepoList().value?.peekContent()
        )
    }
}