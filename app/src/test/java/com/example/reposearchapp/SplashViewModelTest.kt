package com.example.reposearchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.reposearchapp.commons.Event
import com.example.reposearchapp.ui.splash.SplashViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        viewModel = SplashViewModel()
    }

    @Test
    fun `should navigate to search repository screen when splash animation ends`() {
        // WHEN
        viewModel.onSplashAnimationEnd()

        // THEN
        assertEquals(
            Event(true).peekContent(),
            viewModel.getNavigateToSearchRepo().value?.peekContent()
        )
    }
}