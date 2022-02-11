package com.frommetoyou.modulesapp2.presentation.ui.state

import com.frommetoyou.modulesapp2.presentation.redux.State
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

data class MainViewState(
    val storedText: String = "Not fetched yet",
    val showProgressbar: Boolean = false,
    val errorMessage: String? = null,
    val dynamicLinkCreated: Boolean = false,
    val btnSelected: String = "",
    val getFlowText: Flow<String>? = null
) : State