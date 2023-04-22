package com.example.jumpingmindsdemo.views.articles

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.databinding.NewsViewBinding
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.utils.AsyncReceiver
import com.example.jumpingmindsdemo.utils.Utils

/**
 * Adapter for Articles.
 */
class NewsRecyclerViewAdapter(
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    var newsRecyclerViewAdapterListener: NewsRecyclerViewAdapterListener? = null
    var values: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val newsViewBinding = NewsViewBinding.inflate(inflater, parent, false)
        return ViewHolder(newsViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.newsViewBinding.article = item
        holder.itemView.setOnClickListener {
            Log.d("Nishant", "onBindViewHolder: ${item.author}")
            newsRecyclerViewAdapterListener?.onArticleClicked(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(newsViewBinding: NewsViewBinding) : RecyclerView.ViewHolder(newsViewBinding.root) {
        val newsViewBinding = newsViewBinding
    }

    fun update(data: MutableList<Article>) {
        values.clear()
        values.addAll(data)
        notifyDataSetChanged()
    }

    interface NewsRecyclerViewAdapterListener {
        fun onArticleClicked(article: Article)
    }

}