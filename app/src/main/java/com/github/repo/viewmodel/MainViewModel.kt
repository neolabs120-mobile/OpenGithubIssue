package com.github.repo.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.repo.base.BaseKotlinViewModel
import com.github.repo.model.Example
import com.github.repo.retrofit.GitHubService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(private val model: Example): BaseKotlinViewModel(), LifecycleObserver {
    private val issueLiveData = MutableLiveData<List<Example>>()
    val imageSearchResponseLiveData: LiveData<List<Example>> get() = issueLiveData
    var status = MutableLiveData<Boolean?>()

    val service:GitHubService = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubService::class.java)

    @SuppressLint("CheckResult")
    fun getIssue(org: String, repo: String) {
        service.listRepos(org, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //이것저것 받아왔다.
                it.run {
                    issueLiveData.postValue(this)
                }
                Log.d("df", "dfdd")
            }, {
                //실패! 팝업창을 띄운다.
                status.value = true
                Log.d("df", it.message.toString())
            })
    }
}