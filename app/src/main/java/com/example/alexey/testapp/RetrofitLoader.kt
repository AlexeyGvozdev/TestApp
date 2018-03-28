package com.example.alexey.testapp

import android.content.Context
import android.support.v4.content.Loader;
import android.util.Log
import android.widget.Toast
import com.example.alexey.testapp.model.Category
import com.example.alexey.testapp.model.Event
import com.example.alexey.testapp.restservice.SearchRepository
import com.example.alexey.testapp.restservice.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RetrofitLoader(context: Context, private val categoryName: String) : Loader<List<Event>>(context) {

    private var listEvents: List<Event> = emptyList()
    private val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepositoty()


    override fun onStartLoading() {
        super.onStartLoading()
        Log.d("LOADER", listEvents.toString())
        if(!listEvents.isEmpty()) {
            deliverResult(listEvents)
        } else {
            forceLoad()
        }
    }

    override fun onForceLoad() {
        super.onForceLoad()
        repository.searchCategory(categoryName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { returnListEvents(it) },
                        { errorLoad(it)}
                )
    }

    private fun errorLoad(it: Throwable?) {
        Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
    }

    private fun returnListEvents(it: Category?) {
        if(it != null) {
            listEvents = it.events
            deliverResult(listEvents)
        } else {
            Toast.makeText(context, "Error load", Toast.LENGTH_LONG).show()
        }
    }


}