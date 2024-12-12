package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetBookmarkResponse(

	@field:SerializedName("data")
	val data: List<BookmarkItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class BookmarkItem(

	@field:SerializedName("idUser")
	val idUser: Int,

	@field:SerializedName("asal")
	val asal: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("idBookmark")
	val idBookmark: Int,

	@field:SerializedName("kandungan")
	val kandungan: String,

	@field:SerializedName("gambar")
	val gambar: String
)
