package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class TanamanResponse(

    @field:SerializedName("data")
	val data: List<com.android.herbmate.data.remote.response.TanamanItem>,

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("message")
	val message: String
)

data class TanamanItem(

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
	val penyakit: String? = null
)

