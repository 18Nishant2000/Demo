package com.example.jumpingmindsdemo.views

import android.content.Intent
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article

class NewsRecyclerViewAdapter(
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {


    var newsRecyclerViewAdapterListener : NewsRecyclerViewAdapterListener? = null
    var values: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Log.d("Nishant", "article to string ${item.toString()} ")
        holder.author.text = item.author
        holder.title.text = item.title
        holder.itemView.setOnClickListener {
            Log.d("Nishant", "onBindViewHolder: ${item.author}")
            newsRecyclerViewAdapterListener?.onArticleClicked(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val title: TextView = view.findViewById(R.id.title)
    }

    fun update(data : MutableList<Article>){
        values.clear()
        values.addAll(data)
        notifyDataSetChanged()
    }

    interface NewsRecyclerViewAdapterListener{
        fun onArticleClicked(article: Article)

    }

}