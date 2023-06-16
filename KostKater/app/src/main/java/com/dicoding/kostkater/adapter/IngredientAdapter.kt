package com.dicoding.kostkater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kostkater.databinding.ItemIngredientBinding

class IngredientAdapter(private val listIngredient: List<String>) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listIngredient[position])
    }

    override fun getItemCount() = listIngredient.size

    class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(ingredient: String) {
            binding.tvIngredient.text = "\u2022  $ingredient"
        }
    }
}