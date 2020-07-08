package com.example.reposearchapp.data.model

data class RepositoryDomainModel(
    val id: String,
    val name: String,
    val description: String?,
    val isPrivateRepo: Boolean,
    val forkCount: Int,
    val owner: OwnerDomainModel
)