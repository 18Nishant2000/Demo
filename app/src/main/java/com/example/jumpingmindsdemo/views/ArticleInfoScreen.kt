package com.example.jumpingmindsdemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.jumpingmindsdemo.DemoApplication
import com.example.jumpingmindsdemo.MainActivity
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.utils.AsyncReceiver
import com.example.jumpingmindsdemo.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "article"

/**
 * A fragment for showing article info.
 */
class ArticleInfoScreen : Fragment() {

    private var article: Article? = null
    lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = it.getSerializable(ARG_PARAM1) as Article
        }
        (activity as MainActivity).supportActionBar!!.hide()
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
        key = "${article?.author}+${article?.publishedAt}"

        view.findViewById<ImageView>(R.id.image).apply {
            Utils.loadImage(article?.urlToImage, this, object : AsyncReceiver {
                override fun onSuccess() {

                }

                override fun onFailed(error: Error) {
                    view.findViewById<ImageView>(R.id.image)
                        .setImageResource(R.drawable.ic_no_image_foreground)
                }

            })
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.favButton).setOnClickListener {
            GlobalScope.launch {
                (activity?.application as DemoApplication).favoritesDatabase.favoritesDao().insertArticle(Favorites(key, article!!))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).supportActionBar!!.show()
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