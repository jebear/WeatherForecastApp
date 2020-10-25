package com.exam.weatherforecast.data.local

import android.content.Context

class WeatherLocalImpl(
    private val context: Context
) : WeatherLocal {
    private val sharedPreference =
        context.getSharedPreferences("WEATHER_PREFERENCE", Context.MODE_PRIVATE)

    override fun toggleFavorite(
        id: String
    ) {
        var editor = sharedPreference.edit()
        editor.putBoolean(id, !isFavorite(id))
        editor.commit()
    }

    override fun isFavorite(id: String): Boolean =
        sharedPreference.getBoolean(id, false)

}
