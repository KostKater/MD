package com.dicoding.kostkater.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.ActivityDetailBinding
import com.dicoding.kostkater.ui.login.LoginActivity
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
        val name = intent.getStringExtra(EXTRA_NAME)
        val photoUrl = intent.getStringExtra(EXTRA_PHOTO_URL)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val price = intent.getStringExtra(EXTRA_PRICE)
        val calorie = intent.getStringExtra(EXTRA_CALORIE)
        val carbo = intent.getStringExtra(EXTRA_CARBO)
        val protein = intent.getStringExtra(EXTRA_PROTEIN)
        val fat = intent.getStringExtra(EXTRA_FAT)

        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivDetailPhoto)

        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description
        binding.tvDetailPrice.text = price
        binding.tvCalorieValue.text = calorie
        binding.tvCarboValue.text = carbo
        binding.tvProteinValue.text = protein
        binding.tvFatValue.text = fat

        binding.recipeButton.setOnClickListener {
            val moveWithObjectIntent = Intent(this, RecipeActivity::class.java)
            moveWithObjectIntent.putExtra(RecipeActivity.EXTRA_NAME, name)
            moveWithObjectIntent.putExtra(RecipeActivity.EXTRA_PHOTO_URL, photoUrl)
            startActivity(moveWithObjectIntent)
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
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO_URL = "extra_photo_url"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_CALORIE = "extra_calorie"
        const val EXTRA_CARBO = "extra_carbo"
        const val EXTRA_PROTEIN = "extra_protein"
        const val EXTRA_FAT = "extra_fat"
    }
}