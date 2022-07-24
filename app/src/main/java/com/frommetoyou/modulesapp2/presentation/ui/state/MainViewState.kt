package com.frommetoyou.modulesapp2.presentation.ui.state

import com.frommetoyou.modulesapp2.presentation.redux.State

data class MainViewState(
    val storedText: String = "Not fetched yet",
    val showProgressbar: Boolean = false,
    val errorMessage: String? = null,
    val dynamicLinkCreated: Boolean = false,
    val btnSelected: String = "",
    val getFlowText: String = "",
    val dynamicLinkBtnData: String = ""
) : State
