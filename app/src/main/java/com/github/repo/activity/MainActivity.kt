package com.github.repo.activity

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.repo.CustomDialog
import com.github.repo.viewmodel.MainViewModel
import com.github.repo.R
import com.github.repo.RecyclerViewAdapter
import com.github.repo.base.BaseKotlinActivity
import com.github.repo.databinding.ActivityMainBinding
import com.github.repo.model.Example
import com.github.repo.model.IssueUser
import com.github.repo.model.LayoutData
import com.github.repo.retrofit.GetGihubIssueCallback

class MainActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>(), GetGihubIssueCallback {
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel = MainViewModel(Example())
    val mDatas = mutableListOf<LayoutData>()

    private val adapter : RecyclerViewAdapter = RecyclerViewAdapter(this, mDatas)

    companion object {
        var arraylist : ArrayList<IssueUser> = ArrayList<IssueUser>()
    }

    override fun initStartView() {
        viewDataBinding.recyclerView.adapter = adapter
        viewDataBinding.recyclerView.setHasFixedSize(true)
        val lm = LinearLayoutManager(this)
        viewDataBinding.recyclerView.layoutManager = lm
    }

    override fun initDataBinding() {
        viewModel.getIssue("google", "dagger")

        viewModel.imageSearchResponseLiveData.observe(this, Observer {
            Log.d("neoneo", it.toString())
            mDatas.clear()
            for(i in 0 until it.size) {
                Log.d("data", it.get(i).number + ": " + it.get(i).title)
                Log.d("logloglog", it.get(i).body.toString())
                arraylist.add(IssueUser(it.get(i).number!!, it.get(i).body, it.get(i).url!!))
                mDatas.add(LayoutData("#" + it.get(i).number + ": " + it.get(i).title))
            }
            adapter.notifyDataSetChanged()
        })

        viewModel.status.observe(this, Observer{
            viewModel.status.value = null
            Toast.makeText(this, "Fetch Failed", Toast.LENGTH_LONG).show()
        })
    }

    override fun initAfterBinding() {
        viewDataBinding.choose.setOnClickListener {
            val customDialog = CustomDialog(this)
            customDialog.callFunction()
        }
    }

    override fun callback(org: String, repo: String) {
        viewModel.getIssue(org, repo)
        viewDataBinding.choose.text = org + " / " + repo
    }
}
