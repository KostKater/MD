package com.dicoding.kostkater.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.kostkater.adapter.RecommendationAdapter
import com.dicoding.kostkater.databinding.FragmentHomeBinding
import com.dicoding.kostkater.model.Recommendation

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupViewModel()

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvRecommendation.layoutManager = layoutManager

        return root
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel.meals.observe(viewLifecycleOwner) { recommendations ->
            setRecommendationData(recommendations)
        }
    }

    private fun setRecommendationData(recommendations: List<Recommendation>) {
        val adapter = RecommendationAdapter(recommendations)
        binding.rvRecommendation.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}