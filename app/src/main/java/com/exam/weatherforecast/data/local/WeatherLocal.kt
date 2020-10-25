package com.exam.weatherforecast.data.local

interface WeatherLocal {
    fun toggleFavorite(id: String)
    fun isFavorite(id: String) : Boolean
}
