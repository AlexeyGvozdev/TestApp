package com.example.alexey.testapp.model

import java.io.Serializable


data class Article(val team1: String,
                   val team2: String,
                   val time: String,
                   val place: String,
                   val article: Array<Date>,
                   val prediction: String): Serializable

class Date(val header: String, val text: String): Serializable