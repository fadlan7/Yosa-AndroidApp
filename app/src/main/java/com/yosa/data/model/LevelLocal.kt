package com.yosa.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LevelLocal(
    var levelName: String,
    var photo: Int,
) : Parcelable
