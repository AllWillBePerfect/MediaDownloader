package com.my.mediadownloader

import com.my.mediadownloader.platform.getPlatform

class Greeting {
    val platform = getPlatform()


    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}