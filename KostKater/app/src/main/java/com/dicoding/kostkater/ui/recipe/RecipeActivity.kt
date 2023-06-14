package com.dicoding.kostkater.ui.recipe

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.kostkater.R
import com.dicoding.kostkater.adapter.IngredientAdapter
import com.dicoding.kostkater.adapter.InstructionAdapter
import com.dicoding.kostkater.databinding.ActivityRecipeBinding
import com.dicoding.kostkater.model.meals.DataItem

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
        setupViewModel()

        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvInstructions.layoutManager = LinearLayoutManager(this)
    }

    private fun setRecipeInfo() {
        val meal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MEAL, DataItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MEAL)
        }

        if (meal != null) {
            binding.tvRecipeName.text = meal.name

            Glide.with(this)
                .load(meal.imgUrl)
                .into(binding.ivRecipePhoto)
        }
    }

    private fun setupViewModel() {
        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        recipeViewModel.recipe.observe(this) { recipe ->
            setIngredientData(recipe.ingredients)
            setInstructionData(recipe.instructions)
        }

        recipeViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setIngredientData(ingredient: List<String>) {
        binding.tvIngredients.text = getString(R.string.ingredients)
        val adapter = IngredientAdapter(ingredient)
        binding.rvIngredients.adapter = adapter
    }

    private fun setInstructionData(instruction: List<String>) {
        binding.tvInstructions.text = getString(R.string.instructions)
        val adapter = InstructionAdapter(instruction)
        binding.rvInstructions.adapter = adapter
        binding.tvBonApetit.text = getString(R.string.bon_apetit)
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
        const val EXTRA_MEAL = "extra_meal"
    }
}