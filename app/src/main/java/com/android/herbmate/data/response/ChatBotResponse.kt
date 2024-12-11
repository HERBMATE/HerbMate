package com.android.herbmate.data.response

import com.google.gson.annotations.SerializedName

data class ChatBotResponse(

	@field:SerializedName("response")
	val response: String
)
