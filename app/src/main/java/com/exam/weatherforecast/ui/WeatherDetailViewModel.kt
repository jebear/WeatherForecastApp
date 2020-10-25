package com.exam.weatherforecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.weatherforecast.base.BaseViewModel
import com.exam.weatherforecast.base.SingleLiveEvent
import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.exam.weatherforecast.data.repository.WeatherRepository

class WeatherDetailViewModel(
    private val repository: WeatherRepository
) : BaseViewModel() {

    private val _favoriteLiveEvent = SingleLiveEvent<Boolean>()
    val favoriteLiveEvent: LiveData<Boolean> = _favoriteLiveEvent

    private val _currentWeatherLiveData = MutableLiveData<WeatherDetailsModel>()
    val currentWeatherLiveData: LiveData<WeatherDetailsModel> = _currentWeatherLiveData

    private val _errorLiveEvent = SingleLiveEvent<String>()
    val errorLiveEvent: LiveData<String> = _errorLiveEvent

    fun getCurrentWeather(city: String) {
        launchWithErrorHandling {
            _currentWeatherLiveData.value = repository.getCurrentWeather(city).also {
                _favoriteLiveEvent.value = it.isFavorite
            }
        }
    }

    fun toggleFavorite(id: String) {
        repository.toggleFavorite(id)
        _favoriteLiveEvent.apply {
            value = value?.not()
        }
    }
}
