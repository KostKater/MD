package com.dicoding.kostkater.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.kostkater.model.Recipe

class RecipeViewModel : ViewModel() {

    private val recipeObj = Recipe(
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

    private val _recipe = MutableLiveData<Recipe>().apply {
        value = recipeObj
    }
    val recipe: LiveData<Recipe> = _recipe
}