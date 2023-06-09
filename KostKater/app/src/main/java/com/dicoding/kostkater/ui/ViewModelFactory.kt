package com.dicoding.kostkater.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.ui.addplan.AddPlanViewModel
import com.dicoding.kostkater.ui.login.LoginViewModel
import com.dicoding.kostkater.ui.main.MainViewModel
import com.dicoding.kostkater.ui.mealplan.MealPlanViewModel
import com.dicoding.kostkater.ui.preference.PreferenceViewModel
import com.dicoding.kostkater.ui.recipe.RecipeViewModel
import com.dicoding.kostkater.ui.register.RegisterViewModel

class ViewModelFactory(private val pref: UserPreference, private val string: String?) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            modelClass.isAssignableFrom(PreferenceViewModel::class.java) -> {
                PreferenceViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> {
                string?.let { RecipeViewModel(pref, it) } as T
            }

            modelClass.isAssignableFrom(MealPlanViewModel::class.java) -> {
                MealPlanViewModel(pref) as T
            }

            modelClass.isAssignableFrom(AddPlanViewModel::class.java) -> {
                AddPlanViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}