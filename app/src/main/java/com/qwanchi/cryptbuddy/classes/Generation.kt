package com.qwanchi.cryptbuddy.classes

import java.util.Random

object Generation {
    fun generatePass(): String {
        val lowercaseLetters = ('a'..'z').toList()
        val uppercaseLetters = ('A'..'Z').toList()
        val digits = ('0'..'9').toList()
        val symbols = listOf('@', '!', '#', '%', '&', '*', '(', ')', ',')

        val charPool = mutableListOf<Char>()
            charPool.addAll(lowercaseLetters)
            charPool.addAll(uppercaseLetters)
            charPool.addAll(digits)
            charPool.addAll(symbols)
            charPool.addAll(lowercaseLetters)
            charPool.addAll(uppercaseLetters)
        val generatedPassword = (1..12)
            .map { _ -> Random().nextInt(charPool.size) }
            .map { charPool[it] }
            .joinToString("")
        return generatedPassword
    }
}