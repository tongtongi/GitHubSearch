package com.example.reposearchapp.ui.model

data class RepositoryDetailUIModel(
    val pageInfo: PageInfoUIModel,
    val repositorySubscribersCount: Int,
    val repositoryOwnerAvatar: String,
    val subscribers: List<SubscriberUIModel>
)