package com.example.weatherapp.model

data class WeatherModel(
    //This file contains all of the variables that were defined.

    val base: String,

    val id: Int,

    val main: Main,

    val name: String,

    val sys: Sys,

    val weather: List<Weather>,

    val wind: Wind
)