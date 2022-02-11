package com.frommetoyou.modulesapp2.presentation.redux

interface Reducer<S: State, A: Action> {
    /**
     * Dado un [currentState] y un [action] de un usuario se produce un nuevo state
     *
     * Nos asegura que cada state se asocia a una action o intent especifica que lanzó un usuario
     * */
    fun reduce(currentState: S, action: A): S
}