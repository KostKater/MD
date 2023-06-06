package com.dicoding.kostkater.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.kostkater.databinding.ItemRecommendationBinding
import com.dicoding.kostkater.model.Meal
import com.dicoding.kostkater.ui.detail.DetailActivity

class RecommendationAdapter(private val listMeal: List<Meal>) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMeal[position])
    }

    override fun getItemCount() = listMeal.size

    class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
            internal fun bind(meal: Meal) {
                meal.run {
                    binding.tvItemName.text = name
                    binding.tvItemPrice.text = price

                    Glide.with(binding.root.context)
                        .load(photoUrl)
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