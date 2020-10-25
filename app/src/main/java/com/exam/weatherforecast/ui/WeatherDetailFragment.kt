package com.exam.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.exam.weatherforecast.R
import com.exam.weatherforecast.base.BaseFragment
import com.exam.weatherforecast.databinding.FragmentWeatherDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherDetailFragment : BaseFragment() {

    override val viewModel by viewModel<WeatherDetailViewModel>()

    private lateinit var binding: FragmentWeatherDetailBinding

    private val arguments: WeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = arguments.argumentCity

        viewModel.apply {
            getCurrentWeather(city)

            currentWeatherLiveData.observe(viewLifecycleOwner, Observer { weatherDetail ->
                binding.apply {
                    cityTextView.text = weatherDetail.name
                    tempTextView.text =
                        getString(
                            R.string.temperature_celcius,
                            weatherDetail.main.temp
                        )
                    weatherTextView.text = weatherDetail.weather.first().main
                    minMaxTempTextView.text =
                        getString(
                            R.string.min_max_format,
                            weatherDetail.main.tempMax.toInt(),
                            weatherDetail.main.tempMin.toInt()
                        )

                    favoriteLiveEvent.observe(viewLifecycleOwner, Observer { isFavorite ->
                        setFavoriteImageResource(isFavorite)
                    })

                    favoriteImageView.setOnClickListener {
                        viewModel.toggleFavorite(
                            weatherDetail.id
                        )
                    }
                }
            })

            errorLiveEvent.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setFavoriteImageResource(isFavorite: Boolean) {
        binding.favoriteImageView.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_on
            else R.drawable.ic_favorite_off
        )
    }
}
