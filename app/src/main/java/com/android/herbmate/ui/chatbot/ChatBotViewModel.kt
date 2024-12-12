package com.android.herbmate.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.model.Message
import kotlinx.coroutines.launch
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.local.pref.UserModel

class ChatBotViewModel(private val repository: HerbMateRepository) : ViewModel() {

    private val _messages = MutableLiveData<MutableList<Message>>(mutableListOf())
    val messages: LiveData<MutableList<Message>> get() = _messages

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    fun sendMessage(content: String) {
        val userMessage = Message(content = content, isSentByUser = true)
        addMessage(userMessage)
        callChatbot(content)
    }

    private fun callChatbot(prompt: String) {
        viewModelScope.launch {
            try {
                val responseData = repository.chatBot(prompt)
                if (responseData is ApiResult.Success) {
                    val botMessage = Message(content = responseData.data.response, isSentByUser = false)
                    addMessage(botMessage)
                }
            } catch (e: Exception) {
                val errorMessage = Message(content = "Error generating response", isSentByUser = false)
                addMessage(errorMessage)
            }
        }
    }

    private fun addMessage(message: Message) {
        val currentMessages = _messages.value ?: mutableListOf()
        currentMessages.add(message)
        _messages.value = currentMessages
    }
}

