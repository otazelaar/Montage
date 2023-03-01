package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.ImageConfigsDto

data class ConfigurationResponse(
    @SerializedName("change_keys") var change_keys: List<String>,
    @SerializedName("images") var imageConfigurations: ImageConfigsDto
)