package com.otaz.montage.domain.model

data class ImageConfigs(
    val backdrop_sizes: List<String>,
    val base_url: String,
    val logo_sizes: List<String>,
    val poster_sizes: List<String>,
    val profile_sizes: List<String>,
    val secure_base_url: String,
    val still_sizes: List<String>
){
    companion object {
        val EMPTY_CONFIGURATIONS = ImageConfigs(
            backdrop_sizes = emptyList(),
            base_url = String(),
            logo_sizes = emptyList(),
            poster_sizes = emptyList(),
            profile_sizes = emptyList(),
            secure_base_url = String(),
            still_sizes = emptyList(),
        )
    }
}


//fun ImageConfigs.toImageConfigsEntity(): ImageConfigsEntity {
//    return ImageConfigsEntity(
//        backdrop_sizes = backdrop_sizes,
//        base_url = base_url,
//        logo_sizes = logo_sizes,
//        poster_sizes = poster_sizes,
//        profile_sizes = profile_sizes,
//        secure_base_url = secure_base_url,
//        still_sizes = still_sizes,
//    )
//}

