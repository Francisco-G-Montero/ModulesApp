package com.frommetoyou.modulesapp2.presentation.ui.state

import android.graphics.Bitmap
import com.frommetoyou.modulesapp2.presentation.redux.State
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

data class WorkManagerViewState(
    val imgPathList: List<String> = listOf(),
    val errorMessage: String? = null,
) : State