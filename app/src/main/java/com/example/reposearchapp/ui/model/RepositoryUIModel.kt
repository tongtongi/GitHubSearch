package com.example.reposearchapp.ui.model

data class RepositoryUIModel(
    val id: String,
    val name: String,
    val description: String,
    val isPrivateRepo: Boolean,
    val forkCount: Int,
    val owner: OwnerUIModel
)
