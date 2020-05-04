package com.vincent.bamtest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vincent.bamtest.domain.entity.Repository
import com.vincent.bamtest.domain.usecase.GetRepos
import com.vincent.bamtest.domain.usecase.SetFavoriteRepo
import com.vincent.bamtest.domain.usecase.UnsetFavoriteRepo

abstract class BaseViewModel: ViewModel() {

    enum class State { UNINITIALIZED, LOADING, IDLE, ERROR }

    val state: MutableLiveData<State> = MutableLiveData(State.UNINITIALIZED)

}

class MainViewModel: BaseViewModel() {

    private val getRepos = GetRepos()
    private val setFavorite = SetFavoriteRepo()
    private val unsetFavorite = UnsetFavoriteRepo()
    private var currentPage: Int = 0

    val repositories = MutableLiveData<List<Repository>>()

    fun loadRepositories() {
        state.value = State.LOADING
        getRepos.execute(1) {
            when {
                it.isSuccess -> {
                    state.value = State.IDLE
                    repositories.value = it.getOrNull() ?: listOf()
                }
                it.isFailure -> {
                    state.value = State.ERROR
                }
            }
        }
    }

    fun pin(repository: Repository, enable: Boolean) {
        if (enable)
            setFavorite.execute(repository) {
                when {
                    it.isSuccess -> {
                        loadRepositories()
                    }
                    it.isFailure -> {
                        state.value = State.ERROR
                    }
                }
        }
        else unsetFavorite.execute(repository) {
            when {
                it.isSuccess -> {
                    loadRepositories()
                }
                it.isFailure -> {
                    state.value = State.ERROR
                }
            }
        }
    }

}