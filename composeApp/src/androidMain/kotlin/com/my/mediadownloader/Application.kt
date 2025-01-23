package com.my.mediadownloader

import android.app.Application
import com.my.mediadownloader.core.di.commonDataStoreModule
import com.my.mediadownloader.di.dataStoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(commonDataStoreModule, dataStoreModule)
        }
    }
}

