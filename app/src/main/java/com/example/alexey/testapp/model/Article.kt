package com.example.alexey.testapp.model


class Article(val team1: String,
              val team2: String,
              val time: String,
              val place: String,
              val article: Array<Date> )

class Date(val header: String, val text: String)