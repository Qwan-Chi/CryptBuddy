package com.qwanchi.cryptbuddy.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.DB.Password
import com.qwanchi.cryptbuddy.DB.UserPassword
import com.qwanchi.cryptbuddy.classes.Checks
import com.qwanchi.cryptbuddy.classes.Generation
import kotlinx.datetime.Clock


@Composable
fun AddPasswordDialog(onDismissRequest: () -> Unit, userId: Int, navController: NavController) {
    var websiteUrl by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val appDatabase: AppDatabase = AppDatabase.getDatabase(LocalContext.current)
    val passwordDao = appDatabase.passwordDao()
    val userPasswordDao = appDatabase.userPasswordDao()
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Add Password",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    label = { Text(text = "Website URL") },
                    value = websiteUrl,
                    onValueChange = {
                        websiteUrl = it
                    })
                OutlinedTextField(
                    label = { Text(text = "Username") },
                    value = username,
                    onValueChange = {
                        username = it
                    })
                OutlinedTextField(
                    label = { Text(text = "Password") },
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            password = Generation.generatePass()
                        }) {
                            Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "Generate password")
                        }

                    })
                OutlinedTextField(
                    label = { Text(text = "Notes") },
                    value = notes,
                    modifier = Modifier.height(100.dp),
                    onValueChange = {
                        notes = it
                    })
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Button(onClick = {
                        if (!Checks.checkValidUrl(context, websiteUrl)) return@Button
                        val pass = Password(
                            passwordDao.getPasswordCount() + 1,
                            websiteUrl,
                            username,
                            password,
                            notes,
                            Clock.System.now().epochSeconds
                        )
                        passwordDao.insert(pass)
                        userPasswordDao.insert(UserPassword(userId, passwordDao.getPasswordCount()))
                        navController.navigate("app/$userId")
                        onDismissRequest()
                    }, modifier = Modifier.weight(1f)) {
                        Text(text = "Add")
                    }
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}
