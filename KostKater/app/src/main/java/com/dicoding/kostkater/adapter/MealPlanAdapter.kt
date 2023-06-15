package com.dicoding.kostkater.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.kostkater.databinding.ItemMealplanBinding
import com.dicoding.kostkater.model.meals.Meal
import com.dicoding.kostkater.ui.detail.DetailActivity

class MealPlanAdapter(private val mealPlan: List<Meal?>) : RecyclerView.Adapter<MealPlanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemMealplanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mealPlan[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = mealPlan.size

    class ViewHolder(private val binding: ItemMealplanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(mealPlan: Meal) {
            mealPlan.run {
                binding.tvItemName.text = name
                binding.tvDate.text = "Senin, 20 Juni 2023"
                binding.tvType.text = "Sarapan"

                Glide.with(binding.root.context)
                    .load(imgUrl)
                    .into(binding.ivItemPhoto)

                binding.root.setOnClickListener {
                    val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MEAL, mealPlan)
                    it.context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}