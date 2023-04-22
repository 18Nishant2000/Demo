package com.example.jumpingmindsdemo.views.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.databinding.FragmentFavoritesItemBinding
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.utils.AsyncReceiver
import com.example.jumpingmindsdemo.utils.Utils

/**
 *
 * Adapter for Favorites Articles
 */
class FavoritesRecyclerViewAdapter(
) : RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder>() {

    var favoriteNewsRecyclerViewAdapterListener: FavoritesRecyclerViewAdapterListener? = null
    var values: MutableList<Favorites> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val favoritesItemBinding = FragmentFavoritesItemBinding.inflate(inflater, parent, false)
        return ViewHolder(favoritesItemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.favoritesItemBinding.article = item.article

        holder.itemView.setOnClickListener {
            favoriteNewsRecyclerViewAdapterListener?.onFavoriteArticleClicked(item.article)
        }
        holder.favoritesItemBinding.delete.setOnClickListener {
            favoriteNewsRecyclerViewAdapterListener?.onFavoriteArticleDeleteClicked(item)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(favoritesItemBinding: FragmentFavoritesItemBinding) :
        RecyclerView.ViewHolder(favoritesItemBinding.root) {
        val favoritesItemBinding = favoritesItemBinding
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