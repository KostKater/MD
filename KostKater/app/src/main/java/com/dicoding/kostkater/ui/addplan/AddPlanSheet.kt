package com.dicoding.kostkater.ui.addplan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kostkater.R
import com.dicoding.kostkater.databinding.FragmentAddPlanSheetBinding
import com.dicoding.kostkater.model.UserPreference
import com.dicoding.kostkater.ui.ViewModelFactory
import com.dicoding.kostkater.ui.preference.PreferenceSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar


private val Context.dataStore by preferencesDataStore("app_preferences")

class AddPlanSheet(private val mealName: String) : BottomSheetDialogFragment() {
    private lateinit var addPlanViewModel: AddPlanViewModel
    private lateinit var binding: FragmentAddPlanSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLimitDate()
        setupViewModel()
        setupButtonAction()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddPlanSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupViewModel() {
        addPlanViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), null)
        )[AddPlanViewModel::class.java]

        addPlanViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        addPlanViewModel.message.observe(this) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupButtonAction() {
        binding.saveButton.setOnClickListener {
            val day = binding.datePicker.dayOfMonth
            val month = binding.datePicker.month
            val year = binding.datePicker.year
            val calendar = Calendar.getInstance()
            calendar[year, month] = day

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = sdf.format(calendar.time)

            val groupMeal = if (binding.rbBreakfast.isChecked) {
                "breakfast"
            } else if (binding.rbLunch.isChecked) {
                "lunch"
            } else {
                "dinner"
            }

            addPlanViewModel.addMealPlan(mealName, formattedDate, groupMeal)
        }
    }

    private fun setLimitDate() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        binding.datePicker.minDate = calendar.timeInMillis

        // Set the maximum date to 7 days from today
        calendar.add(Calendar.DAY_OF_MONTH, 7)
        val maxYear = calendar.get(Calendar.YEAR)
        val maxMonth = calendar.get(Calendar.MONTH)
        val maxDay = calendar.get(Calendar.DAY_OF_MONTH)
        binding.datePicker.maxDate = calendar.timeInMillis

        // Set the current date on the date picker
        binding.datePicker.init(currentYear, currentMonth, currentDay, null)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }
}