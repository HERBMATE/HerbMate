package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChatBotResponse(

	@field:SerializedName("response")
	val response: String
)
