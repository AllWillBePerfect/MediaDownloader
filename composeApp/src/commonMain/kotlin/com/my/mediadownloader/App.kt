package com.my.mediadownloader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.my.mediadownloader.core.datastore.SuperFlowableDataStore
import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import com.my.mediadownloader.core.datastore.base.SuperDataClass
import kotlinx.coroutines.launch
import mediadownloader.composeapp.generated.resources.Res
import mediadownloader.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.my.mediadownloader.platform.Platform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.parameter.parametersOf
import org.koin.core.context.GlobalContext.get
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.reflect.KClass

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var textState by remember { mutableStateOf("") }
        val qwerty: SuperDataClass by SampleApplication.superDataStore.load()
            .collectAsState(SuperDataClass("bbbbbbb"))
        val scope = rememberCoroutineScope()
        val viewModel: ExperimentalViewModel = viewModel(factory = MyViewModelFactory(get().get()))
        val qwe = viewModel.uiState.collectAsState().value
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
            Text(qwe)
            Button(onClick = {
                val data = SampleApplication.pref.loadData("hello")
                textState = data ?: "Data not found"
                scope.launch {
                    SampleApplication.superDataStore.save(SuperDataClass("aaaaaaa"))
                }
                viewModel.updateState("update")
            }) {}
        }
    }
}

object SampleApplication : KoinComponent {
    val platform: Platform by inject()
    val pref: PlatformDataStore by inject()
    val superDataStore: SuperFlowableDataStore by inject()
}

class ExperimentalViewModel(
    private val platform: Platform
) : ViewModel() {

    private val _uiState = MutableStateFlow(platform.name)
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    fun updateState(newState: String) {
        _uiState.value = newState
    }
}

class MyViewModelFactory(private val platform: Platform) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val javaClass = modelClass.java
        if (ExperimentalViewModel::class.java.isAssignableFrom(javaClass)) {
            @Suppress("UNCHECKED_CAST")
            return ExperimentalViewModel(platform) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}



