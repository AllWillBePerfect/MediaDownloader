package com.my.mediadownloader.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.my.mediadownloader.platform.getYtDlpMeta
import com.my.mediadownloader.platform.runYtDlp
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.GlobalContext.get


@Preview
@Composable
fun MainScreen() {
    var screen: String by remember { mutableStateOf(Screen.HOME.route) }
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color.LightGray)
            ) {
                when (screen) {
                    Screen.HOME.route -> HomeScreen()
                    Screen.PROFILE.route -> ProfileScreen()
                    else -> Text("Unknown screen: $screen")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.DarkGray)
            ) {
                NavigationBar {
                    Screen.entries.forEach { item ->
                        NavigationBarItem(selected = item.route == screen,
                            onClick = {
                                screen = item.route
                            },
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(text = item.title) })
                    }
                }
            }

        }
    }
}

@Composable
fun HomeScreen() {

    val scope = rememberCoroutineScope()
    val moshi: Moshi = get().get()
    val adapter = moshi.adapter(VideoMeta::class.java)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var text by remember { mutableStateOf("") }
        val imageUrl = "https://example.com/my-image.jpg"

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                model = "https://i.ytimg.com/vi_webp/dFuigmebc_I/maxresdefault.webp",
                contentDescription = null,
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Введите Url") },
                singleLine = false,
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(onClick = { text = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Очистить")
                        }
                    }
                }
            )
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        runYtDlp().collect {
                            println(it)
                        }
                    }
//                    withContext(Dispatchers.IO) {
//                        getYtDlpMeta().collect {
//                            println(adapter.fromJson(it))
//                        }
//                    }
                }
            }) {
                Text("Поиск")
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen", fontSize = 24.sp)
    }
}


enum class Screen(val route: String, val icon: ImageVector, val title: String) {
    HOME("Поиск", Icons.Default.Search, "Поиск"),
    PROFILE("Настройки", Icons.Default.Settings, "Настройки")
}

data class VideoMeta(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
)

