package com.android.herbmate.data.response

import com.google.gson.annotations.SerializedName

data class TanamanDetailsResponse(
	val data: List<TanamanDetailsItem>,
	val error: Boolean,
	val message: String
)

data class TanamanDetailsItem(
	val manfaat: String,
	val sumber: String,
	val kandungan: String,
	val gambar: String,
	val asal: String,
	val penyakit: String,
	val nama: String,
	val namaTanaman: String,
	val namaLatin: String,

	@field:SerializedName("efek_samping")
	val efekSamping: String,
	val idPertanyaan: Int,
	val id: Int,
	val resep: String
)

