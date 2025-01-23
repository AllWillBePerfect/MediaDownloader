package com.my.mediadownloader.core.datastore

import com.my.mediadownloader.core.datastore.base.Container
import com.my.mediadownloader.core.datastore.base.DataStoreExtractor
import com.my.mediadownloader.core.datastore.base.FlowableDataStore
import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import com.my.mediadownloader.core.datastore.base.SuperDataClass
import com.squareup.moshi.Moshi

class SuperDataStoreExtractor(
    dataStore: PlatformDataStore,
    moshi: Moshi,
) : DataStoreExtractor<SuperDataClass>(
    dataStore = dataStore,
    moshi = moshi,
    key = "super_data_key",
    defaultData = Container(SuperDataClass("super_default_value")),
    clazz = SuperDataClass::class.java
)
typealias SuperFlowableDataStore = FlowableDataStore<SuperDataClass>
