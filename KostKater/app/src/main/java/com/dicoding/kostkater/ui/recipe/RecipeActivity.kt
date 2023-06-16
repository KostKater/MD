package com.dicoding.kostkater.ui.recipe

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.kostkater.adapter.IngredientAdapter
import com.dicoding.kostkater.adapter.InstructionAdapter
import com.dicoding.kostkater.databinding.ActivityRecipeBinding
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.meals.Meal
import com.dicoding.kostkater.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.floatingActionButton2.setOnClickListener {
            finish()
        }

        setRecipeInfo()

        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvInstructions.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecipeInfo() {
        val meal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MEAL, Meal::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MEAL)
        }

        if (meal != null) {
            binding.tvRecipeName.text = meal.name

            Glide.with(this)
                .load(meal.imgUrl)
                .into(binding.ivRecipePhoto)

            setupViewModel(meal.name)
        }
    }

    private fun setupViewModel(mealName: String) {
        recipeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), mealName)
        )[RecipeViewModel::class.java]

        recipeViewModel.recipe.observe(this) { recipe ->
            if (recipe != null) {
                setIngredientData(recipe.ingredients)
                setInstructionData(recipe.instructions)
            }
        }

        recipeViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        recipeViewModel.message.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setIngredientData(ingredient: List<String>) {
        binding.tvIngredients.visibility = View.VISIBLE
        val adapter = IngredientAdapter(ingredient)
        binding.rvIngredients.adapter = adapter
    }

    private fun setInstructionData(instruction: List<String>) {
        binding.tvInstructions.visibility = View.VISIBLE
        val adapter = InstructionAdapter(instruction)
        binding.rvInstructions.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.progressBar2.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_MEAL = "extra_meal"
    }
}