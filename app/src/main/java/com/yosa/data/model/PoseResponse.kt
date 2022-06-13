package com.yosa.data.model

import com.google.gson.annotations.SerializedName

data class PoseResponse(

	@field:SerializedName("poses")
	val poses: List<PosesItem>
)

data class Level(

	@field:SerializedName("nameLevel")
	val nameLevel: String,

	@field:SerializedName("id")
	val id: Int
)

data class ImagesItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("urlImage")
	val urlImage: String
)

data class PosesItem(

	@field:SerializedName("images")
	val images: List<ImagesItem>,

	@field:SerializedName("descPose")
	val descPose: String,

	@field:SerializedName("level")
	val level: Level,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("namePose")
	val namePose: String
)
