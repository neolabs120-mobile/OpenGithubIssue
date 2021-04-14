package com.github.repo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.repo.R
import com.github.repo.databinding.ActivityDetailBinding
import com.github.repo.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent : Intent = intent
        val viewDataBinding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewDataBinding.username.text = MainActivity.arraylist.get(intent.getIntExtra("row", 0)).issuename
        viewDataBinding.issue.text = MainActivity.arraylist.get(intent.getIntExtra("row", 0)).issuebody
        Glide.with(this).load(MainActivity.arraylist.get(intent.getIntExtra("row", 0))).transform(CenterCrop(), RoundedCorners(10)).into(viewDataBinding.profile)
    }
}