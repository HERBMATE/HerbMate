package com.android.herbmate.data.remote.response

data class PenyakitJerawatResponse(
	val penyakitJerawatResponse: List<com.android.herbmate.data.remote.response.PenyakitJerawatResponseItem?>? = null
)

data class PenyakitJerawatResponseItem(
	val asal: String? = null,
	val penyakit: String? = null,
	val manfaat: String? = null,
	val sumber: String? = null,
	val namaTanaman: String? = null,
	val namaLatin: String? = null,
	val efekSamping: String? = null,
	val kandungan: String? = null,
	val gambar: String? = null,
	val resep: String? = null
)

