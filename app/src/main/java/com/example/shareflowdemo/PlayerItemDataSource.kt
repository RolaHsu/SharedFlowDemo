package com.example.shareflowdemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PlayerItemDataSource {
    private val globalCoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val _sharedQueueFlow = MutableSharedFlow<List<String>>()
    val sharedQueueFlow = _sharedQueueFlow.asSharedFlow()

    private val _sharedCurrentIndexFlow = MutableSharedFlow<Int>()
    val sharedCurrentIndexFlow = _sharedCurrentIndexFlow.asSharedFlow()

    var items = mutableListOf("A", "B", "C", "D")

    fun updateCurrentIndex(index: Int) {
        globalCoroutineScope.launch {
            _sharedCurrentIndexFlow.emit(index)
        }
    }

    fun updateQueueFlow(index: Int, value: String) {
        items[index] = value
        globalCoroutineScope.launch {
            _sharedQueueFlow.emit(items)
        }
    }

}