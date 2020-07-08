package com.example.reposearchapp.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.reposearchapp.ui.model.RepositoryUIModel

class RepoListAdapter(
    private val itemClickListener: (RepositoryUIModel) -> Unit
) : ListAdapter<RepositoryUIModel, RepoListViewHolder>(RepositoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoListViewHolder(itemClickListener, parent)

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) =
        holder.bind(getItem(position))
}

private class RepositoryDiffCallback : DiffUtil.ItemCallback<RepositoryUIModel>() {
    override fun areItemsTheSame(oldItem: RepositoryUIModel, newItem: RepositoryUIModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RepositoryUIModel, newItem: RepositoryUIModel) =
        oldItem == newItem
}