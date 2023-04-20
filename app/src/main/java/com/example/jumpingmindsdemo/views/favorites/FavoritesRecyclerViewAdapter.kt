package com.example.jumpingmindsdemo.views.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.utils.AsyncReceiver
import com.example.jumpingmindsdemo.utils.Utils

/**
 *
 * TODO: Replace the implementation with code for your data type.
 */
class FavoritesRecyclerViewAdapter(
) : RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder>() {

    var favoriteNewsRecyclerViewAdapterListener: FavoritesRecyclerViewAdapterListener? = null
    var values: MutableList<Favorites> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_favorites_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.author.text = item.article.author
        holder.title.text = item.article.title
        holder.pub.text = item.article.publishedAt
        item.article.urlToImage
        Utils.loadImage(item.article.urlToImage, holder.imageView, object : AsyncReceiver {
            override fun onSuccess() {

            }

            override fun onFailed(error: Error) {
                holder.imageView.setImageResource(R.drawable.ic_no_image_foreground)
            }

        })
        holder.itemView.setOnClickListener {
            favoriteNewsRecyclerViewAdapterListener?.onFavoriteArticleClicked(item.article)
        }
        holder.deleteButton.setOnClickListener {
            favoriteNewsRecyclerViewAdapterListener?.onFavoriteArticleDeleteClicked(item)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val title: TextView = view.findViewById(R.id.title)
        val deleteButton: ImageButton = view.findViewById(R.id.delete)
        val imageView : ImageView = view.findViewById(R.id.image)
        val pub : TextView = view.findViewById(R.id.pub)

    }

    fun update(data: MutableList<Favorites>) {
        values.clear()
        values.addAll(data)
        notifyDataSetChanged()
    }

    interface FavoritesRecyclerViewAdapterListener {
        fun onFavoriteArticleClicked(article: Article)
        fun onFavoriteArticleDeleteClicked(favArticle: Favorites)
    }

}