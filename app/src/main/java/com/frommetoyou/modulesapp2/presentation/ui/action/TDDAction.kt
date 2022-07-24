package com.frommetoyou.modulesapp2.presentation.ui.action

import com.frommetoyou.modulesapp2.presentation.redux.Action

/**
 * Todas las acciones posibles
 * */
sealed class TDDAction : Action {
    object OnCallApiClicked : TDDAction()
}
