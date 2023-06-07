package com.dicoding.kostkater.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kostkater.R
import com.dicoding.kostkater.adapter.RecommendationAdapter
import com.dicoding.kostkater.databinding.ActivityMainBinding
import com.dicoding.kostkater.model.Meal
import com.dicoding.kostkater.ui.dialog.FilterSheet
import com.dicoding.kostkater.ui.dialog.PreferenceSheet
import com.dicoding.kostkater.ui.home.HomeViewModel
import com.dicoding.kostkater.ui.login.LoginActivity
import com.dicoding.kostkater.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvRecommendation.layoutManager = layoutManager

        binding.preferenceButton.setOnClickListener {
            PreferenceSheet().show(supportFragmentManager, "preferenceTag")
        }

        binding.budgetButton.setOnClickListener {
            FilterSheet().show(supportFragmentManager, "budgetTag")
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.meals.observe(this) { recommendations ->
            setRecommendationData(recommendations)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setRecommendationData(meals: List<Meal>) {
        val adapter = RecommendationAdapter(meals)
        binding.rvRecommendation.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                true
            }

            else -> true
        }
    }
}