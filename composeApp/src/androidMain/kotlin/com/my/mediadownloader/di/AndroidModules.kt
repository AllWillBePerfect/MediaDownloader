package com.my.mediadownloader.di

import android.content.Context
import android.content.SharedPreferences
import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import com.my.mediadownloader.platform.SharedPrefDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single<SharedPreferences> { androidContext().getSharedPreferences("shar_pref", Context.MODE_PRIVATE) }
    single<PlatformDataStore> { SharedPrefDataStore(get()) }
}
