package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.frommetoyou.modulesapp2.databinding.FragmentTDDBinding
import com.frommetoyou.modulesapp2.presentation.ui.state.TDDViewState
import com.frommetoyou.modulesapp2.presentation.viewmodel.TDDViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TDDFragment : Fragment() {
    private lateinit var binding: FragmentTDDBinding
    private val viewModel: TDDViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTDDBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewModel.callAPI()
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
        viewModel.communicationWithCore()
    }

    private fun processViewState(viewState: TDDViewState) {
        var text = "vacio"
        viewState.factsList?.entries?.forEach { entry ->
            text += (entry.Description + "\n")
        }
        binding.tvFacts.text = text
    }
}
