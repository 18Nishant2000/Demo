package com.example.jumpingmindsdemo.views.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.DemoApplication
import com.example.jumpingmindsdemo.MainActivity
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.utils.NetworkUtils
import com.example.jumpingmindsdemo.utils.Utils
import com.example.jumpingmindsdemo.viewModels.articles.ListingScreenViewModel
import com.example.jumpingmindsdemo.viewModels.articles.ListingScreenViewModelFactory
import com.example.jumpingmindsdemo.views.ArticleInfoScreen

/**
 * Fragment representing a list of Articles.
 */
class ListingScreen : Fragment() {

    private lateinit var listingScreenViewModel: ListingScreenViewModel
    private var articleList: MutableList<Article> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private var newsRecyclerViewAdapter: NewsRecyclerViewAdapter = NewsRecyclerViewAdapter()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val articleRepository = (activity?.application as DemoApplication).articlesRepository
        listingScreenViewModel =
            ViewModelProvider(this, ListingScreenViewModelFactory(articleRepository)).get(
                ListingScreenViewModel::class.java
            )

        listingScreenViewModel.getArticles().observeForever {
            if (it.size > 0) {
                if(!NetworkUtils.isInternetAvailable(requireContext()))
                    Toast.makeText(context, "Please connect with internet for more articles", Toast.LENGTH_SHORT).show()
                articleList = it
                newsRecyclerViewAdapter.update(articleList)
            }else{
                if(NetworkUtils.isInternetAvailable(requireContext()))
                    Toast.makeText(context, "No Articles are there", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Please Check your internet connection.\nAnd try again", Toast.LENGTH_LONG).show()
            }
        }

        (activity as MainActivity).supportActionBar!!.title = "Jumping News"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listing_screen, container, false)
        recyclerView = view.findViewById(R.id.newsList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = newsRecyclerViewAdapter
        searchView = view.findViewById(R.id.searchView)
        searchView.clearFocus()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsRecyclerViewAdapter.newsRecyclerViewAdapterListener = object :
            NewsRecyclerViewAdapter.NewsRecyclerViewAdapterListener {
            override fun onArticleClicked(article: Article) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.mainContainerView, ArticleInfoScreen.newInstance(article))
                    addToBackStack(null)
                    commit()
                }
                searchView.clearFocus()
                searchView.setQuery("", true)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }

        })

        (activity?.application as DemoApplication).search.observeForever {
            if (it) {
                searchView.visibility = View.VISIBLE
                recyclerView.translationY = 200F
            } else {
                searchView.visibility = View.GONE
                recyclerView.translationY = 0F
            }
        }

    }

    override fun onStop() {
        super.onStop()
        (activity?.application as DemoApplication).search.value = false
    }

    private fun filterList(newText: String) {
        val filteredValues: MutableList<Article> = mutableListOf()
        articleList.forEach {
            if ((it.author != null && it.author.lowercase()
                    .contains(newText.lowercase())) || it.publishedAt.lowercase()
                    .contains(newText.lowercase()) || it.title.lowercase().contains(newText.lowercase())
            ) {
                filteredValues.add(it)
            }
        }

        if (filteredValues.isEmpty())
            Toast.makeText(requireContext(), "No Article Found", Toast.LENGTH_SHORT).show()
        newsRecyclerViewAdapter.update(filteredValues)

    }

}