package com.my.mediadownloader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.my.mediadownloader.core.di.commonDataStoreModule
import com.my.mediadownloader.di.dataStoreModule
import com.my.mediadownloader.screens.main.MainScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun main() = application {
    startKoin {
        modules(commonDataStoreModule, dataStoreModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "MediaDownloader",
    ) { MainScreen() }
}

@Composable
fun desktopApp() {

    val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val iitems = items.collectAsState()

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                scope.launch {
                    _items.value = emptyList()
                    runYtDlp().collect {
                        println(it)
                        _items.value += it
                        listState.animateScrollToItem(_items.value.lastIndex)
                    }
                }
            }) {
                Text("Click me!")
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(iitems.value) { item ->
                    Text(text = item)
                }
            }

        }
    }

}


fun runYtDlp(): Flow<String> = flow {
    try {
        // Путь к yt-dlp (укажите полный путь, если требуется)
        val ytDlpPath = "yt-dlp"
        val outputFileName = "%(title)s.mp4"
        val outputDir = "${System.getProperty("user.home")}\\yt-dlp\\$outputFileName"
        // Команды и аргументы
        val processBuilder = ProcessBuilder(
            ytDlpPath,
            "-f",
            "bestvideo[ext=mp4]+bestaudio[ext=webm]",
            "-o",
            "%(title)s.%(ext)s",
            "https://www.youtube.com/watch?v=dFuigmebc_I",
            "--output", outputDir
        )

//             Установить рабочую директорию, если нужно
        processBuilder.directory(File("src/desktopMain/assets/"));

        // Перенаправить потоки
        processBuilder.redirectErrorStream(true)

        // Запуск процесса
        val process = processBuilder.start()

        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
//                    println(line)
                emit(line!!)
            }
        }
        // Ожидание завершения процесса
        val exitCode = process.waitFor()
//            println("Process exited with code: $exitCode")
        emit("Process exited with code: $exitCode")
    } catch (e: Exception) {
        emit("ERROR: $e")
    }
}.flowOn(Dispatchers.IO)
