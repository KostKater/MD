package com.dicoding.kostkater.ui.mealplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MealPlanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is meal plan Fragment"
    }
    val text: LiveData<String> = _text
}