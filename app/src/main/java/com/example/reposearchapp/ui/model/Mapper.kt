package com.example.reposearchapp.ui.model

import com.example.reposearchapp.data.model.*

fun PageInfoDomainModel.toUIModel() =
    PageInfoUIModel(
        currentEndCursor = this.currentEndCursor,
        hasNextPage = this.hasNextPage
    )

fun RepositoryDomainModel.toUIModel() =
    RepositoryUIModel(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        isPrivateRepo = this.isPrivateRepo,
        forkCount = this.forkCount,
        owner = this.owner.toUIModel()
    )

private fun OwnerDomainModel.toUIModel() =
    OwnerUIModel(
        avatarUrl = this.avatarUrl,
        name = this.name
    )

fun RepositoryDetailDomainModel.toUIModel(currentSubscribers: List<SubscriberUIModel>) =
    RepositoryDetailUIModel(
        pageInfo = this.pageInfo.toUIModel(),
        repositorySubscribersCount = this.repositorySubscribersCount,
        repositoryOwnerAvatar = this.repositoryOwnerAvatar,
        subscribers = this.subscribers.map { it.toUIModel() }.combineLists(currentSubscribers)
    )

fun SubscriberDomainModel.toUIModel() =
    SubscriberUIModel(
        name = this.name,
        avatarUrl = this.avatarUrl
    )

private fun List<SubscriberUIModel>.combineLists(currentSubscribers: List<SubscriberUIModel>): List<SubscriberUIModel> {
    val list = mutableListOf<SubscriberUIModel>()
    list.addAll(currentSubscribers)
    list.addAll(this)
    return list.toList()
}


