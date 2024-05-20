package com.qwanchi.cryptbuddy.classes

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.DB.Password
import com.qwanchi.cryptbuddy.DB.UserPassword
import kotlinx.datetime.Clock
import java.io.InputStream

class Importer {
    fun readPasswords(csv: InputStream): List<Password> {
        val reader = csv.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (url, username, password, note) = it.split(',', ignoreCase = false, limit = 4)
                Password(website_url = url.trim(), username = username.trim(), password = password.trim(), notes = note.trim(), last_updated = Clock.System.now().epochSeconds)
            }.toList()
    }

    fun importPasswords(passwords: List<Password>, context: Context, userId: Int) {
        val appDatabase = AppDatabase.getDatabase(context)
        val passwordDao = appDatabase.passwordDao()
        val userPasswordDao = appDatabase.userPasswordDao()
        for (password in passwords) {
            passwordDao.insert(password)
            println("User ID: $userId")
            val userPass = UserPassword(userId, passwordDao.getPasswordCount())
            userPasswordDao.insert(userPass)
        }
    }
}