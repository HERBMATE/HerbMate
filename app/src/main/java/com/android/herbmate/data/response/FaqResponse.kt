package com.android.herbmate.data.response

data class FaqResponse(
	val kategori3a: String,
	val listPenyakit: List<String?>?,
	val listTanaman: List<String?>,
	val message: String,
	val kategori3b: String
)

