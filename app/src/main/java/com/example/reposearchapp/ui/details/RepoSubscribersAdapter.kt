package com.example.reposearchapp.ui.details

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.reposearchapp.ui.model.SubscriberUIModel

class RepoSubscribersAdapter :
    ListAdapter<SubscriberUIModel, RepoSubscribersViewHolder>(SubscribersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoSubscribersViewHolder(parent)

    override fun onBindViewHolder(holder: RepoSubscribersViewHolder, position: Int) =
        holder.bind(getItem(position))
}

private class SubscribersDiffCallback : DiffUtil.ItemCallback<SubscriberUIModel>() {
    override fun areItemsTheSame(oldItem: SubscriberUIModel, newItem: SubscriberUIModel) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: SubscriberUIModel, newItem: SubscriberUIModel) =
        oldItem == newItem
}