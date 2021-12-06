package com.ngocvtt.mvvm.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngocvtt.mvvm.weatherforecast.R
import com.ngocvtt.mvvm.weatherforecast.model.AppMessage
import com.ngocvtt.mvvm.weatherforecast.model.weather.OpenWeather
import com.ngocvtt.mvvm.weatherforecast.model.weather.WeatherInfo
import com.ngocvtt.mvvm.weatherforecast.network.OpenWeatherService
import com.ngocvtt.mvvm.weatherforecast.network.OpenWeatherServiceListener
import com.ngocvtt.mvvm.weatherforecast.utils.Helper
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel: ViewModel() {
    var displayNameLiveData: MutableLiveData<String> = MutableLiveData()
    var noticeMessageLiveData: MutableLiveData<AppMessage> = MutableLiveData()
        private set
    val listWeatherLiveData: MutableLiveData<List<WeatherInfo>> by lazy {MutableLiveData<List<WeatherInfo>>()}
    private var listWeatherInfo: ArrayList<WeatherInfo> = arrayListOf()


    fun getWeather(city: String){
        val searchValue = city.trim()
        if (searchValue.length > 2){
            fetchWeather(searchValue)
        }
        else{
            noticeMessageLiveData.value = AppMessage.NoticeMessage(Helper.getString(R.string.msg_input_more_than_2_char))
        }
    }


    private fun fetchWeather(city: String) {
        OpenWeatherService.getWeather(
            Helper.deAccent(city),
            listener = object : OpenWeatherServiceListener<OpenWeather> {
                override fun <T> onSuccess(data: T) {

                    listWeatherInfo.clear()
                    val result = data as OpenWeather
                    result.list.forEach {
                        val date = Date(it.dt * 1000)
                        val avgTemp = (it.temp.max + it.temp.min) / 2
                        val des = it.weather[0].description
                        listWeatherInfo.add(WeatherInfo(date, avgTemp, it.pressure, it.humidity, des))
                    }
                    displayNameLiveData.value = "${result.city.name}, ${result.city.country}"
                    listWeatherLiveData.value = listWeatherInfo
                }

                override fun onFail(message: String) {
                    noticeMessageLiveData.value = AppMessage.ErrorMessage(message)
                }

            })
    }

}