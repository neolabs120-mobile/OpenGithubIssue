package com.github.repo.retrofit

import com.github.repo.model.Example
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("/repos/{org}/{repo}/issues")
    fun listRepos(@Path("org") org:String, @Path("repo") repo:String): Single<List<Example>>
}

interface GetGihubIssueCallback {
    fun callback(org: String, repo: String)
}