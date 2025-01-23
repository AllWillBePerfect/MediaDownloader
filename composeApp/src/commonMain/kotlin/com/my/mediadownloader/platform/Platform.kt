package com.my.mediadownloader.platform

import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import kotlinx.coroutines.flow.Flow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getPlatformDataStore(): PlatformDataStore
expect fun getYtDlpMeta(): Flow<String>
expect fun runYtDlp(): Flow<String>
