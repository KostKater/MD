package com.dicoding.kostkater.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kostkater.adapter.RecommendationAdapter
import com.dicoding.kostkater.databinding.FragmentHomeBinding
import com.dicoding.kostkater.model.Recommendation

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recommendations = listOf(
            Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten"),
            Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang"),
            Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten"),
            Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang"),
            Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten"),
            Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang"),
            Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten"),
            Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang"),
            Recommendation("https://i.pinimg.com/564x/6e/d5/99/6ed599196deefa2ef54d22c94ae2726a.jpg", "Sup Ayam Klaten"),
            Recommendation("https://www.indonesia.travel/content/dam/indtravelrevamp/en/trip-ideas/5-popular-indonesian-foods-you-can-make-at-home/i2.jpg", "Rendang"),
        )

        val adapter = RecommendationAdapter(recommendations)
        binding.rvRecommendation.adapter = adapter

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvRecommendation.layoutManager = layoutManager

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}