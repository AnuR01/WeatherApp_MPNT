package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

import io.reactivex.Single


interface WeatherApi {

    // The data is processing from an open weather Api.
    @GET("data/2.5/weather?&units=metric&APPID=6cd787ced39f932f00c2147bd03d5613")

    // Get Query
    fun getData(@Query("q") cityName: String):

            Single<WeatherModel>
}