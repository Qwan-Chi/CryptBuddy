package com.qwanchi.cryptbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Добавить новый пароль
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            SearchBar(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search, contentDescription = "Search"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                query = "Enter text to search...",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                trailingIcon = {
                    IconButton(onClick = {
                        // Вызвать меню фильтра
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.List, contentDescription = "Filter"
                        )
                    }
                }) {
            }
            Divider(Modifier.padding(bottom = 16.dp))

            LazyColumn {
                items(10) {
                    PasswordCard()
                }
            }
        }
    }
}

@Composable
fun PasswordCard() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Иконка вебсайта
            Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = "Account")

            Column {
                Text(
                    "Website Name",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text("tuning3097@gmail.com", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}