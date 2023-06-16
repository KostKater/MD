package com.dicoding.kostkater.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.meals.Meal
import com.dicoding.kostkater.model.meals.MealsResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    private val _recommendation = MutableLiveData<List<Meal?>?>()
    val recommendation: LiveData<List<Meal?>?> = _recommendation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _allMeal = MutableLiveData<List<Meal?>?>()
    val allMeal: LiveData<List<Meal?>?> = _allMeal

    private val _isLoading2 = MutableLiveData<Boolean>()
    val isLoading2: LiveData<Boolean> = _isLoading2

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    getRecommendation(it)
                    getAllMeal(it)
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

    private fun getRecommendation(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getRecommendation()
        client.enqueue(object : Callback<MealsResponse> {
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _recommendation.value = response.body()?.data
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }

    private fun getAllMeal(token: String) {
        _isLoading2.value = true
        val client = ApiConfig.getApiService(token).getAllMeal()
        client.enqueue(object : Callback<MealsResponse> {
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                _isLoading2.value = false
                if (response.isSuccessful) {
                    _allMeal.value = response.body()?.data
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                _isLoading2.value = false
                _message.value = t.message
            }
        })
    }
}