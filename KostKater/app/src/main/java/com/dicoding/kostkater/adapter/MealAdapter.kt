package com.dicoding.kostkater.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.kostkater.databinding.ItemRecommendationBinding
import com.dicoding.kostkater.model.meals.Meal
import com.dicoding.kostkater.ui.detail.DetailActivity

class MealAdapter(private val listMeal: List<Meal?>) : RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listMeal[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = listMeal.size

    class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
            internal fun bind(meal: Meal) {
                meal.run {
                    binding.tvItemName.text = name
                    binding.tvItemPrice.text = harga
                    binding.tvHalal.text = if (kehalalan) "Halal" else "Non-halal"

                    Glide.with(binding.root.context)
                        .load(imgUrl)
                        .into(binding.ivItemPhoto)

                    binding.root.setOnClickListener {
                        val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MEAL, meal)
                        it.context.startActivity(moveWithObjectIntent)
                    }
                }
            }
    }
}