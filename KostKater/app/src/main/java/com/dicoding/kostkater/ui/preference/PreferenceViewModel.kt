package com.dicoding.kostkater.ui.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.user.Preference
import com.dicoding.kostkater.model.user.PreferenceRequest
import com.dicoding.kostkater.model.user.PreferenceResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferenceViewModel(private val pref: UserPreference) : ViewModel() {

    private val _userPreference = MutableLiveData<Preference?>()
    val userPreference: LiveData<Preference?> = _userPreference

    private val _isLoadingData = MutableLiveData<Boolean>()
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    private val _isLoadingSave = MutableLiveData<Boolean>()
    val isLoadingSave: LiveData<Boolean> = _isLoadingSave

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    getUserPreference(it)
                }
            }.collect()
        }
    }

    private fun getUserPreference(token: String) {
        _isLoadingData.value = true
        val client = ApiConfig.getApiService(token).getUserData()
        client.enqueue(object : Callback<PreferenceResponse> {
            override fun onResponse(
                call: Call<PreferenceResponse>,
                response: Response<PreferenceResponse>
            ) {
                _isLoadingData.value = false
                if (response.isSuccessful) {
                    _userPreference.value = response.body()?.preference
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<PreferenceResponse>, t: Throwable) {
                _isLoadingData.value = false
                _message.value = t.message
            }
        })
    }

    fun savePreference(
        token: String,
        halal: Boolean,
        allergies: List<String>,
        priceMin: Int,
        priceMax: Int,
        listIngredient: List<String>
    ) {
        _isLoadingSave.value = true
        val client = ApiConfig.getApiService(token)
            .postPreference(PreferenceRequest(allergies, listIngredient, priceMin, priceMax, halal))
        client.enqueue(object : Callback<PreferenceResponse> {
            override fun onResponse(
                call: Call<PreferenceResponse>,
                response: Response<PreferenceResponse>
            ) {
                _isLoadingSave.value = false
                if (response.isSuccessful) {
                    _userPreference.value = response.body()?.preference
                    _message.value = "Tersimpan"
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<PreferenceResponse>, t: Throwable) {
                _isLoadingSave.value = false
                _message.value = t.message
            }
        })
    }
}