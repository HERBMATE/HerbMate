package com.android.herbmate.data.response

data class SearchResponse(
	val seacrhReponse: List<SearchReponseItem>
)

data class SearchReponseItem(
	val nama: String,
	val gambar: String
)

