package com.example.reposearchapp.data

import com.example.reposearchapp.RepositoryDetailQuery
import com.example.reposearchapp.RepositoryListQuery
import com.example.reposearchapp.data.model.*

fun RepositoryListQuery.Data.toDomainModel() =
    ResponseDomainModel(
        pageInfo = this.search().pageInfo().toDomainModel(),
        repositoryList = this.search().edges()?.map { it.node().toDomainModel() } ?: emptyList()
    )

private fun RepositoryListQuery.Node?.toDomainModel(): RepositoryDomainModel {
    val dataModel = this as RepositoryListQuery.AsRepository
    return RepositoryDomainModel(
        id = dataModel.id(),
        name = dataModel.name(),
        description = dataModel.description(),
        isPrivateRepo = dataModel.isPrivate,
        forkCount = dataModel.forkCount(),
        owner = dataModel.owner().toDomainModel()
    )
}

private fun RepositoryListQuery.Owner.toDomainModel() =
    OwnerDomainModel(
        avatarUrl = this.avatarUrl().toString(),
        name = this.login()
    )

private fun RepositoryListQuery.PageInfo.toDomainModel() =
    PageInfoDomainModel(
        currentEndCursor = this.endCursor(),
        hasNextPage = this.hasNextPage()
    )

fun RepositoryDetailQuery.Data.toDomainModel(): RepositoryDetailDomainModel {
    val watchers = this.repository()?.watchers()

    return RepositoryDetailDomainModel(
        pageInfo = watchers?.pageInfo()?.toDomainModel()
            ?: PageInfoDomainModel(null, false),
        repositorySubscribersCount = watchers?.totalCount() ?: 0,
        repositoryOwnerAvatar = this.repository()?.owner()?.avatarUrl().toString(),
        subscribers = watchers?.nodes()?.map { it.toDomainModel() } ?: emptyList()
    )
}


fun RepositoryDetailQuery.Node.toDomainModel() = SubscriberDomainModel(
    name = this.login(),
    avatarUrl = this.avatarUrl().toString()
)

fun RepositoryDetailQuery.PageInfo.toDomainModel() =
    PageInfoDomainModel(
        currentEndCursor = this.endCursor(),
        hasNextPage = this.hasNextPage()
    )