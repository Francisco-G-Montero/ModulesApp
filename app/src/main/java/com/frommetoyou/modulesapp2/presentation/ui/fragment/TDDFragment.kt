package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frommetoyou.modulesapp2.databinding.FragmentTDDBinding

class TDDFragment : Fragment() {
    private lateinit var binding: FragmentTDDBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTDDBinding.inflate(layoutInflater)
        return binding.root
    }
}
