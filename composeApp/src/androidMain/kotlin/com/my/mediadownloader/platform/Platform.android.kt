package com.my.mediadownloader.platform

import android.os.Build
import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.GlobalContext.get

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getPlatformDataStore(): PlatformDataStore = get().get()
actual fun runYtDlp(): Flow<String> = flow {  }
actual fun getYtDlpMeta(): Flow<String> = flow {  }