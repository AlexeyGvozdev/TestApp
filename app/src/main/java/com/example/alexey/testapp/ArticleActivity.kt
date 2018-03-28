package com.example.alexey.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.alexey.testapp.model.Article
import com.example.alexey.testapp.restservice.SearchRepository
import com.example.alexey.testapp.restservice.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_article.*
import org.jetbrains.anko.toast

class ArticleActivity : AppCompatActivity() {

    private val KEY_ARTICLE_CLASS = "key"
    private var article: Article? = null
    private val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepositoty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        if(savedInstanceState == null) {
            val articleFromIntent: String? = intent.getStringExtra(KEY_ARTICLE)
            if (articleFromIntent != null) {
                repository.searchArticle(articleFromIntent)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                { onLoad(it) },
                                { onError(it) }
                        )
            }
        } else {
            article = savedInstanceState.getSerializable(KEY_ARTICLE_CLASS) as Article?
            showArcticle(article)
        }
    }

    private fun onError(it: Throwable?) {
        toast(it.toString())
        Log.d(TAG, it.toString())
    }

    val TAG = "ARCT"
    private fun onLoad(it: Article?) {
        article = it

        showArcticle(article)

    }

    private fun showArcticle(article: Article?) {
        progress_arcticle.visibility = View.GONE
        tv_team1.text = article?.team1
        tv_team2.text = article?.team2
        tv_place_arcticle.text = article?.place
        tv_time_arcticle.text = article?.time
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putSerializable(KEY_ARTICLE_CLASS, article)
    }

    companion object {
        val KEY_ARTICLE = "key article"
    }
}
