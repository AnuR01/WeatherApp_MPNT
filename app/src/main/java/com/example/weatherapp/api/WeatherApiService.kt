package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherModel

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

import io.reactivex.Single



class WeatherApiService {

   private val BASE_URL = "http://api.openweathermap.org/"
// The code sets various setup options by building the Retrofit.Builder instance.

//For instance, the API's base URL, any necessary headers, or the kind of data converter to apply when parsing API answers.
    private val api = Retrofit.Builder()

        .baseUrl(BASE_URL)

        .addConverterFactory(GsonConverterFactory.create())

        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        .build()
        .create(WeatherApi::class.java)

   // Configure code for open API data retrieval.
    fun getDataService(cityName: String): Single<WeatherModel> {

        return api.getData(cityName)
    }
}