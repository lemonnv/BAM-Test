package com.vincent.bamtest.data

import com.vincent.bamtest.data.db.room.RepositoryDao
import com.vincent.bamtest.data.github.GithubService
import com.vincent.bamtest.data.github.RepositoryJson
import com.vincent.bamtest.domain.entity.Repository
import com.vincent.bamtest.domain.repository.RepoRepository
import com.vincent.bamtest.data.db.room.Repository as RepositoryRoom

class RepoRepositoryImpl(private val remoteService: GithubService, private val localService: RepositoryDao):
    RepoRepository {

    override fun getRepos(page: Int): List<Repository> {
        val saved = localService.getRepositories()
        return remoteService.getRepos(page).execute().body()!!.filter { !it.private }.map { json ->
            json.toEntity(saved.find { it.id == json.id } != null) }
    }

    override fun setFavoriteRepo(id: Long, enabled: Boolean) {
        val entityRoom = RepositoryRoom(id)
        if (!enabled) localService.deleteRepository(entityRoom)
        else localService.addRepository(entityRoom)
    }
}

private fun RepositoryJson.toEntity(isFavorite: Boolean) = Repository(
    this.id,
    this.name,
    this.description ?: "",
    isFavorite
)