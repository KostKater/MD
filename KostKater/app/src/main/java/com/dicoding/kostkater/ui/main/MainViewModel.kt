package com.dicoding.kostkater.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.meals.DataItem
import com.dicoding.kostkater.model.meals.MealsResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference): ViewModel() {

    private val _meals = MutableLiveData<List<DataItem?>>()
    val meals: LiveData<List<DataItem?>> = _meals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    getMeals(it)
                }
            }.collect()
        }
    }

    fun getToken(): LiveData<String> {
        return pref.token.asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.saveToken("")
        }
    }

    private fun getMeals(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getRecommendation()
        client.enqueue(object : Callback<MealsResponse> {
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _meals.value = response.body()?.data
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}