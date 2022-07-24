package com.frommetoyou.modulesapp2.presentation.ui.state

import com.example.core.data.model.Facts
import com.frommetoyou.modulesapp2.presentation.redux.State

data class TDDViewState(
    val loading: Boolean = false,
    val factsList: Facts? = null
) : State
