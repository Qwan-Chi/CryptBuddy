package com.qwanchi.cryptbuddy.screens

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Dao
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.DB.User
import com.qwanchi.cryptbuddy.classes.Checks.checkAuth
import com.qwanchi.cryptbuddy.classes.Checks.checkSpecial
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    val appDatabase: AppDatabase = AppDatabase.getDatabase(LocalContext.current)
    val userDao = appDatabase.userDao()
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("userSet", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(36.dp),
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Lock Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "CryptBuddy",
                        style = MaterialTheme.typography.displaySmall,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "Your personal local-first encryption tool",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Форма авторизации
        TextField(
            value = loginText,
            onValueChange = { loginText = it },
            label = { Text("Username") },
            modifier = Modifier.padding(top = 24.dp)
        )
        TextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text("Password") },
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = {
                if (!checkAuth(context, loginText, passwordText)) return@Button
                val user = User(userDao.getUserCount(), loginText, passwordText)
                userDao.insert(user)
                with(sharedPrefs.edit()) {
                    putString("user", userDao.getUserCount().toString())
                    apply()
                }
                navController.navigate("app/${userDao.getUserCount()}")
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Get Started")
        }
        // Кнопка для смены режима (регистрация/авторизация)
        TextButton(
            {
                navController.navigate("start")
            },
        ) {
            Text("Already have an account? Sign in")
        }
    }
}