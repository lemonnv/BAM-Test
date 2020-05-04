package com.vincent.bamtest.domain.usecase

import com.vincent.bamtest.domain.entity.Repository
import com.vincent.bamtest.domain.functional.Interactor
import com.vincent.bamtest.domain.repository.RepoRepository
import org.koin.core.inject

class SetFavoriteRepo: Interactor<Unit, Repository>() {

    private val repository: RepoRepository by inject()

    override suspend fun runOnBackground(params: Repository) {
        repository.setFavoriteRepo(params.id, true)
    }

}

class UnsetFavoriteRepo: Interactor<Unit, Repository>() {

    private val repository: RepoRepository by inject()

    override suspend fun runOnBackground(params: Repository) {
        repository.setFavoriteRepo(params.id, false)
    }
}