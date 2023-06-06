package com.dicoding.kostkater.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.ActivityDetailBinding
import com.dicoding.kostkater.model.Meal
import com.dicoding.kostkater.ui.recipe.RecipeActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail)

        setDetailData()
    }

    private fun setDetailData() {
        val meal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MEAL, Meal::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MEAL)
        }

        if (meal != null) {
            Glide.with(this)
                .load(meal.photoUrl)
                .into(binding.ivDetailPhoto)

            binding.tvDetailName.text = meal.name
            binding.tvDetailDescription.text = meal.description
            binding.tvDetailPrice.text = meal.price
            binding.tvCalorieValue.text = meal.calorie
            binding.tvCarboValue.text = meal.carbo
            binding.tvProteinValue.text = meal.protein
            binding.tvFatValue.text = meal.fat

            binding.recipeButton.setOnClickListener {
                val moveWithObjectIntent = Intent(this, RecipeActivity::class.java)
                moveWithObjectIntent.putExtra(RecipeActivity.EXTRA_MEAL, meal)
                startActivity(moveWithObjectIntent)
            }
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