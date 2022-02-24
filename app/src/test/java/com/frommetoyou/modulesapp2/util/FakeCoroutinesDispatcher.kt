package com.frommetoyou.modulesapp2

import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
fun provideFakeCoroutinesDispatcherProvider(
    main: CoroutineDispatcher? = null,
    computation: CoroutineDispatcher? = null,
    io: CoroutineDispatcher? = null
): CoroutinesDispatcherProvider {
    val sharedTestCoroutineDispatcher = TestCoroutineDispatcher()
    return CoroutinesDispatcherProvider(
        main ?: sharedTestCoroutineDispatcher,
        computation ?: sharedTestCoroutineDispatcher,
        io ?: sharedTestCoroutineDispatcher)
}