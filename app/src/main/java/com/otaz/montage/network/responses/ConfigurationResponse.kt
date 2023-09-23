package com.otaz.montage.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.montage.network.model.ImageConfigsDto

data class ConfigurationResponse(
    @SerializedName("images") var imageConfigsDto: ImageConfigsDto
)