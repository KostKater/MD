package com.dicoding.kostkater.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.kostkater.R
import com.dicoding.kostkater.adapter.MealAdapter
import com.dicoding.kostkater.databinding.ActivityMainBinding
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.meals.DataItem
import com.dicoding.kostkater.ui.ViewModelFactory
import com.dicoding.kostkater.ui.dialog.PreferenceSheet
import com.dicoding.kostkater.ui.welcome.WelcomeActivity
import java.util.Calendar

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var tokenString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setGreeting()
        setupViewModel()

        val layoutManager1 = GridLayoutManager(this, 2)
        binding.rvRecommendation.layoutManager = layoutManager1

        val layoutManager2 = GridLayoutManager(this, 2)
        binding.rvAllMeals.layoutManager = layoutManager2

        binding.cvTips.setOnClickListener {
            PreferenceSheet().show(supportFragmentManager, "preferenceTag")
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[MainViewModel::class.java]

        mainViewModel.getToken().observe(this) { token ->
            if (token.isEmpty()) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
                return@observe
            }
            tokenString = token
        }

        mainViewModel.recommendation.observe(this) { recommendations ->
            setRecommendationData(recommendations)
        }

        mainViewModel.allMeal.observe(this) { meals ->
            setAllMealData(meals)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it, 1)
        }

        mainViewModel.isLoading2.observe(this) {
            showLoading(it, 2)
        }
    }

    private fun setRecommendationData(meals: List<DataItem?>) {
        val adapter = MealAdapter(meals)
        binding.rvRecommendation.adapter = adapter
    }

    private fun setAllMealData(meals: List<DataItem?>) {
        val adapter = MealAdapter(meals)
        binding.rvAllMeals.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean, progressBar: Int) {
        if (progressBar == 1) {
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        } else {
            if (isLoading) {
                binding.progressBar2.visibility = View.VISIBLE
            } else {
                binding.progressBar2.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.logout_confirm))
                    setMessage(getString(R.string.logout_confirm_desc))
                    setPositiveButton(getString(R.string.logout)) { _, _ ->
                        mainViewModel.logout()
                    }
                    setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
                true
            }

            else -> true
        }
    }

    private fun getGreeting(): String {
        val calendar = Calendar.getInstance()

        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 1..10 -> "pagi"
            in 11..14 -> "siang"
            in 15..18 -> "sore"
            else -> "malam"
        }
    }

    private fun setGreeting() {
        val greeting = "Selamat"
        val timeOfDay = getGreeting()
        val text = "$greeting $timeOfDay."
        val spannableString = SpannableString(text)

        val whiteColorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.white))
        spannableString.setSpan(whiteColorSpan, 0, greeting.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.selective_yellow))

        val startIndex = greeting.length
        val endIndex = startIndex + timeOfDay.length + 2
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvGreetings.text = spannableString
    }
}