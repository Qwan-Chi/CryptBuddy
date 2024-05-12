package com.qwanchi.cryptbuddy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingScreen(navController: NavController) {
    Button(onClick = {
        navController.navigate("auth")
    }) { Text(text = "Exit") }
}