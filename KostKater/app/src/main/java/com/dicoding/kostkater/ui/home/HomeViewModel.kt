package com.dicoding.kostkater.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.Meal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val recommendations = listOf(
        Meal(
            "https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg",
            "Sup Ayam Klaten",
            "Sup ayam dengan kuah gurih, potongan ayam, sayuran segar, dan bawang goreng.",
            "Rp25.000",
            "200kal",
            "20g",
            "15g",
            "10g"
        ),
        Meal(
            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg",
            "Rendang",
            "Hidangan daging yang dimasak dengan rempah-rempah khas Indonesia, dengan rasa gurih dan pedas yang kaya serta tekstur daging yang lembut.",
            "Rp40.000",
            "300kal",
            "15g",
            "40g",
            "50g"
        ),
        Meal(
            "https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg",
            "Sup Ayam Klaten",
            "Sup ayam dengan kuah gurih, potongan ayam, sayuran segar, dan bawang goreng.",
            "Rp25.000",
            "200kal",
            "20g",
            "15g",
            "10g"
        ),
        Meal(
            "https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg",
            "Rendang",
            "Hidangan daging yang dimasak dengan rempah-rempah khas Indonesia, dengan rasa gurih dan pedas yang kaya serta tekstur daging yang lembut.",
            "Rp40.000",
            "300kal",
            "15g",
            "40g",
            "50g"
        )
    )

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    init {
        viewModelScope.launch {
            getMeals()
        }
    }

    private suspend fun getMeals() {
        _isLoading.value = true
        delay(2000)
        _meals.value = recommendations
        _isLoading.value = false
    }
}