package com.dicoding.kostkater.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.auth.AuthRequest
import com.dicoding.kostkater.model.auth.RegisterResponse
import com.dicoding.kostkater.model.auth.UserInfo
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    private val service = ApiConfig.getApiService("")

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun register(email: String, password: String) {
        _isLoading.value = true
        val response = service.register(AuthRequest(password, email))
        response.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _registerResponse.value = response.body()
                    response.body()?.userInfo?.let { saveToken(it.token) }
                } else {
                    _registerResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _registerResponse.value = RegisterResponse(null, "Network error", true)
            }
        })
    }
}