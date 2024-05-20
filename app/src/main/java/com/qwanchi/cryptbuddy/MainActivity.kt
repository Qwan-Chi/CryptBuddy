package com.qwanchi.cryptbuddy

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.classes.Exporter
import com.qwanchi.cryptbuddy.classes.Importer
import com.qwanchi.cryptbuddy.nav.Nav
import com.qwanchi.cryptbuddy.screens.CryptApp
import com.qwanchi.cryptbuddy.screens.StartScreen
import com.qwanchi.cryptbuddy.ui.theme.CryptBuddyTheme
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var nav = rememberNavController()
                    Nav()
                }
            }
        }
    }

    private fun alterDocument(uri: Uri, content: String) {
        try {
            contentResolver.openFileDescriptor(uri, "w")?.use { it ->
                FileOutputStream(it.fileDescriptor).use {
                    it.write(
                        content.toByteArray()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == 55
            && resultCode == Activity.RESULT_OK
        ) {
            resultData?.data?.also { uri ->
                val inputStream = contentResolver.openInputStream(uri)
                val importer = Importer()
                val passwords = importer.readPasswords(inputStream!!)
                val sharedPreferences = getSharedPreferences("userSet", MODE_PRIVATE)
                val userId = sharedPreferences.getString("user", "0")
                importer.importPasswords(passwords, this, userId!!.toInt())
            }
        }
        if (requestCode == 56 && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                val exporter = Exporter()
                val appDatabase = AppDatabase.getDatabase(this)
                val userPasswordDao = appDatabase.userPasswordDao()
                val sharedPreferences = getSharedPreferences("userSet", MODE_PRIVATE)
                val userId = sharedPreferences.getString("user", "0")
                val passwords = userPasswordDao.getPasswordsForUser(userId!!.toInt())
                val csv = exporter.writePasswords(passwords)
                alterDocument(uri, csv)
            }
        }
    }
}