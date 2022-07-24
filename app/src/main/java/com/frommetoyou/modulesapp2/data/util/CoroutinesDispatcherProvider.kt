package com.frommetoyou.modulesapp2.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {
    constructor() : this(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)
}
