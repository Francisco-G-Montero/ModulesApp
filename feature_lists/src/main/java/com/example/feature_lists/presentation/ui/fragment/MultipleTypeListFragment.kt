package com.example.feature_lists.presentation.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_lists.data.model.CinemaModel
import com.example.feature_lists.databinding.FragmentMultipleTypeListBinding
import com.example.feature_lists.presentation.ui.adapter.CinemaAdapter
import com.example.feature_lists.presentation.viewmodel.MultipleListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MultipleTypeListFragment : Fragment() {
    private lateinit var binding: FragmentMultipleTypeListBinding
    private lateinit var cinemaAdapter: CinemaAdapter
    private val viewModel: MultipleListViewModel by viewModels()

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
        binding.btnMessage.setOnClickListener {
            viewModel.getAppImage()
        }
        viewModel.appImage.observe(viewLifecycleOwner) { bitmap ->
            binding.imgApp.setImageBitmap(bitmap)
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
