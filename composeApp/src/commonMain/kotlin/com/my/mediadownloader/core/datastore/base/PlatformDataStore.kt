package com.my.mediadownloader.core.datastore.base

interface PlatformDataStore {

    fun loadData(key: String): String?
    fun storeData(key: String,data: String)
}
