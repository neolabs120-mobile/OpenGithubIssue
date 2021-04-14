package com.github.repo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.repo.activity.DetailActivity
import com.github.repo.model.LayoutData

class RecyclerViewAdapter (private val context : Context, var data: MutableList<LayoutData>) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View

        view = LayoutInflater.from(context).inflate(R.layout.listview, parent, false)
        return MyViewHolder(view, context)
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindissue(data[position])
    }
}

class MyViewHolder(itemView : View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    val issues = itemView.findViewById<TextView>(R.id.listtext)

    fun bindissue(layoutData: LayoutData) {
        issues.text = layoutData.issues

        issues.setOnClickListener {
            var nextIntent = Intent(context, DetailActivity::class.java)
            Log.d("rowrowrow", adapterPosition.toString())
            nextIntent.putExtra("row", adapterPosition)
            context.startActivity(nextIntent)
        }
    }
}
