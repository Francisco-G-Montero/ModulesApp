package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.frommetoyou.modulesapp2.data.model.CinemaModel
import com.frommetoyou.modulesapp2.databinding.FragmentMultipleTypeListBinding
import com.frommetoyou.modulesapp2.presentation.ui.adapter.CinemaAdapter
import com.frommetoyou.modulesapp2.presentation.viewmodel.MultipleTypeListViewModel

class MultipleTypeListFragment : Fragment() {
    private lateinit var binding: FragmentMultipleTypeListBinding
    private val viewModel: MultipleTypeListViewModel by viewModels()
    private lateinit var cinemaAdapter: CinemaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMultipleTypeListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cinemaAdapter = CinemaAdapter()
        val data = getData()
        cinemaAdapter.submitList(data)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
            adapter = cinemaAdapter
        }
    }

    private fun getData(): List<CinemaModel> = listOf(
        CinemaModel.Header(
            "Terror"
        ),
        CinemaModel.TerrorMovie(
            "Sangre hirviendo",
            Color.BLUE
        ),
        CinemaModel.Header(
            "Drama"
        ),
        CinemaModel.DramaMovie(
            "Suave",
            Color.RED
        ),
        CinemaModel.DramaMovie(
            "Paz",
            Color.RED
        )
    )
}
