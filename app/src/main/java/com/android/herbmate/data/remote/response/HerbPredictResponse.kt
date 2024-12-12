package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class HerbPredictResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class Data(

	@field:SerializedName("asal")
	val asal: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("kandungan")
	val kandungan: String,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("penyakit")
	val penyakit: String
)
