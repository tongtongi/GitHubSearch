package com.example.reposearchapp.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.ActivitySplashBinding
import com.example.reposearchapp.ui.search.SearchRepoActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

private const val ANIMATION_DURATION = 2000L //ms

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: SplashViewModel

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        hideToolbar()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.onSplashAnimationEnd() }, ANIMATION_DURATION)
    }

    private fun hideToolbar() {
        supportActionBar?.hide()
    }

    private fun observeViewModel() {
        viewModel.getNavigateToSearchRepo().observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                // Only proceed if the event has never been handled
                startActivity(SearchRepoActivity.newIntent(this))
                finish()
            }
        })
    }
}