package com.example.reposearchapp.ui.list

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reposearchapp.R
import com.example.reposearchapp.ui.model.RepositoryUIModel
import com.example.reposearchapp.util.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_repository.view.*

class RepoListViewHolder(
    private val onItemClicked: (RepositoryUIModel) -> Unit,
    parent: ViewGroup
) : RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_repository)) {

    private val textViewRepositoryName: AppCompatTextView by lazy { itemView.textViewRepoName }
    private val textViewRepositoryDescription: AppCompatTextView by lazy { itemView.textViewRepoDescription }
    private val textViewRepositoryForkCount: AppCompatTextView by lazy { itemView.textViewRepoForkCount }
    private val imageViewRepositoryAvatarImage: AppCompatImageView by lazy { itemView.imageViewRepoAvatar }

    private lateinit var item: RepositoryUIModel

    init {
        itemView.setOnClickListener { onItemClicked(item) }
    }

    fun bind(item: RepositoryUIModel) {
        this.item = item

        textViewRepositoryName.text =
            textViewRepositoryName.context.getString(R.string.repository_name, item.name)
        textViewRepositoryDescription.text = textViewRepositoryDescription.context.getString(
            R.string.repository_description,
            item.description
        )
        textViewRepositoryForkCount.text = textViewRepositoryForkCount.context.getString(
            R.string.repository_fork_count,
            item.forkCount.toString()
        )
        Picasso.with(imageViewRepositoryAvatarImage.context)
            .load(item.owner.avatarUrl)
            .placeholder(R.drawable.avatar_placeholder)
            .into(imageViewRepositoryAvatarImage)
    }
}