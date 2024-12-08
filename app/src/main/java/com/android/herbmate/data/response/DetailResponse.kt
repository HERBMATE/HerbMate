package com.android.herbmate.data.response

data class DetailResponse(
	val data: List<DataItem>,
	val error: Boolean,
	val message: String
)

data class DataItem(
	val manfaat: String,
	val sumber: String,
	val kandungan: String,
	val gambar: String,
	val asal: String,
	val penyakit: String,
	val nama: String,
	val namaTanaman: String,
	val namaLatin: String,
	val efekSamping: String,
	val idPertanyaan: Int,
	val id: Int,
	val resep: String
)

