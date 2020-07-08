package com.example.reposearchapp.ui.details

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reposearchapp.R
import com.example.reposearchapp.ui.model.SubscriberUIModel
import com.example.reposearchapp.util.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_repo_details.view.*

class RepoSubscribersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.list_item_repo_details)
) {
    private val textViewSubscriberName: AppCompatTextView by lazy { itemView.textViewRepoSubscriberName }
    private val imageViewSubscriberAvatarImage: AppCompatImageView by lazy { itemView.imageViewRepoSubscriberAvatar }

    private lateinit var item: SubscriberUIModel

    fun bind(item: SubscriberUIModel) {
        this.item = item
        textViewSubscriberName.text = item.name
        Picasso.with(imageViewSubscriberAvatarImage.context)
            .load(item.avatarUrl)
            .placeholder(R.drawable.avatar_placeholder)
            .into(imageViewSubscriberAvatarImage)
    }
}