package com.dicoding.kostkater.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.FragmentPreferenceSheetBinding
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.ui.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private val Context.dataStore by preferencesDataStore("app_preferences")
class PreferenceSheet : BottomSheetDialogFragment() {
    private lateinit var preferenceViewModel: PreferenceViewModel
    private lateinit var binding: FragmentPreferenceSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupAction()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPreferenceSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupViewModel() {
        preferenceViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[PreferenceViewModel::class.java]
    }

    private fun setupAction() {
        binding.saveButton.setOnClickListener {
            val halal = binding.switchHalal.isChecked

            val allergies = mutableListOf<String>()
            val eggAllergic = binding.checkboxEgg
            val peanutAllergic = binding.checkboxPeanut
            val soybeanAllergic = binding.checkboxSoybean
            val seafoodAllergic = binding.checkboxSeafood
            val shrimpAllergic = binding.checkboxShrimp
            val milkAllergic = binding.checkboxMilk
            val wheatAllergic = binding.checkboxWheat

            if (eggAllergic.isChecked) {
                allergies.add(eggAllergic.text.toString())
            }
            if (peanutAllergic.isChecked) {
                allergies.add(peanutAllergic.text.toString())
            }
            if (soybeanAllergic.isChecked) {
                allergies.add(soybeanAllergic.text.toString())
            }
            if (seafoodAllergic.isChecked) {
                allergies.add(seafoodAllergic.text.toString())
            }
            if (shrimpAllergic.isChecked) {
                allergies.add(shrimpAllergic.text.toString())
            }
            if (milkAllergic.isChecked) {
                allergies.add(milkAllergic.text.toString())
            }
            if (wheatAllergic.isChecked) {
                allergies.add(wheatAllergic.text.toString())
            }

            val priceMinString = binding.inputMinPrice.text.toString()
            val priceMin = if (priceMinString.isEmpty()) null else priceMinString.toInt()
            val priceMaxString = binding.inputMaxPrice.text.toString()
            val priceMax = if (priceMaxString.isEmpty()) null else priceMaxString.toInt()

            val ingredients = binding.inputIngredient.text.toString()
            val listIngredient = ingredients.split(Regex(",(?=\\s*\\w)"))

            preferenceViewModel.savePreference(halal, allergies, priceMin, priceMax, listIngredient)
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}