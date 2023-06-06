package com.dicoding.kostkater.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.FragmentFilterSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.searchButton.setOnClickListener {
            savePreference()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilterSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun savePreference() {
        Log.d("SEARCH SHEET: ", binding.inputBudget.text.toString())
        dismiss()
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}