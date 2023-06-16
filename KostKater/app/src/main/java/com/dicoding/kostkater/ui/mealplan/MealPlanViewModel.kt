package com.dicoding.kostkater.ui.mealplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.mealplan.MealPlan
import com.dicoding.kostkater.model.mealplan.MealPlanResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealPlanViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mealPlan = MutableLiveData<List<MealPlan>?>()
    val mealPlan: LiveData<List<MealPlan>?> = _mealPlan

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    getMealPlan(it)
                }
            }.collect()
        }
    }

    private fun getMealPlan(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getMealPlan()
        client.enqueue(object : Callback<MealPlanResponse> {
            override fun onResponse(
                call: Call<MealPlanResponse>,
                response: Response<MealPlanResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _mealPlan.value = response.body()?.data
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<MealPlanResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }
}