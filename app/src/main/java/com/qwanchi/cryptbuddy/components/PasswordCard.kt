package com.qwanchi.cryptbuddy.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.qwanchi.cryptbuddy.DB.Password
import com.qwanchi.cryptbuddy.dialogs.EditPasswordDialog

@Composable
fun PasswordCard(password: Password, navController: NavController, userId: Int) {
    var showEdit by remember { mutableStateOf(false) }
    var isCopyExpanded by remember { mutableStateOf(false) }
    val clipboardManager = LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                showEdit = true
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val faviconUrl = "${password.website_url}/favicon.ico"
            AsyncImage(
                model = faviconUrl,
                contentDescription = "Favicon",
                modifier = Modifier.size(24.dp)
            )
            Column(Modifier.fillMaxWidth().weight(4f)) {
                Text(
                    password.website_url,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(password.username, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(modifier = Modifier.fillMaxWidth().weight(1f), onClick = {
                isCopyExpanded = true
            }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Icon(Icons.Rounded.MoreVert, contentDescription = "Copy...")
                    DropdownMenu(expanded = isCopyExpanded, onDismissRequest = { isCopyExpanded = false }) {
                        DropdownMenuItem(text = { Text("Copy username") }, onClick = {
                            val clip = ClipData.newPlainText("username", password.username)
                            clipboardManager.setPrimaryClip(clip)
                        })
                        DropdownMenuItem(text = { Text("Copy password") }, onClick = {
                            val clip = ClipData.newPlainText("password", password.password)
                            clipboardManager.setPrimaryClip(clip)
                        })
                    }
                }
            }
        }
        if (showEdit) {
            EditPasswordDialog(
                onDismissRequest = { showEdit = false },
                pass = password,
                navController = navController,
                userId = userId
            )
        }
    }
}