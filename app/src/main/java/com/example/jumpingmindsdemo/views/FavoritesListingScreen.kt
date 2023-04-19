package com.example.jumpingmindsdemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.ArticlesRepository
import com.example.jumpingmindsdemo.repo.FavoriteArticlesRepository
import com.example.jumpingmindsdemo.repo.local.Favorites
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article

/**
 * A fragment representing a list of Items.
 */
class FavoritesListingScreen : Fragment() {

    private var favoritesArticleList : MutableList<Article> = mutableListOf()
    private lateinit var favoritesRecyclerView: RecyclerView
    private var favoritesRecyclerViewAdapter : FavoritesRecyclerViewAdapter = FavoritesRecyclerViewAdapter()
    private lateinit var favoritesArticlesRepository : FavoriteArticlesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoritesArticlesRepository = FavoriteArticlesRepository(requireContext())
        favoritesArticlesRepository.getFavoritesArticles().observeForever{
            if(it.size > 0){
                favoritesRecyclerViewAdapter.update(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites_listing_screen_list, container, false)
        favoritesRecyclerView = view.findViewById(R.id.favoriteNewsList)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        favoritesRecyclerView.adapter = favoritesRecyclerViewAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesRecyclerViewAdapter.favoriteNewsRecyclerViewAdapterListener = object : FavoritesRecyclerViewAdapter.FavoritesRecyclerViewAdapterListener{
            override fun onFavoriteArticleClicked(article: Article) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.mainContainerView, ArticleInfoScreen.newInstance(article))
                    addToBackStack(null)
                    commit()
                }
            }

            override fun onFavoriteArticleDeleteClicked(favArticle: Favorites) {
                favoritesArticlesRepository.deleteFavoriteArticle(favArticle)
            }

        }
    }

}