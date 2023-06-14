package com.dicoding.kostkater.ui.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.user.Preference
import com.dicoding.kostkater.model.user.UserResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferenceViewModel(private val pref: UserPreference) : ViewModel() {

    private val _userPreference = MutableLiveData<Preference?>()
    val userPreference: MutableLiveData<Preference?> = _userPreference

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    getUserData(it)
                }
            }.collect()
        }
    }

    private fun getUserData(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getUserData()
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userPreference.value = response.body()?.preference
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun savePreference(halal: Boolean, allergies: List<String>, priceMin: Int, priceMax: Int, listIngredient: List<String>) {
    }

    companion object {
        private const val TAG = "PreferenceViewModel"
    }
}