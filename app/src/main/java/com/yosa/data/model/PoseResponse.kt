package com.yosa.data.model

import com.google.gson.annotations.SerializedName

data class PoseResponse(

	@field:SerializedName("PoseResponse")
	val poseResponse: List<PoseResponseItem>? = null
)

data class Level(

	@field:SerializedName("nameLevel")
	val nameLevel: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)

data class PoseResponseItem(

	@field:SerializedName("images")
	val images: List<ImagesItem>? = null,

	@field:SerializedName("descPose")
	val descPose: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namePose")
	val namePose: String? = null
)

data class ImagesItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)
