package com.frommetoyou.modulesapp2.presentation.ui.state

import com.frommetoyou.modulesapp2.data.model.Facts
import com.frommetoyou.modulesapp2.presentation.redux.State

data class TDDViewState(
    val loading: Boolean = false,
    val factsList: List<Facts> = listOf()
) : State
