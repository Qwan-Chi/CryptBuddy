package com.qwanchi.cryptbuddy.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qwanchi.cryptbuddy.screens.CryptApp
import com.qwanchi.cryptbuddy.screens.StartScreen

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, "auth") {
        composable("auth") {
            StartScreen(navController)
        }
        composable(
            "app/{userID}",
            arguments = listOf(navArgument("userID") { type = NavType.IntType })
        ) {
            CryptApp(navController, it.arguments?.getInt("userID")!!)
        }
//        composable ("register") {
//            RegisterActivity(navController)
//        }
    }
}