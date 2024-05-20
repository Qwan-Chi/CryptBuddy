package com.qwanchi.cryptbuddy.classes

import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult
import com.qwanchi.cryptbuddy.DB.Password
import java.io.File

class Exporter {
    fun writePasswords(passwords: List<Password>): String {
        val csv = StringBuilder()
        for (password in passwords) {
            csv.append("${password.website_url},${password.username},${password.password},${password.notes}\n")
        }
        return csv.toString()
    }

}