package com.example.reposearchapp.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findcar.commons.ItemDecoration
import com.example.reposearchapp.R
import com.example.reposearchapp.commons.Status
import com.example.reposearchapp.databinding.ActivityRepoDetailsBinding
import com.example.reposearchapp.ui.model.RepositoryDetailUIModel
import com.example.reposearchapp.ui.model.SubscriberUIModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_repo_details.*
import javax.inject.Inject

class RepoDetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: RepoDetailsViewModel

    private lateinit var binding: ActivityRepoDetailsBinding

    private val adapter: RepoSubscribersAdapter by lazy { RepoSubscribersAdapter() }

    private val repositoryName: String by lazy {
        intent.extras?.getString(KEY_REPOSITORY_NAME).orEmpty()
    }
    private val repositoryOwner: String by lazy {
        intent.extras?.getString(KEY_REPOSITORY_OWNER).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_details)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.sendRequest(repositoryName, repositoryOwner)

        setActionBarTitle()
        initRecyclerView()
        setLoadMoreButtonClick()
        observeViewModel()
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = repositoryName
    }

    private fun initRecyclerView() {
        binding.recyclerViewSubscribers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSubscribers.adapter = adapter
        binding.recyclerViewSubscribers.addItemDecoration(
            ItemDecoration(resources.getDimensionPixelSize(R.dimen.space_default))
        )
    }

    private fun observeViewModel() {
        viewModel.getStatus().observe(this, Observer {
            it.getContentIfNotHandled()?.let { status ->
                when (status) {
                    is Status.Success -> handleSuccess(status.data)
                    is Status.Error -> handleError(getString(status.error))
                }
            }
        })
    }

    private fun handleSuccess(response: RepositoryDetailUIModel) {
        setRepositoryOwnerAvatar(response.repositoryOwnerAvatar)
        setRepositoryName()
        setRepositorySubscribersCount(response.repositorySubscribersCount)
        setListOfSubscribers(response.subscribers)
    }

    private fun handleError(errorMessage: String) {
        Snackbar.make(constraintLayout, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun setLoadMoreButtonClick() {
        buttonLoadMore.setOnClickListener {
            viewModel.sendRequest(repositoryName, repositoryOwner)
        }
    }

    private fun setRepositoryOwnerAvatar(avatar: String) {
        Picasso.with(this)
            .load(avatar)
            .placeholder(R.drawable.avatar_placeholder)
            .into(imageViewRepoOwnerAvatar)
    }

    private fun setRepositoryName() {
        textViewRepositoryName.text = getString(R.string.repository_detail_name, repositoryName)
    }

    private fun setRepositorySubscribersCount(subscribersCount: Int) {
        textViewSubscribersCount.text = getString(
            R.string.repository_subscriber_count,
            subscribersCount
        )
    }

    private fun setListOfSubscribers(subscribersList: List<SubscriberUIModel>) {
        adapter.submitList(subscribersList)
    }

    companion object {
        private const val KEY_REPOSITORY_NAME = "KEY_REPOSITORY_NAME"
        private const val KEY_REPOSITORY_OWNER = "KEY_REPOSITORY_OWNER"

        fun newIntent(activity: Activity, repositoryName: String, repositoryOwner: String): Intent {
            val intent = Intent(activity, RepoDetailsActivity::class.java)
            intent.putExtra(KEY_REPOSITORY_NAME, repositoryName)
            intent.putExtra(KEY_REPOSITORY_OWNER, repositoryOwner)
            return intent
        }
    }
}