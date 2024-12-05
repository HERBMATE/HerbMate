package com.android.herbmate.data.response

data class TanamanResponse(
	val tanamanResponse: List<TanamanResponseItem>
)

data class TanamanResponseItem(
	val nama: String? = null,
	val id: Int? = null,
	val gambar: String? = null
)

