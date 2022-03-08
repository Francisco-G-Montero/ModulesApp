package com.frommetoyou.modulesapp2.presentation.ui.state

import com.frommetoyou.modulesapp2.presentation.redux.State

data class WorkManagerViewState(
    val imgPathList: List<String> = listOf(),
    val errorMessage: String? = null,
) : State
