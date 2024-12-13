package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @field:SerializedName("data")
	val data: List<DataSearchItem>,

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("message")
	val message: String
)

data class DataSearchItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("gambar")
	val gambar: String
)
