package com.example.alexey.testapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.alexey.testapp.model.Event
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val adapter = MyAdapter({ openArticleActivity(it) })
    private var idCheckedCategory: Int = 0
    private val KEY_ID_CHECKED_CATEGORY = "id"
    private val KEY_CATEGORY_NAME = "name"
    private var globalCategotyName: String = ""

    private fun openArticleActivity(event: Event) {
        startActivity<ArticleActivity>(ArticleActivity.KEY_ARTICLE to event.article)
    }


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        nav_view.setNavigationItemSelectedListener(this)


        if(savedInstanceState == null) {
            nav_view.menu.getItem(idCheckedCategory).isChecked = true
            onNavigationItemSelected(nav_view.menu.getItem(idCheckedCategory))
        } else {
            idCheckedCategory = savedInstanceState.getInt(KEY_ID_CHECKED_CATEGORY)
            globalCategotyName = savedInstanceState.getString(KEY_CATEGORY_NAME)
            Log.d(TAG, idCheckedCategory.toString())
            loadEvents(false, idCheckedCategory, nav_view.menu.getItem(idCheckedCategory).title.toString())
        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        refresh.setOnRefreshListener { refreshListEvents() }
        refresh.setColorSchemeResources(R.color.blue)
    }

    private fun refreshListEvents() {
        loadEvents(true, idCheckedCategory, globalCategotyName)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(KEY_ID_CHECKED_CATEGORY, idCheckedCategory)
        outState?.putString(KEY_CATEGORY_NAME, globalCategotyName)
        Log.d(TAG, idCheckedCategory.toString())
        super.onSaveInstanceState(outState)
    }

    val TAG = "TAG"


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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        for (i in 0..(nav_view.menu.size() - 1)) {
            Log.d(TAG, "id ${item.itemId} : " + nav_view.menu.getItem(i))
            if(nav_view.menu.getItem(i).itemId == item.itemId) {
                idCheckedCategory = i
            }
        }
        globalCategotyName = item.title.toString()
        loadEvents(false, idCheckedCategory, item.title.toString())
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadEvents(restart: Boolean, itemId: Int, categoryName: String) {
        Log.d(TAG, "NENF ${itemId}")


        val callbacks: LoaderManager.LoaderCallbacks<List<Event>> = EventsCallback(categoryName)
        if (restart) {
            refresh.isRefreshing = true
            supportLoaderManager.restartLoader(itemId, Bundle.EMPTY, callbacks)
        } else {
            supportLoaderManager.initLoader(itemId, Bundle.EMPTY, callbacks)
        }
    }

    inner class EventsCallback(private val categoryName: String) : LoaderManager.LoaderCallbacks<List<Event>> {
        override fun onLoaderReset(loader: Loader<List<Event>>?) {   }
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Event>> = RetrofitLoader(this@MainActivity, categoryName)
        override fun onLoadFinished(loader: Loader<List<Event>>?, data: List<Event>) { showEvents(data) }
    }

    private fun showEvents(listEvent: List<Event>) {
        adapter.setListEvents(listEvent)
        refresh.isRefreshing = false
    }
}
