package com.ngocvtt.mvvm.weatherforecast.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ngocvtt.mvvm.weatherforecast.R
import com.ngocvtt.mvvm.weatherforecast.utils.Helper
import com.ngocvtt.mvvm.weatherforecast.utils.RootedTrack
import com.ngocvtt.mvvm.weatherforecast.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Helper.init(applicationContext)
        RootedTrack.init()

        weatherViewModel.displayNameLiveData.observe(this, {
            edt_search.setText(it)
        })

        weatherViewModel.listWeatherLiveData.observe(this, { weatherList ->
            weatherListView.adapter = ArrayAdapter(
                this@MainActivity,
                R.layout.weather_cell,
                weatherList.map { it.displayValue })

            btn_getWeather.isEnabled = true
        })

        weatherViewModel.noticeMessageLiveData.observe(this, {
            Helper.showAppMessageDialog(this, it)
            btn_getWeather.isEnabled = true
        })



        btn_getWeather.setOnClickListener {
            Helper.dismissKeyboard()
            btn_getWeather.isEnabled = false
            weatherViewModel.getWeather(edt_search.text.toString())
        }

    }
}