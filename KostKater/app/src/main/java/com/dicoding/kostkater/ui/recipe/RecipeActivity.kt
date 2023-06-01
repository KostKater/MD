package com.dicoding.kostkater.ui.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.kostkater.R
import com.dicoding.kostkater.adapter.IngredientAdapter
import com.dicoding.kostkater.adapter.RecommendationAdapter
import com.dicoding.kostkater.databinding.ActivityRecipeBinding
import com.dicoding.kostkater.model.Recommendation
import com.dicoding.kostkater.ui.detail.DetailActivity
import com.dicoding.kostkater.ui.home.HomeViewModel

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.recipe)

        setRecipeInfo()
        setupViewModel()

        val layoutManager = LinearLayoutManager(this)
        binding.rvIngredients.layoutManager = layoutManager
    }

    private fun setRecipeInfo() {
        val name = intent.getStringExtra(DetailActivity.EXTRA_NAME)
        val photoUrl = intent.getStringExtra(DetailActivity.EXTRA_PHOTO_URL)

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivRecipePhoto)

        binding.tvRecipeName.text = name
    }

    private fun setupViewModel() {
        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        recipeViewModel.recipe.observe(this) { recipe ->
            setIngredientData(recipe.ingredients)
        }
    }

    private fun setIngredientData(ingredient: List<String>) {
        val adapter = IngredientAdapter(ingredient)
        binding.rvIngredients.adapter = adapter
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
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO_URL = "extra_photo_url"
    }
}