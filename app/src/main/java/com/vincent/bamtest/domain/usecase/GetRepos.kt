package com.vincent.bamtest.domain.usecase

import com.vincent.bamtest.domain.entity.Repository
import com.vincent.bamtest.domain.functional.Interactor
import com.vincent.bamtest.domain.repository.RepoRepository
import org.koin.core.inject

class GetRepos: Interactor<List<Repository>, Int>() {

    private val repository: RepoRepository by inject()

    override suspend fun runOnBackground(params: Int): List<Repository> = repository.getRepos(params)


}