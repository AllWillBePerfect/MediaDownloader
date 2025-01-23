package com.my.mediadownloader.core.datastore.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

abstract class DataStoreExtractor<T : Any>(
    private val dataStore: PlatformDataStore,
    private val moshi: Moshi,
    private val key: String,
    private val defaultData: Container<T>,
    clazz: Class<T>? = null,
    type: Type? = null
) {

    init {
        require((clazz != null) xor (type != null)) {
            "You must provide either 'clazz' or 'type', but not both!"
        }
    }

    private val adapter: JsonAdapter<Container<T>> =
        if (clazz != null) moshi.adapter(
            Types.newParameterizedType(
                Container::class.java,
                clazz
            )
        ) else
            moshi.adapter(Types.newParameterizedType(Container::class.java, type))

    fun parseToJson(data: Container<T>) {
        val json = adapter.toJson(data)
        dataStore.storeData(key, json)
    }

    fun parseFromJson(): Container<T> {
        val data = dataStore.loadData(key)
        return if (data == null) defaultData else adapter.fromJson(data)!!
    }
}

data class Container<T>(val data: T)