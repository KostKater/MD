package com.dicoding.kostkater.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.FragmentIngredientSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class IngredientSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentIngredientSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.searchButton.setOnClickListener {
            savePreference()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIngredientSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun savePreference() {
        Log.d("INGREDIENT SHEET: ", binding.name.text.toString())
        dismiss()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}