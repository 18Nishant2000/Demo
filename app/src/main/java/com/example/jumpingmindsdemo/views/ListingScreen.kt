package com.example.jumpingmindsdemo.views

import android.os.Bundle
import android.util.Log
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
    private var articleList = mutableListOf<Article>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listingScreenViewModel = ViewModelProvider(this).get(ListingScreenViewModel::class.java)

        //TODO temp call for getting news
        listingScreenViewModel.getNews()

        listingScreenViewModel.data.observeForever {
            if(it.size > 0){
                articleList = it
                recyclerView.adapter = NewsRecyclerViewAdapter(articleList)
                /*recyclerView.adapter!!.notifyDataSetChanged()*/
                Log.d("Nishant", "onCreate: ${articleList.size}")

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
        recyclerView.adapter = NewsRecyclerViewAdapter(articleList)
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ListingScreen().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }




}