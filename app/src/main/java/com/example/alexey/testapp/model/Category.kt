package com.example.alexey.testapp.model


class Category(val events: List<Event>)

data class Event(val title: String,
               val coefficient: String,
               val place: String,
               val preview: String,
               val article: String,
               val keyCategory: String)