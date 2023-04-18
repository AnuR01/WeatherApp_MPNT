package com.example.weatherapp.viewmodel

import android.util.Log
import com.example.weatherapp.api.WeatherApiService
import com.example.weatherapp.model.WeatherModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.observers.DisposableSingleObserver

import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

//The TAG constant is used in the program as a special identifier for a specific class or module.
// To give the logged messages context, that is utilized in log statements.

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {

    private val weatherApiService = WeatherApiService()
    private val disposable = CompositeDisposable()


    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()

    val weather_loading = MutableLiveData<Boolean>()

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }
    //The custom function getDataFromAPI(cityName: String) obtains weather information for a given city from a remote API.

    private fun getDataFromAPI(cityName: String) {


        weather_loading.value = true
        disposable.add(

            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    //This indicates that the data was retrieved from a weather API,
                    // which may provide information on the current temperature, the current weather, and other weather-related data.


                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                        weather_error.value = false

                        weather_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    //The logic for managing the problem may be included in the onError() function,
                    // such as showing an error message to the user or retrying the data retrieval operation after a predetermined length of time.

                    override fun onError(e: Throwable) {

                        weather_error.value = true

                        weather_loading.value = false

                        Log.e(TAG, "onError: " + e)

                    }

                })
        )

    }
}