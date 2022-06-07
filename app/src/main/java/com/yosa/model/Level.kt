package com.yosa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Level(
    var levelName: String,
    var photo: Int,
) : Parcelable
