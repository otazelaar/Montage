package com.otaz.montage.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.montage.network.model.ImageConfigsDto

/**
 * This response contains...
 */

data class ConfigurationResponse(
    @SerializedName("images") var imageConfigurations: ImageConfigsDto
)