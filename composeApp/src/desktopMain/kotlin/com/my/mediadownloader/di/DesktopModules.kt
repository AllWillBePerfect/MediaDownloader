package com.my.mediadownloader.di

import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import com.my.mediadownloader.platform.JVMDataStore
import org.koin.dsl.module
import java.util.prefs.Preferences

val dataStoreModule = module {
    single<Preferences> { Preferences.userRoot().node("preferences") }
    single<PlatformDataStore> { JVMDataStore(get()) }
}