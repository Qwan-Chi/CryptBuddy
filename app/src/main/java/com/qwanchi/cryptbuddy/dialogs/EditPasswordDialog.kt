package com.qwanchi.cryptbuddy.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.DB.Password
import com.qwanchi.cryptbuddy.classes.Checks
import com.qwanchi.cryptbuddy.classes.Generation
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun EditPasswordDialog(
    pass: Password,
    navController: NavController,
    onDismissRequest: () -> Unit,
    userId: Int
) {
    var websiteUrl by remember { mutableStateOf(pass.website_url) }
    var username by remember { mutableStateOf(pass.username) }
    var password by remember { mutableStateOf(pass.password) }
    var notes by remember { mutableStateOf(pass.notes) }
    val appDatabase = AppDatabase.getDatabase(LocalContext.current)
    val passwordDao = appDatabase.passwordDao()
    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Edit Password",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = websiteUrl,
                    onValueChange = { websiteUrl = it },
                    label = { Text("Website URL") })
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") })
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    trailingIcon = {
                        IconButton(onClick = {
                            password = Generation.generatePass()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = "Generate password"
                            )
                        }
                    })
                OutlinedTextField(
                    value = notes ?: "",
                    onValueChange = { notes = it },
                    label = { Text("Notes") })
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(onClick = {
                        onDismissRequest()
                    }) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        if (!Checks.checkValidUrl(context, websiteUrl)) return@Button
                        pass.website_url = websiteUrl
                        pass.username = username
                        pass.password = password
                        pass.notes = notes
                        pass.last_updated = Clock.System.now().epochSeconds
                        passwordDao.update(pass)
                        navController.navigate("app/$userId")
                        onDismissRequest()
                    }) {
                        Text("Save")
                    }
                    IconButton(onClick = {
                        passwordDao.delete(pass)
                        navController.navigate("app/$userId")
                        onDismissRequest()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
                Text(
                    "Last updated: ${
                        Instant.ofEpochSecond(pass.last_updated)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    }"
                )
            }
        }
    }
}