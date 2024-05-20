package com.qwanchi.cryptbuddy.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController

@Composable
fun SettingScreen(navController: NavController, activity: Activity) {
    Column(Modifier.padding(16.dp)) {
        Button(onClick = {
            navController.navigate("auth")
        }) { Text(text = "Sign out") }
        Button(
            onClick = {
                openFile(activity)
            }
        ) {
            Text("Import Passwords")
        }
        Button(
            onClick = {
                createFile(activity)
            }
        ) {
            Text("Export Passwords")
        }
    }

}

const val PICK_CSV = 55
const val SAVE_CSV = 56

private fun createFile(activity: Activity) {
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "text/comma-separated-values"
        putExtra(Intent.EXTRA_TITLE, "passwords.csv")
    }
    startActivityForResult(activity, intent, SAVE_CSV, null)
}

fun openFile(activity: Activity) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "text/comma-separated-values"
    }

    startActivityForResult(activity, intent, PICK_CSV, null)
}