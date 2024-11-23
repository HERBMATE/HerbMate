package com.android.herbmate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant(
    var name: String,
    var image: Int
) : Parcelable