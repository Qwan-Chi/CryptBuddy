package com.qwanchi.cryptbuddy.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Random

@Composable
fun GeneratorScreen() {
    val lowercaseLetters = ('a'..'z').toList()
    val uppercaseLetters = ('A'..'Z').toList()
    val digits = ('0'..'9').toList()
    val symbols = listOf('@', '!', '#', '%', '&', '*', '(', ')', ',')

    var textFild by remember { mutableStateOf("") }
    var charactersNumbers by remember { mutableStateOf(false) } // Remember checkbox state
    var passwordType = remember { mutableStateOf(false) } // Remember checkbox state
    var sliderValue by remember { mutableStateOf(4) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = textFild,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = charactersNumbers,
                onCheckedChange = { charactersNumbers = it }
            )
            Text(
                text = "Add special characters/numbers",
                style = MaterialTheme.typography.titleSmall
            )
        }

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxWidth()
//        ) {
//            Checkbox(
//                checked = passwordType.value,
//                onCheckedChange = { passwordType.value = it }
//            )
//            Text(
//                text = "Запоминающийся пароль",
//                style = MaterialTheme.typography.titleSmall
//            )
//        }

        Slider(
            value = sliderValue.toFloat(),
            onValueChange = { newValue ->
                sliderValue = newValue.toInt()
            },
            valueRange = 4f..64f,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Text(
            text = "Password length: $sliderValue",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 5.dp)
        )

        Button(
            onClick = {
                val charPool = mutableListOf<Char>()
                if (charactersNumbers) {
                    charPool.addAll(lowercaseLetters)
                    charPool.addAll(uppercaseLetters)
                    charPool.addAll(digits)
                    charPool.addAll(symbols)
                } else {
                    charPool.addAll(lowercaseLetters)
                    charPool.addAll(uppercaseLetters)
                }
                val generatedPassword = (1..sliderValue)
                    .map { _ -> Random().nextInt(charPool.size) }
                    .map { charPool[it] }
                    .joinToString("")
                textFild = generatedPassword
            },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(
                text = "Generate",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GeneratorScreenPreview() {
    GeneratorScreen()
}
