package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.ImageConfigsDto

/**
 * This response contains...
 */

data class ConfigurationResponse(
    @SerializedName("images") var imageConfigurations: ImageConfigsDto
)