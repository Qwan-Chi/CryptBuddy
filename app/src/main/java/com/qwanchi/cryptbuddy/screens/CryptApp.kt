package com.qwanchi.cryptbuddy.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

fun Context.activity(): Activity? = when {
    this is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptApp(navController: NavController, userID: Int) {
    val activity = LocalContext.current.activity()
    val screens = mapOf(
        "Passwords" to Icons.Rounded.Lock,
        "Generator" to Icons.Rounded.Star,
        "Settings" to Icons.Rounded.Settings
    )
    var currentScreen by remember {
        mutableStateOf("Passwords")
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(currentScreen) })
        },
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.value,
                                contentDescription = "Navigation item"
                            )
                        },
                        label = { Text(screen.key) },
                        selected = screen.key == currentScreen,
                        onClick = {
                            currentScreen = screen.key
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (currentScreen) {
                "Passwords" -> PasswordScreen(userID, navController = navController)
                "Generator" -> GeneratorScreen()
                "Settings" -> SettingScreen(navController, activity!!)
            }
        }
    }
}

//@Preview
//@Composable
//fun CryptAppPreview() {
//    CryptApp()
//}
