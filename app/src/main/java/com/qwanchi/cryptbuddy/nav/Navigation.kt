package com.qwanchi.cryptbuddy.nav

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qwanchi.cryptbuddy.screens.CryptApp
import com.qwanchi.cryptbuddy.screens.RegisterScreen
import com.qwanchi.cryptbuddy.screens.StartScreen

@Composable
fun Nav() {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val start = remember {
        val userId = sharedPref.getString("user", "0")
        if (userId == "0") {
            "auth"
        } else {
            "app/$userId"
        }

    }
    NavHost(navController = navController, start) {
        composable("auth") {
            StartScreen(navController)
        }
        composable(
            "app/{userID}",
            arguments = listOf(navArgument("userID") { type = NavType.IntType })
        ) {
            CryptApp(navController, it.arguments?.getInt("userID")!!)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("start") {
            StartScreen(navController)
        }
    }
}