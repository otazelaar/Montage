package com.otaz.montage.interactors.app

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.ConfigsDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetConfigurations(
    private val tmdbApiService: TmdbApiService,
    private val configurationsDtoMapper: ConfigsDtoMapper,
){
    fun execute(
        apikey: String,
    ): Flow<DataState<ImageConfigs>> = flow {
        try {
            emit(DataState.loading())

            val configurations = getConfigurationsFromNetwork(
                apikey = apikey,
            )

            emit(DataState.success(configurations))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetConfigurations: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getConfigurationsFromNetwork(
        apikey: String,
    ): ImageConfigs{
        return configurationsDtoMapper.mapToDomainModel(
            tmdbApiService.configuration(
                apikey = apikey,
            ).imageConfigurations
        )
    }

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