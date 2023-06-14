package com.dicoding.kostkater.ui.dialog

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dicoding.kostkater.model.UserPreference

class PreferenceViewModel(private val pref: UserPreference) : ViewModel() {

    fun savePreference(halal: Boolean, allergies: List<String>, priceMin: Int?, priceMax: Int?, listIngredient: List<String>) {

    }
}