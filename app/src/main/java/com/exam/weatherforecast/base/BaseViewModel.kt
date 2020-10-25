package com.exam.weatherforecast.base

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weatherforecast.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    private val _errorMessageLiveEvent = SingleLiveEvent<Int>()
    val errorMessageLiveEvent: LiveData<Int> = _errorMessageLiveEvent

    protected fun launchWithErrorHandling(
        context: CoroutineContext = EmptyCoroutineContext,
        call: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context) {
        try {
            call()
        } catch (error: Throwable) {
            when (error) {
                is NetworkErrorException, is SocketTimeoutException, is UnknownHostException -> {
                    _errorMessageLiveEvent.value = R.string.no_connection
                }

                else -> {
                    _errorMessageLiveEvent.value = R.string.something_wrong
                }
            }
        }
    }
}
