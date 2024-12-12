package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChatBotResponse(

	@field:SerializedName("response")
	val response: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
