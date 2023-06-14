package com.dicoding.kostkater.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.kostkater.databinding.ActivityDetailBinding
import com.dicoding.kostkater.model.meals.DataItem
import com.dicoding.kostkater.ui.recipe.RecipeActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.floatingActionButton.setOnClickListener {
            finish()
        }

        setDetailData()
    }

    private fun setDetailData() {
        val meal = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MEAL, DataItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MEAL)
        }

        if (meal != null) {
            Glide.with(this)
                .load(meal.imgUrl)
                .into(binding.ivDetailPhoto)

            binding.tvDetailName.text = meal.name
            binding.tvHalal.text = if (meal.kehalalan) "Halal" else "Non-halal"
            binding.tvDetailDescription.text = meal.deskripsi
            binding.tvDetailPrice.text = meal.harga
            binding.tvCalorieValue.text = meal.nutrisi?.kalori.toString() + "kal"
            binding.tvCarboValue.text = meal.nutrisi?.karbohidrat.toString() + "g"
            binding.tvProteinValue.text = meal.nutrisi?.protein.toString() +"g"
            binding.tvFatValue.text = meal.nutrisi?.lemak.toString() + "g"

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