package com.dicoding.kostkater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kostkater.databinding.ItemInstructionBinding

class InstructionAdapter(private val listInstruction: List<String>) : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemInstructionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listInstruction[position], position)
    }

    override fun getItemCount() = listInstruction.size

    class ViewHolder(private val binding: ItemInstructionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(instruction: String, position: Int) {
            binding.tvItemStep.text = (position + 1).toString()
            binding.tvItemInstruction.text = instruction
        }
    }
}