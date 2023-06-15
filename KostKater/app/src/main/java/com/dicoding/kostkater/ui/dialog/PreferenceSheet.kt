package com.dicoding.kostkater.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.FragmentPreferenceSheetBinding
import com.dicoding.kostkater.helper.NumberTextWatcherForThousand
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.model.user.Preference
import com.dicoding.kostkater.ui.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private val Context.dataStore by preferencesDataStore("app_preferences")
class PreferenceSheet(private val token: String) : BottomSheetDialogFragment() {
    private lateinit var preferenceViewModel: PreferenceViewModel
    private lateinit var binding: FragmentPreferenceSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupButtonAction(token)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPreferenceSheetBinding.inflate(inflater, container, false)

        binding.inputMinPrice.addTextChangedListener(NumberTextWatcherForThousand(binding.inputMinPrice))
        binding.inputMaxPrice.addTextChangedListener(NumberTextWatcherForThousand(binding.inputMaxPrice))
        return binding.root
    }

    private fun setupViewModel() {
        preferenceViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), null)
        )[PreferenceViewModel::class.java]

        preferenceViewModel.userPreference.observe(this) { userPreference ->
            if (userPreference != null) {
                setPreferenceData(userPreference)
            }
        }

        preferenceViewModel.isLoadingData.observe(this) {
            showLoading(it, binding.progressBar)
        }

        preferenceViewModel.isLoadingSave.observe(this) {
            showLoading(it, binding.progressBar2)
        }

        preferenceViewModel.message.observe(this) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setPreferenceData(userPreference: Preference) {
        binding.switchHalal.isChecked = userPreference.eatHalal

        for (allergy in userPreference.allergies) {
            val userAllergy = allergy.lowercase()
            setCheckbox(userAllergy, "telur", binding.checkboxEgg)
            setCheckbox(userAllergy, "kacang", binding.checkboxPeanut)
            setCheckbox(userAllergy, "kedelai", binding.checkboxSoybean)
            setCheckbox(userAllergy, "seafood", binding.checkboxSeafood)
            setCheckbox(userAllergy, "udang", binding.checkboxShrimp)
            setCheckbox(userAllergy, "susu", binding.checkboxMilk)
            setCheckbox(userAllergy, "gandum", binding.checkboxWheat)
        }

        binding.inputMinPrice.setText(userPreference.priceMin.toString())
        binding.inputMaxPrice.setText(userPreference.priceMax.toString())

        binding.inputIngredient.setText(userPreference.ingredients.joinToString(", "))
    }

    private fun setCheckbox(userAllergy: String, allergy: String, checkBox: CheckBox) {
        if (userAllergy == allergy) checkBox.isChecked = true
    }

    private fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun setupButtonAction(token: String) {
        binding.saveButton.setOnClickListener {
            val halal = binding.switchHalal.isChecked

            val allergies = mutableListOf<String>()
            addAllergy(binding.checkboxEgg, allergies)
            addAllergy(binding.checkboxPeanut, allergies)
            addAllergy(binding.checkboxSoybean, allergies)
            addAllergy(binding.checkboxSeafood, allergies)
            addAllergy(binding.checkboxShrimp, allergies)
            addAllergy(binding.checkboxMilk, allergies)
            addAllergy(binding.checkboxWheat, allergies)

            val priceMinString = NumberTextWatcherForThousand.trimCommaOfString(binding.inputMinPrice.text.toString())
            val priceMin = if (priceMinString.isEmpty()) 0 else priceMinString.toInt()

            val priceMaxString = NumberTextWatcherForThousand.trimCommaOfString(binding.inputMaxPrice.text.toString())
            val priceMax = if (priceMaxString.isEmpty()) 999999999 else priceMaxString.toInt()

            val ingredients = binding.inputIngredient.text.toString()
            val listIngredient = ingredients.split(Regex(",(?=\\s*\\w)"))

            preferenceViewModel.savePreference(token, halal, allergies, priceMin, priceMax, listIngredient)
            setFragmentResult(MY_REQUEST_KEY, Bundle())
        }
    }

    private fun addAllergy(checkBox: CheckBox, allergies: MutableList<String>) {
        if (checkBox.isChecked) allergies.add(checkBox.text.toString())
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    companion object {
        const val MY_REQUEST_KEY = "MY_REQUEST_KEY"
    }
}