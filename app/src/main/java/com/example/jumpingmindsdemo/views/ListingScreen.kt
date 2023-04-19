package com.example.jumpingmindsdemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmindsdemo.DemoApplication
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.viewModels.ListingScreenViewModel

/**
 * Fragment representing a list of Articles.
 */
class ListingScreen : Fragment() {

    private lateinit var listingScreenViewModel: ListingScreenViewModel
    private var articleList : MutableList<Article>
    private lateinit var recyclerView: RecyclerView
    private var newsRecyclerViewAdapter : NewsRecyclerViewAdapter
    private lateinit var searchView : SearchView
    init {
        articleList = mutableListOf()
        newsRecyclerViewAdapter = NewsRecyclerViewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listingScreenViewModel = ViewModelProvider(this).get(ListingScreenViewModel::class.java)

        //TODO temp call for getting news
        listingScreenViewModel.getNews()

        listingScreenViewModel.data.observeForever {
            if(it.size > 0){
                articleList = it
                newsRecyclerViewAdapter.update(articleList)
            }
        }
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
        newsRecyclerViewAdapter.newsRecyclerViewAdapterListener = object : NewsRecyclerViewAdapter.NewsRecyclerViewAdapterListener{
            override fun onArticleClicked(article: Article) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.mainContainerView, ArticleInfoScreen.newInstance(article))
                    addToBackStack(null)
                    commit()
                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    filterList(newText)
                }
                return true
            }

        })

        DemoApplication.search.observeForever {
            if(it){
                searchView.visibility = View.VISIBLE
            }else{
                searchView.visibility = View.GONE
            }
        }

    }
    private fun filterList(newText: String) {
        var filteredValues: MutableList<Article> = mutableListOf()
        articleList.forEach {
            if((it.author != null && it.author.lowercase().contains(newText.lowercase())) || it.publishedAt.lowercase().contains(newText.lowercase())){
                filteredValues.add(it)
            }
        }

        if(filteredValues.isEmpty()){
            Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
        }else{
            newsRecyclerViewAdapter.update(filteredValues)
        }

    }
}