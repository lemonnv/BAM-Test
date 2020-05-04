package com.vincent.bamtest.domain.entity

data class Repository(
    val id: Long,
    val name: String,
    val description: String,
    val isFavorite: Boolean
)