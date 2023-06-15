package com.dicoding.kostkater.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.auth.AuthRequest
import com.dicoding.kostkater.model.auth.LoginResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val service = ApiConfig.getApiService("")

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val response = service.login(AuthRequest(password, email))
        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _loginResponse.value = response.body()
                    response.body()?.userInfo?.let { saveToken(it.token) }
                } else {
                    _loginResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _loginResponse.value = LoginResponse(null, true, "Network error")
            }
        })
    }
}