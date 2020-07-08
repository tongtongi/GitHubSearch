package com.example.reposearchapp.data.model

data class ResponseDomainModel(
    val pageInfo: PageInfoDomainModel,
    val repositoryList: List<RepositoryDomainModel>
)