package com.dicoding.kostkater.ui.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.meals.Recipe
import com.dicoding.kostkater.model.meals.RecipeResponse
import com.dicoding.kostkater.remote.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(private val pref: UserPreference, private val mealName: String): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: MutableLiveData<Recipe?> = _recipe

    init {
        viewModelScope.launch {
            pref.token.onEach {
                if (it.isNotEmpty()) {
                    getRecipe(it, mealName)
                }
            }.collect()
        }
    }

    fun getRecipe(token: String, mealName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getRecipe(mealName)
        client.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _recipe.value = response.body()?.recipe
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "RecipeViewModel"
    }
}