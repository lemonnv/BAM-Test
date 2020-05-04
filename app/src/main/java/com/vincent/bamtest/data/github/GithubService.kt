package com.vincent.bamtest.data.github

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("repos")
    fun getRepos(@Query("page") page: Int): Call<List<RepositoryJson>>

}