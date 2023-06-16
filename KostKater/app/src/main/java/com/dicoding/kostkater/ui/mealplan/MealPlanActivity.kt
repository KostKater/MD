package com.dicoding.kostkater.ui.mealplan

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kostkater.R
import com.dicoding.kostkater.adapter.MealPlanAdapter
import com.dicoding.kostkater.databinding.ActivityMealPlanBinding
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.mealplan.MealPlan
import com.dicoding.kostkater.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MealPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealPlanBinding

    private lateinit var mealPlanViewModel: MealPlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()

        binding.rvMealplan.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        mealPlanViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), null)
        )[MealPlanViewModel::class.java]

        mealPlanViewModel.mealPlan.observe(this) { mealPlan ->
            if (mealPlan != null) {
                setMealPlan(mealPlan)
            } else {
                AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.session))
                    setPositiveButton(getString(R.string.next)) { _, _ ->
                        mealPlanViewModel.logout()
                        finish()
                    }
                    create()
                    show()
                }
            }
        }

        mealPlanViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mealPlanViewModel.message.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMealPlan(mealPlan: List<MealPlan>) {
        if (mealPlan.isEmpty()) {
            binding.tvNoPlan.visibility = View.VISIBLE
        } else {
            binding.tvNoPlan.visibility = View.GONE
            val adapter = MealPlanAdapter(mealPlan)
            binding.rvMealplan.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }
}