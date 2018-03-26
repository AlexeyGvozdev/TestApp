package com.example.alexey.testapp.model


class Category(val events: List<Events>)

data class Events(val title: String,
               val coefficient: String,
               val place: String,
               val preview: String,
               val acricle: String,
               val keyCategory: String)