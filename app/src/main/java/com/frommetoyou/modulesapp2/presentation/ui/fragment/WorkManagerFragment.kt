package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.frommetoyou.modulesapp2.databinding.FragmentWorkManagerBinding
import com.frommetoyou.modulesapp2.presentation.ui.adapter.ImgDownloadedAdapter
import com.frommetoyou.modulesapp2.presentation.ui.state.WorkManagerViewState
import com.frommetoyou.modulesapp2.presentation.viewmodel.WorkManagerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WorkManagerFragment : Fragment() {
    private lateinit var binding: FragmentWorkManagerBinding
    private val viewModel: WorkManagerViewModel by viewModels()
    private var imgDownloadedAdapter: ImgDownloadedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkManagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLeafAdapter()
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
        viewModel.imgFilePathList.observe(viewLifecycleOwner) {
            updateImgList(it)
        }
        viewModel.checkForStoredImg()
        binding.btnDownload.setOnClickListener {
            viewModel.onDownloadClicked()
        }
    }

    private fun setupLeafAdapter() {
        val adapterVal = ImgDownloadedAdapter()
        imgDownloadedAdapter = adapterVal
        with(binding.recyclerView) {
            adapter = adapterVal
            imgDownloadedAdapter?.submitList(listOf())
        }
    }

    private fun processViewState(viewState: WorkManagerViewState) {
        updateImgList(viewState.imgPathList)
    }

    private fun updateImgList(imgPathList: List<String>) {
        if (imgPathList.isNotEmpty()) {
            imgDownloadedAdapter?.submitList(imgPathList)
        }
    }
}
