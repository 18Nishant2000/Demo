package com.example.jumpingmindsdemo.views.articles

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.utils.AsyncReceiver
import com.example.jumpingmindsdemo.utils.Utils

class NewsRecyclerViewAdapter(
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {


    var newsRecyclerViewAdapterListener: NewsRecyclerViewAdapterListener? = null
    var values: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.author.text = item.author
        holder.title.text = item.title
        holder.pub.text = item.publishedAt
        item.urlToImage
        Utils.loadImage(item.urlToImage, holder.imageView, object : AsyncReceiver{
            override fun onSuccess() {

            }

            override fun onFailed(error: Error) {
                holder.imageView.setImageResource(R.drawable.ic_no_image_foreground)
            }

        })
        holder.itemView.setOnClickListener {
            Log.d("Nishant", "onBindViewHolder: ${item.author}")
            newsRecyclerViewAdapterListener?.onArticleClicked(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val title: TextView = view.findViewById(R.id.title)
        val pub : TextView = view.findViewById(R.id.pub)
        val imageView : ImageView = view.findViewById(R.id.image)
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