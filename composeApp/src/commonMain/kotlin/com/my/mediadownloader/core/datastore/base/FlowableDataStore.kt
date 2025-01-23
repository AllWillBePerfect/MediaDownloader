package com.my.mediadownloader.core.datastore.base

import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FlowableDataStore<T : Any>(
    private val dataStoreExtractor: DataStoreExtractor<T>
) {
    private val _stateFlow = MutableStateFlow(getDefault())
    val stateFlow: StateFlow<T> = _stateFlow.asStateFlow()

    private fun getDefault(): T {
        return dataStoreExtractor.parseFromJson().data
    }

    suspend fun save(value: T) {
        withContext(Dispatchers.IO) {
            dataStoreExtractor.parseToJson(Container(value))
        }
        _stateFlow.update { value }
    }

    fun load(): SharedFlow<T> = _stateFlow.asSharedFlow()
}

data class SuperDataClass(val superValue: String)

