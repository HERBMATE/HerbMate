package com.android.herbmate

data class ChatMessage(
    val message: String,
    val isUser:  Boolean // true jika pesan dari pengguna, false jika dari chatbot
)
