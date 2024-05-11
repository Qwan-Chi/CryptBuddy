package com.qwanchi.cryptbuddy.classes

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

object Checks {
    fun checkAuth(context: Context, login: String, password: String): Boolean {
        if (login.isEmpty() || password.isEmpty() || login.length < 4 || login.length > 16 || password.length < 8 || password.length > 32) {
            Toast.makeText(context, "Invalid parameters.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    fun checkSpecial(context: Context, password: String): Boolean{
        if (!password.matches(Regex("[@_!#\$%^&*()<>?/|}{~:]"))){
            Toast.makeText(context, "No special symbols.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}