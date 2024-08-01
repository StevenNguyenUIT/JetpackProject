package com.nhinhnguyenuit.jetpackproject.ui.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class StateFlowTestObserver<T>(private val stateFlow: StateFlow<T>) {

    private val values = mutableListOf<T>()
    private val job: Job

    init {
        job = GlobalScope.launch {
            stateFlow.collect {
                values.add(it)
            }
        }
    }

    fun values() = values

    suspend fun awaitValue(): T {
        while (values.isEmpty()) {
            delay(10)
        }
        return values.first()
    }

    fun close() {
        job.cancel()
    }
}

fun <T> StateFlow<T>.test() = StateFlowTestObserver(this)
