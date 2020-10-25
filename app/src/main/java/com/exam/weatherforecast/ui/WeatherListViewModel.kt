package com.exam.weatherforecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.weatherforecast.base.BaseViewModel
import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.exam.weatherforecast.data.repository.WeatherRepository

class WeatherListViewModel(
    val repository: WeatherRepository
) : BaseViewModel() {

    private var _weatherListLiveData = MutableLiveData<List<WeatherDetailsModel>>()
    val weatherListLiveData: LiveData<List<WeatherDetailsModel>> = _weatherListLiveData

    init {
        getWeatherList()
    }

    fun getWeatherList() {
        launchWithErrorHandling {
            _weatherListLiveData.value = repository.getWeatherList(
                id = CONST_GROUP_IDS
            )
        }
    }

    fun isFavorite() {
        _weatherListLiveData.value?.map { it.isFavorite = repository.isFavorite(it.id) }
    }

    companion object {
        private const val CONST_GROUP_IDS = "1701668,3067696,1835848"
    }
}
