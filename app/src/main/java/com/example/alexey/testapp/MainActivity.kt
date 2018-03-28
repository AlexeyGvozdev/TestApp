package com.example.alexey.testapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.alexey.testapp.model.Category
import com.example.alexey.testapp.model.Event
import com.example.alexey.testapp.restservice.SearchRepository
import com.example.alexey.testapp.restservice.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepositoty()
    private val adapter = MyAdapter({ openArticleActivity(it) })

    private fun openArticleActivity(event: Event) {
        startActivity<ArticleActivity>(ArticleActivity.KEY_ARTICLE to event.article)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        nav_view.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null) {
            nav_view.menu.getItem(1).isChecked = true
            onNavigationItemSelected(nav_view.menu.getItem(1))
        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }


    private fun errorFill(it: Throwable?) {
        Log.d(TAG, it.toString())
        toast(it.toString())
    }

    val TAG = "TAG"
    private fun ok(it: Category) {
        adapter.setListEvents(it.events)
        Log.d(TAG, it.events[0].toString() + "gmhgmbhbv\nmbm")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        repository.searchCategory(item.title.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { ok(it) },
                        { errorFill(it)}
                )
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
