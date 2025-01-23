package com.my.mediadownloader.platform
import android.content.SharedPreferences
import com.my.mediadownloader.core.datastore.base.PlatformDataStore

class SharedPrefDataStore(private val sharedPreferences: SharedPreferences) : PlatformDataStore {


    override fun loadData(key: String): String? {
        val json = sharedPreferences.getString(key, null)
        return json
    }

    override fun storeData(key: String, data: String) {
        sharedPreferences.edit().putString(key, data).commit()
    }
}