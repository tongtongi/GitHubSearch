package com.example.reposearchapp.data.model

data class RepositoryDetailDomainModel(
    val pageInfo: PageInfoDomainModel,
    val repositorySubscribersCount: Int,
    val repositoryOwnerAvatar: String,
    val subscribers: List<SubscriberDomainModel>
)