package com.vincent.bamtest.domain.repository

import com.vincent.bamtest.domain.entity.Repository

interface RepoRepository {

    fun getRepos(page: Int): List<Repository>

    fun setFavoriteRepo(id: Long, enabled: Boolean)

}