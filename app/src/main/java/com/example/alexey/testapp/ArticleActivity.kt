package com.example.alexey.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.alexey.testapp.model.Article
import com.example.alexey.testapp.restservice.SearchRepository
import com.example.alexey.testapp.restservice.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_article.*
import org.jetbrains.anko.toast

class ArticleActivity : AppCompatActivity() {

    private val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepositoty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val articleFromIntent: String? = intent.getStringExtra(KEY_ARTICLE)
        Log.d(TAG, articleFromIntent.toString())
        if (articleFromIntent != null) {
            repository.searchArticle(articleFromIntent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            { ok(it) },
                            { errorFill(it) }
                    )
        }
    }

    private fun errorFill(it: Throwable?) {
        toast(it.toString())
        Log.d(TAG, it.toString())
    }

    val TAG = "ARCT"
    private fun ok(it: Article?) {
        Log.d(TAG, it.toString())
        if(it != null) {
            tv_team1.text = it.team1
            tv_team2.text = it.team2
        }
    }


    companion object {
        val KEY_ARTICLE = "key article"
    }
}
