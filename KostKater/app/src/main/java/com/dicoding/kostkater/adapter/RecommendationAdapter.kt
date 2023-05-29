package com.dicoding.kostkater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.kostkater.databinding.ItemRecommendationBinding
import com.dicoding.kostkater.model.Recommendation

class RecommendationAdapter(private val listRecommendation: List<Recommendation>) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listRecommendation[position])
    }

    override fun getItemCount() = listRecommendation.size

    class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
            internal fun bind(recommendation: Recommendation) {
                recommendation.run {
                    binding.tvItemName.text = name

                    Glide.with(binding.root.context)
                        .load(photoUrl)
                        .into(binding.ivItemPhoto)
                }
            }
    }
}