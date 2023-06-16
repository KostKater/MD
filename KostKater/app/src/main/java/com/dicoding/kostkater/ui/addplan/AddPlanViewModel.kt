package com.dicoding.kostkater.ui.addplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.mealplan.MealPlanPostResponse
import com.dicoding.kostkater.model.mealplan.MealPlanRequest
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlanViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private lateinit var token: String

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    token = it
                }
            }.collect()
        }
    }

    fun addMealPlan(token: String, mealName: String, date: String, groupMeal: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).postMealPlan(MealPlanRequest(date, mealName, groupMeal))
        client.enqueue(object : Callback<MealPlanPostResponse> {
            override fun onResponse(call: Call<MealPlanPostResponse>, response: Response<MealPlanPostResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = "Tersimpan"
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<MealPlanPostResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
            }
        })
    }
}