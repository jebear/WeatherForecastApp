package com.exam.weatherforecast.ui

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.exam.weatherforecast.R
import com.exam.weatherforecast.data.model.WeatherDetailsModel


@EpoxyModelClass
abstract class WeatherEpoxyModelHolder : EpoxyModelWithHolder<WeatherEpoxyModelHolder.Holder>() {
    @EpoxyAttribute
    lateinit var weatherDetail: WeatherDetailsModel

    @EpoxyAttribute
    lateinit var weatherItemClick: (WeatherDetailsModel) -> Unit

    override fun getDefaultLayout(): Int = R.layout.item_weather

    @SuppressLint("ResourceType")
    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.apply {
            temp.text =
                temp.context.getString(R.string.temperature_celcius, weatherDetail.main.temp)
            city.text = weatherDetail.name
            weather.text = weatherDetail.weather.first().main
            weatherCardView.setOnClickListener {
                weatherItemClick(weatherDetail)
            }
            weatherCardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    weatherCardView.context,
                    when {
                        weatherDetail.main.temp < 0.0 -> R.color.freezing
                        weatherDetail.main.temp in 0.0..15.0 -> R.color.cold
                        weatherDetail.main.temp in 15.1..30.0 -> R.color.warm
                        else -> R.color.hot
                    }
                )
            )
            favorite.apply {
                if (weatherDetail.isFavorite) {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.ic_favorite_on)
                } else {
                    visibility = View.GONE
                    setImageResource(R.drawable.ic_favorite_off)
                }
            }

        }
    }

    class Holder : EpoxyHolder() {
        lateinit var temp: TextView
        lateinit var city: TextView
        lateinit var weather: TextView
        lateinit var favorite: ImageView
        lateinit var weatherCardView: CardView

        override fun bindView(itemView: View) {
            temp = itemView.findViewById(R.id.tempTextView)
            city = itemView.findViewById(R.id.cityTextView)
            weather = itemView.findViewById(R.id.weatherTextView)
            favorite = itemView.findViewById(R.id.favoriteImageView)
            weatherCardView = itemView.findViewById(R.id.weatherCardView)
        }
    }
}
