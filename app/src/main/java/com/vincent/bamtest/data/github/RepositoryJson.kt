package com.vincent.bamtest.data.github

data class RepositoryJson(
    val id: Long,
    val name: String,
    val description: String?,
    val private: Boolean
)