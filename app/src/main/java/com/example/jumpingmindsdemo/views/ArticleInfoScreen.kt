package com.example.jumpingmindsdemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jumpingmindsdemo.AsyncReceiver
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.Utils
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article


private const val ARG_PARAM1 = "article"

/**
 * A fragment for showing article info.
 */
class ArticleInfoScreen : Fragment() {

    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = it.getSerializable(ARG_PARAM1) as Article
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_article_info_screen, container, false)
        view.findViewById<TextView>(R.id.title).text = article?.title
        view.findViewById<TextView>(R.id.author).text = article?.author
        view.findViewById<TextView>(R.id.desc).text = article?.description
        view.findViewById<TextView>(R.id.pub).text = article?.publishedAt

        view.findViewById<ImageView>(R.id.image).apply {
            article?.urlToImage?.let {
                Utils.loadImage(it, this, object : AsyncReceiver {
                    override fun onSuccess() {

                    }

                    override fun onFailed(error: Error) {
                        view.findViewById<ImageView>(R.id.image).visibility = View.GONE
                    }

                })
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Article) =
            ArticleInfoScreen().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}