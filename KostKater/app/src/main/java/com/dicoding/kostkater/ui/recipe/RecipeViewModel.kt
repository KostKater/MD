package com.dicoding.kostkater.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kostkater.model.meals.Recipes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val recipeObj = Recipes(
        listOf(
            "1/2 daging sapi secukupnya",
            "Minyak goreng",
            "1 batang sereh",
            "2 lembar daun salam",
            "2 lembar daun jeruk",
            "600 ml santan kental / sun kara",
            "200 ml air",
            "Bumbu yang dihaluskan",
            "2 siung bawang putih",
            "4 siung bawang merah",
            "1 ruas jahe",
            "1 ruas lengkuas",
            "6 cabai rawit merah"
        ),
        listOf(
            "Potong-potong daging sapi. Lalu cuci bersih.",
            "Siapkan wajan. Tambahkan minyak. Setelah minyak panas, masukan bumbu yang sudah dihaluskan, daun salam, daun jeruk, dan sereh. Tumis hingga bumbu matang dan harum.",
            "Tambahkan air. Masukan daging dan tunggu sampai mendidih. Setelah mendidih, masukkan santan.",
            "Beri garam, gula, dan penyedap. Cek rasa.",
            "Tunggu sampai air surut, daging empuk, dan matang."
        )
    )

    private val _recipe = MutableLiveData<Recipes>()
    val recipe: LiveData<Recipes> = _recipe

    init {
        viewModelScope.launch {
            getRecipe()
        }
    }

    private suspend fun getRecipe() {
        _isLoading.value = true
        delay(2000)
        _recipe.value = recipeObj
        _isLoading.value = false
    }
}