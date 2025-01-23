package com.my.mediadownloader.platform
import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import java.util.prefs.Preferences


class JVMDataStore(
    private val preferences: Preferences
) : PlatformDataStore {

    override fun loadData(key: String): String? {
        return preferences.get(key, null)
    }

    override fun storeData(key: String, data: String) {
        preferences.put(key, data)
    }
}