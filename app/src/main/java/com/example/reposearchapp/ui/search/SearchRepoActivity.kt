package com.example.reposearchapp.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.ActivitySearchRepoBinding
import com.example.reposearchapp.ui.list.RepoListActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_search_repo.*
import javax.inject.Inject

class SearchRepoActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: SearchRepoViewModel

    private lateinit var binding: ActivitySearchRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_repo)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setActionBarTitle()
        observeViewModel()
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = getString(R.string.search_repo_activity_action_bar_title)
    }

    private fun observeViewModel() {
        viewModel.getNavigateToRepoList().observe(this, Observer {
            it.getContentIfNotHandled()?.let { navigationEvent ->
                // Only proceed if the event has never been handled
                if (navigationEvent) {
                    navigateToRepoListScreen(viewModel.searchQuery.value.toString())
                } else {
                    showWarning()
                }
            }
        })
    }

    private fun navigateToRepoListScreen(searchQuery: String) {
        startActivity(RepoListActivity.newIntent(this, searchQuery))
    }

    private fun showWarning() {
        Snackbar.make(
            constraintLayout,
            getString(R.string.search_query_warning),
            Snackbar.LENGTH_LONG
        ).show()
    }

    companion object {
        fun newIntent(activity: Activity) = Intent(activity, SearchRepoActivity::class.java)
    }
}
