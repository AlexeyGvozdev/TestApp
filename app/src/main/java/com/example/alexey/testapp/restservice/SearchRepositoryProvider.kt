package com.example.alexey.testapp.restservice

/**
 * Created by alexey on 28.03.18.
 */
object SearchRepositoryProvider {

    fun provideSearchRepositoty(): SearchRepository =
            SearchRepository(RestApi.getRestApiService())
}