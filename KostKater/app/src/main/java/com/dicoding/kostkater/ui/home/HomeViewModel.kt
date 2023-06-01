package com.dicoding.kostkater.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.kostkater.model.Recommendation

class HomeViewModel : ViewModel() {

    private val recommendations = listOf(
        Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten", "Sup ayam dengan kuah gurih, potongan ayam, sayuran segar, dan bawang goreng.", "Rp25.000"),
        Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang", "Hidangan daging yang dimasak dengan rempah-rempah khas Indonesia, dengan rasa gurih dan pedas yang kaya serta tekstur daging yang lembut.", "Rp40.000"),

        Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten", "Sup ayam dengan kuah gurih, potongan ayam, sayuran segar, dan bawang goreng.", "Rp25.000"),
        Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang", "Hidangan daging yang dimasak dengan rempah-rempah khas Indonesia, dengan rasa gurih dan pedas yang kaya serta tekstur daging yang lembut.", "Rp40.000"),

        Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten", "Sup ayam dengan kuah gurih, potongan ayam, sayuran segar, dan bawang goreng.", "Rp25.000"),
        Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang", "Hidangan daging yang dimasak dengan rempah-rempah khas Indonesia, dengan rasa gurih dan pedas yang kaya serta tekstur daging yang lembut.", "Rp40.000"),
        )

    private val _meals = MutableLiveData<List<Recommendation>>().apply {
        value = recommendations
    }
    val meals: LiveData<List<Recommendation>> = _meals
}