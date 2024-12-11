package com.android.herbmate.ui.chatbot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.Message
import com.android.herbmate.data.HerbMateRepository
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatBotViewModel(private val repository: HerbMateRepository) : ViewModel() {

//    private val _messages = MutableLiveData<MutableList<Message>>(mutableListOf())
//    val messages: LiveData<MutableList<Message>> get() = _messages
//
//    fun sendMessage(content: String) {
//        val userMessage = Message(content = content, isSentByUser = true)
//        addMessage(userMessage)
////
////        // Call the chatbot API
//        callChatbot(content)
//    }
//
//    private fun callChatbot(prompt: String) {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    generativeModel.generateContent(prompt)
//                }
//                val chatbotMessage = Message(content = response.text.toString(), isSentByUser = false)
//                addMessage(chatbotMessage)
//
//                Log.i("Chatbot", "Generated Text: ${response.text}")
//            } catch (e: Exception) {
//                Log.e("Chatbot", "Error generating content: ${e.message}")
//                val errorMessage = Message(content = "Error generating response", isSentByUser = false)
//                addMessage(errorMessage)
//            }
//        }
//    }
//
//    private fun addMessage(message: Message) {
//        val currentMessages = _messages.value ?: mutableListOf()
//        currentMessages.add(message)
//        _messages.value = currentMessages
//    }


}
