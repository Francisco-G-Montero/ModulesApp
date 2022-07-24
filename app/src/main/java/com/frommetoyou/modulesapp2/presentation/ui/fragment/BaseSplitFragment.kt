package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompat

open class BaseSplitFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { SplitCompat.install(it) }
    }
}
