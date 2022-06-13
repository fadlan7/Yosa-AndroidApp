package com.yosa.data.model

import com.google.gson.annotations.SerializedName

data class LevelResponse(

    @field:SerializedName("levels")
    val levels: List<LevelsItem>? = null
)

data class LevelsItem(

    @field:SerializedName("nameLevel")
    val nameLevel: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,
)
