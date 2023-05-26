package com.dicoding.kostkater.ui.mealplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kostkater.databinding.FragmentMealplanBinding

class MealPlanFragment : Fragment() {

    private var _binding: FragmentMealplanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mealPlanViewModel =
            ViewModelProvider(this)[MealPlanViewModel::class.java]

        _binding = FragmentMealplanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMealPlan
        mealPlanViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}