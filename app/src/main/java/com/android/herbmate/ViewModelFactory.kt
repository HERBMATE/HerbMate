package com.android.herbmate

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.di.Injection
import com.android.herbmate.ui.detail.DetailViewModel
import com.android.herbmate.ui.login.LoginViewModel
import com.android.herbmate.ui.profile.ProfileViewModel
import com.android.herbmate.ui.register.RegisterViewModel
import com.android.herbmate.ui.upload.UploadViewModel
import com.android.herbmate.ui.bookmark.BookmarkViewModel
import com.android.herbmate.ui.chatbot.ChatBotViewModel
import com.android.herbmate.ui.home.HomeViewModel
import com.google.ai.client.generativeai.GenerativeModel

class ViewModelFactory(
    private val repository: HerbMateRepository,
    private val generativeModel: GenerativeModel
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                BookmarkViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChatBotViewModel::class.java) -> {
                ChatBotViewModel(generativeModel) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            val repository = Injection.provideRepository(context)
            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = "AIzaSyA3j1LX3fvspUzIdqdeJvX3ejqoMVj_jRs"
            )
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(repository, generativeModel)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
