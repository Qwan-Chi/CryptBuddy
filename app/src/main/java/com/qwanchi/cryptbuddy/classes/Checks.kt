package com.qwanchi.cryptbuddy.classes

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.DB.User

object Checks {

    fun checkAuth(context: Context, login: String, password: String): Boolean {
        if (login.isEmpty() || password.isEmpty() || login.length < 4 || login.length > 16 || password.length < 8 || password.length > 32) {
            Toast.makeText(context, "Invalid parameters.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun checkSpecial(context: Context, password: String): Boolean {
        if (!password.matches(Regex("[@_!#\$%^&*()<>?/|}{~:]"))) {
            Toast.makeText(context, "No special symbols.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun checkUserExist(context: Context, login: String): User? {
        val appDatabase: AppDatabase = AppDatabase.getDatabase(context)
        val userDao = appDatabase.userDao()
        val user = userDao.getUserByEmail(login)

        return if (user != null)
            user
        else {
            Toast.makeText(context, "User doesn`t exist.", Toast.LENGTH_SHORT).show()
            null
        }
    }

    fun checkUserPassword(context: Context, user: User, password: String): Boolean {
        return if (user.password == password)
            true
        else {
            Toast.makeText(context, "Wrong password.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun checkValidUrl(context: Context, url: String): Boolean {
        return if (url.matches(Regex("^(http|https)://.*"))) {
            true
        } else {
            Toast.makeText(context, "Invalid URL.", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
