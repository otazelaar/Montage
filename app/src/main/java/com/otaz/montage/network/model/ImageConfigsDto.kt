package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName
import com.otaz.montage.domain.model.ImageConfigs

/**
 * This data class contains necessary data to build the image URLs.
 * Different sizes and styles of image are available for access.
 *
 * Three items of data are necessary to build a qualified URL:
 *      1. base_url    Ex: https://image.tmdb.org/t/p/
 *      2. size        Ex: w500/
 *      3. file_path   Ex: 8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
 *
 * Ex: https://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
 */

data class ImageConfigsDto(
    @SerializedName("backdrop_sizes") var backdrop_sizes: List<String>,
    @SerializedName("base_url") var base_url: String,
    @SerializedName("logo_sizes") var logo_sizes: List<String>,
    @SerializedName("poster_sizes") var poster_sizes: List<String>,
    @SerializedName("profile_sizes") var profile_sizes: List<String>,
    @SerializedName("secure_base_url") var secure_base_url: String,
    @SerializedName("still_sizes") var still_sizes: List<String>
)

fun ImageConfigsDto.toImageConfigs(): ImageConfigs {
    return ImageConfigs(
        backdrop_sizes = backdrop_sizes,
        base_url = base_url,
        logo_sizes = logo_sizes,
        poster_sizes = poster_sizes,
        profile_sizes = profile_sizes,
        secure_base_url = secure_base_url,
        still_sizes = still_sizes,
    )
}