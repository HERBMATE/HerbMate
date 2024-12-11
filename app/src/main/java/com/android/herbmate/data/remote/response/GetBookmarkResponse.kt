package com.android.herbmate.data.remote.response

data class GetBookmarkResponse(
	val data: List<com.android.herbmate.data.remote.response.BookmarkItem>,
	val error: Boolean,
	val message: String
)

data class BookmarkItem(
	val idUser: Int,
	val asal: String,
	val nama: String,
	val namaLatin: String,
	val id: Int,
	val idBookmark: Int,
	val kandungan: String,
	val gambar: String
)

