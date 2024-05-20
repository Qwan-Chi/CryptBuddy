package com.qwanchi.cryptbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qwanchi.cryptbuddy.DB.AppDatabase
import com.qwanchi.cryptbuddy.DB.Password
import com.qwanchi.cryptbuddy.components.PasswordCard
import com.qwanchi.cryptbuddy.dialogs.AddPasswordDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(userID: Int, navController: NavController) {
    val appDatabase: AppDatabase = AppDatabase.getDatabase(LocalContext.current)
    val userPasswordDao = appDatabase.userPasswordDao()
    var passwords by remember { mutableStateOf(userPasswordDao.getPasswordsForUser(userID)) }
    var showAdd by remember { mutableStateOf(false) }
    var currentQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var isFilterExpanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("No sort", "Sort by name", "Sort by last updated")
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAdd = true
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        if (showAdd) {
            AddPasswordDialog({
                showAdd = false
            }, userID, navController = navController)
        }
        Column(Modifier.padding(innerPadding)) {
            SearchBar(
                leadingIcon = {
                    // Icon button to disable search
                    if (isSearchActive) {
                        IconButton(onClick = {
                            isSearchActive = false
                            currentQuery = ""
                            passwords = userPasswordDao.getPasswordsForUser(userID)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack, contentDescription = "Search"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isSearchActive = true
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Search, contentDescription = "Search"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                query = currentQuery,
                onQueryChange = {
                    currentQuery = it
                },
                onSearch = {
                    if (currentQuery.isEmpty()) {
                        passwords = userPasswordDao.getPasswordsForUser(userID)
                    }
                    passwords = userPasswordDao.getPasswordsForUser(userID)
                    passwords = passwords.filter { pass ->
                        pass.website_url.contains(it, ignoreCase = true)
                                || pass.username.contains(it, ignoreCase = true)
                                || (pass.notes ?: "").contains(it, ignoreCase = true)
                    }
                    isSearchActive = false
                },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                trailingIcon = {
                    IconButton(onClick = {
                        isFilterExpanded = true
                    }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.List, contentDescription = "Filter"
                            )
                            DropdownMenu(
                                expanded = isFilterExpanded,
                                onDismissRequest = { isFilterExpanded = false }) {
                                sortOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        leadingIcon = {
                                            when (option) {
                                                "No sort" -> Icon(
                                                    Icons.Rounded.Close,
                                                    contentDescription = "No sort"
                                                )

                                                "Sort by name" -> Icon(
                                                    Icons.Rounded.Send,
                                                    contentDescription = "Sort by name"
                                                )

                                                "Sort by last updated" -> Icon(
                                                    Icons.Rounded.DateRange,
                                                    contentDescription = "Sort by last updated"
                                                )
                                            }
                                        },
                                        modifier = Modifier.padding(8.dp),
                                        onClick = {
                                            when (option) {
                                                "No sort" -> {
                                                    passwords =
                                                        userPasswordDao.getPasswordsForUser(userID)
                                                }

                                                "Sort by name" -> {
                                                    passwords =
                                                        passwords.sortedBy { it.website_url }
                                                }

                                                "Sort by last updated" -> {
                                                    passwords =
                                                        passwords.sortedByDescending { it.last_updated }
                                                }
                                            }
                                            isFilterExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }) {
            }
            Divider(Modifier.padding(bottom = 16.dp))
            LazyColumn {
                items(passwords) { pass ->
                    PasswordCard(pass, navController, userID)
                }
            }
        }
    }
}
