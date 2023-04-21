package com.example.jumpingmindsdemo.views

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        view.findViewById<Button>(R.id.link).setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                this.data = Uri.parse(article?.url)
                startActivity(this)
            }
        }

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

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.favButton).setOnClickListener {
            GlobalScope.launch {
                (activity?.application as DemoApplication).favoritesDatabase.favoritesDao().insertArticle(Favorites(key, article!!))
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                }
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