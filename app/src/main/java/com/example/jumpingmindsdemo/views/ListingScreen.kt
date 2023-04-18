package com.example.jumpingmindsdemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    }
}