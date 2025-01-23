package com.my.mediadownloader.platform
import com.my.mediadownloader.core.datastore.base.PlatformDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import org.koin.core.context.GlobalContext.get
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

actual fun getPlatformDataStore(): PlatformDataStore = get().get()

actual fun getYtDlpMeta(): Flow<String> = flow {
    try {
        // Путь к yt-dlp (укажите полный путь, если требуется)
        val ytDlpPath = "yt-dlp"
        // Команды и аргументы
        val processBuilder = ProcessBuilder(
            ytDlpPath,
            "-j",
            "https://www.youtube.com/watch?v=dFuigmebc_I"
        )

        processBuilder.directory(File("src/desktopMain/assets/"));

        // Перенаправить потоки
        processBuilder.redirectErrorStream(true)

        // Запуск процесса
        val process = processBuilder.start()

        BufferedReader(InputStreamReader(process.inputStream, StandardCharsets.UTF_8)).use { reader ->
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
//                    println(line)
                emit(line!!)
            }
        }
        // Ожидание завершения процесса
        val exitCode = process.waitFor()
//            println("Process exited with code: $exitCode")
    } catch (e: Exception) {
        emit("ERROR: $e")
    }
}
actual fun runYtDlp(): Flow<String> = flow {
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
            "https://youtu.be/-LgmPXOeQtQ?si=OUY8syRCeqWsuS2D",
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

