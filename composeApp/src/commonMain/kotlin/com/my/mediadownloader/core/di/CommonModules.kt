package com.my.mediadownloader.core.di

import com.my.mediadownloader.Greeting
import com.my.mediadownloader.core.datastore.SuperDataStoreExtractor
import com.my.mediadownloader.core.datastore.SuperFlowableDataStore
import com.my.mediadownloader.core.datastore.base.DataStoreExtractor
import com.my.mediadownloader.core.datastore.base.FlowableDataStore
import com.my.mediadownloader.core.datastore.base.SuperDataClass
import com.my.mediadownloader.platform.Platform
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

val commonDataStoreModule = module {

    single<Moshi> { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    single<DataStoreExtractor<SuperDataClass>> { SuperDataStoreExtractor(get(), get()) }
    single<SuperFlowableDataStore> { FlowableDataStore(get()) }
    single<Platform> { Greeting().platform}
}