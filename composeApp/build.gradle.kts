import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-android:3.5.0")

            implementation(libs.moshi)
            implementation(libs.moshi.kotlin)
            implementation(libs.moshi.adapters)
            implementation("io.coil-kt.coil3:coil-compose:3.0.4")


        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation("io.insert-koin:koin-core:3.5.0")
//            implementation("io.insert-koin:koin-androidx-compose:3.5.0")
//            implementation("io.insert-koin:koin-androidx-viewmodel:3.5.0")

            implementation(libs.moshi)
            implementation(libs.moshi.kotlin)
            implementation(libs.moshi.adapters)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
            implementation(compose.animation)
//            implementation("moe.tlaster:precompose:1.3.15")
//            api("moe.tlaster:precompose-molecule:1.3.15") // For Molecule intergration
//
//            api("moe.tlaster:precompose-viewmodel:1.3.15") // For ViewModel intergration
//
//            api("moe.tlaster:precompose-koin:1.3.15") // For Koin intergration
            implementation(compose.material3)
            implementation("io.coil-kt.coil3:coil-compose:3.0.4")


        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation("io.insert-koin:koin-core:3.5.0")
            implementation(libs.moshi)
            implementation(libs.moshi.kotlin)
            implementation(libs.moshi.adapters)
            implementation("io.coil-kt.coil3:coil-compose:3.0.4")


        }
    }
}

android {
    namespace = "com.my.mediadownloader"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.my.mediadownloader"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.constraintlayout)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.my.mediadownloader.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.my.mediadownloader"
            packageVersion = "1.0.0"
        }
    }
}
