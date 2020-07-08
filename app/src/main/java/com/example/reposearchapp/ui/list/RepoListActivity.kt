package com.example.reposearchapp.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findcar.commons.ItemDecoration
import com.example.reposearchapp.R
import com.example.reposearchapp.commons.Status
import com.example.reposearchapp.databinding.ActivityRepoListBinding
import com.example.reposearchapp.ui.details.RepoDetailsActivity
import com.example.reposearchapp.ui.model.RepositoryUIModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_repo_list.*
import javax.inject.Inject

class RepoListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: RepoListViewModel

    private lateinit var binding: ActivityRepoListBinding
    private val adapter: RepoListAdapter by lazy { RepoListAdapter(::handleListItemClick) }

    private val searchQuery: String by lazy { intent.extras?.getString(SEARCH_QUERY).orEmpty() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.sendRequest(searchQuery)

        setActionBarTitle()
        initRecyclerView()
        setLoadMoreButtonClick()
        observeViewModel()
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = getString(R.string.repo_list_activity_action_bar_title)
    }

    private fun initRecyclerView() {
        binding.recyclerViewRepoList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRepoList.adapter = adapter
        binding.recyclerViewRepoList.addItemDecoration(
            ItemDecoration(resources.getDimensionPixelSize(R.dimen.space_default))
        )
    }

    private fun observeViewModel() {
        viewModel.getStatus().observe(this, Observer {
            it.getContentIfNotHandled()?.let { status ->
                when (status) {
                    is Status.Progress -> handleProgress()
                    is Status.Success -> handleSuccess(status.data)
                    is Status.Error -> handleError(getString(status.error))
                }
            }
        })
    }

    private fun handleProgress() {
        showLoading()
    }

    private fun handleSuccess(response: List<RepositoryUIModel>) {
        hideLoading()
        setListOfRepositories(response)
    }

    private fun handleError(errorMessage: String) {
        Snackbar.make(constraintLayout, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun setLoadMoreButtonClick() {
        buttonLoadMore.setOnClickListener {
            viewModel.sendRequest(searchQuery)
        }
    }

    private fun setListOfRepositories(repositoryList: List<RepositoryUIModel>) {
        adapter.submitList(repositoryList)
    }

    private fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
        binding.recyclerViewRepoList.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.loadingView.visibility = View.GONE
        binding.recyclerViewRepoList.visibility = View.VISIBLE
    }

    private fun handleListItemClick(repository: RepositoryUIModel) {
        startActivity(
            RepoDetailsActivity.newIntent(this, repository.name, repository.owner.name)
        )
    }

    companion object {
        private const val SEARCH_QUERY = "SEARCH_QUERY"

        fun newIntent(activity: Activity, searchQuery: String): Intent {
            val intent = Intent(activity, RepoListActivity::class.java)
            intent.putExtra(SEARCH_QUERY, searchQuery)

            return intent
        }
    }
}