package com.example.jumpingmindsdemo.views.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.DemoApplication
import com.example.jumpingmindsdemo.MainActivity
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.FavoriteArticlesRepository
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.viewModels.favorites.FavoritesListingScreenViewModel
import com.example.jumpingmindsdemo.viewModels.favorites.FavoritesListingScreenViewModelFactory
import com.example.jumpingmindsdemo.views.ArticleInfoScreen

/**
 * Fragment representing list of Favorites Articles.
 */
class FavoritesListingScreen : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private var favoritesRecyclerViewAdapter: FavoritesRecyclerViewAdapter =
        FavoritesRecyclerViewAdapter()
    lateinit var favoritesListingScreenViewModel: FavoritesListingScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).supportActionBar!!.hide()

        val favoriteArticlesRepository =
            (activity?.application as DemoApplication).favoriteArticlesRepository
        favoritesListingScreenViewModel = ViewModelProvider(
            this,
            FavoritesListingScreenViewModelFactory(favoriteArticlesRepository)
        ).get(
            FavoritesListingScreenViewModel::class.java
        )
        favoritesListingScreenViewModel.getFavoritesArticles()
            .observe(this@FavoritesListingScreen) {
                if (it.size > 0) {
                    favoritesRecyclerViewAdapter.update(it)
                } else {
                    Toast.makeText(context, "Favorites are empty!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
            }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar!!.hide()
    }

    override fun onPause() {
        favoritesListingScreenViewModel.getFavoritesArticles()
            .removeObservers(this@FavoritesListingScreen)
        super.onPause()
    }

    override fun onStop() {
        (activity as MainActivity).supportActionBar!!.show()
        favoritesListingScreenViewModel.getFavoritesArticles()
            .removeObservers(this@FavoritesListingScreen)
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_favorites_listing_screen_list, container, false)
        favoritesRecyclerView = view.findViewById(R.id.favoriteNewsList)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        favoritesRecyclerView.adapter = favoritesRecyclerViewAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesRecyclerViewAdapter.favoriteNewsRecyclerViewAdapterListener = object :
            FavoritesRecyclerViewAdapter.FavoritesRecyclerViewAdapterListener {
            override fun onFavoriteArticleClicked(article: Article) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.mainContainerView, ArticleInfoScreen.newInstance(article))
                    addToBackStack(null)
                    commit()
                }
            }

            override fun onFavoriteArticleDeleteClicked(favArticle: Favorites) {
                favoritesListingScreenViewModel.deleteFavoriteArticle(favArticle)
            }

        }
    }

}