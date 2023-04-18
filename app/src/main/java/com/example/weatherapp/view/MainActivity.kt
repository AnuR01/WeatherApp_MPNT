package com.example.weatherapp.view

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.viewmodel.MainViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.weatherapp.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    //view are binding

    lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewmodel: MainViewModel

    // Get and Set variables:

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)


        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewmodel = ViewModelProviders.of(this)[MainViewModel::class.java]

        var cName = GET.getString("placeName", "oulu")?.lowercase()
        mainBinding.edtCityName.setText(cName)
        viewmodel.refreshData(cName!!)

        getLiveData()

        //The method setOnRefreshListener is called.
        // This can entail doing things like updating a RecyclerView's contents, loading data from an API, or refreshing the active activity.

        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            mainBinding.llData.visibility = View.GONE
           mainBinding.tvError.visibility = View.GONE
            mainBinding.pbLoading.visibility = View.GONE

           // Here, we are retrieving the cityname using the GET function.

            var cityName = GET.getString("placeName", cName)?.lowercase()
            mainBinding.edtCityName.setText(cityName)

            viewmodel.refreshData(cityName!!)
            mainBinding.swipeRefreshLayout.isRefreshing = false

        }

        /// based on the user-entered city name,
        // the setOnClickListerner method can launch a new activity, show a dialog box, or run a search.

        mainBinding.imgSearchCity.setOnClickListener {
            val cityName = mainBinding.edtCityName.text.toString()

            SET.putString("placeName", cityName)
            SET.apply()

            viewmodel.refreshData(cityName)
            getLiveData()

            Log.i(TAG, "onCreate: " + cityName)
        }

    }

    //Other app functions or methods may call the getLiveData() function.
    //To update the Interface after retrieving data.

    private fun getLiveData() {

        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {
                mainBinding.llData.visibility = View.VISIBLE


               mainBinding.tvCityCode.text = data.sys.country.toString()
                mainBinding.tvCityName.text = data.name.toString()


                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(mainBinding.imgWeatherPictures)


                // Temperature in celsius

                mainBinding.tvDegree.text = data.main.temp.toString() + "°C"

                //Calculating Wind Speed and Humidity.

                mainBinding.tvHumidity.text = data.main.humidity.toString() + "%"

                mainBinding.tvWindSpeed.text = data.wind.speed.toString()


            }
        })

    }
}


