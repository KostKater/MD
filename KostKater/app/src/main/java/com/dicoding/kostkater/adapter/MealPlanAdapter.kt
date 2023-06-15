package com.dicoding.kostkater.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.kostkater.databinding.ItemMealplanBinding
import com.dicoding.kostkater.model.mealplan.MealPlan
import com.dicoding.kostkater.model.meals.Meal
import com.dicoding.kostkater.ui.detail.DetailActivity

class MealPlanAdapter(private val mealPlan: List<MealPlan?>) : RecyclerView.Adapter<MealPlanAdapter.ViewHolder>() {
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
        internal fun bind(mealPlan: MealPlan) {
            mealPlan.run {
                binding.tvDate.text = dateInTimestamp

                if (breakfast != null) {
                    Glide.with(binding.root.context)
                        .load(breakfast.imgUrl)
                        .into(binding.ivBreakfast)

                    binding.tvNameBreakfast.text = breakfast.name

                    binding.cvBreakfast.setOnClickListener {
                        val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MEAL, breakfast)
                        it.context.startActivity(moveWithObjectIntent)
                    }
                } else {
                    binding.cvBreakfast.visibility= View.GONE
                }

                if (lunch != null) {
                    Glide.with(binding.root.context)
                        .load(lunch.imgUrl)
                        .into(binding.ivLunch)

                    binding.tvNameLunch.text = lunch.name

                    binding.cvLunch.setOnClickListener {
                        val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MEAL, lunch)
                        it.context.startActivity(moveWithObjectIntent)
                    }
                } else {
                    binding.cvLunch.visibility= View.GONE
                }

                if (dinner != null) {
                    Glide.with(binding.root.context)
                        .load(dinner.imgUrl)
                        .into(binding.ivDinner)

                    binding.tvNameDinner.text = dinner.name

                    binding.cvDinner.setOnClickListener {
                        val moveWithObjectIntent = Intent(it.context, DetailActivity::class.java)
                        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MEAL, dinner)
                        it.context.startActivity(moveWithObjectIntent)
                    }
                } else {
                    binding.cvDinner.visibility= View.GONE
                }
            }
        }
    }
}