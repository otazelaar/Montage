package com.otaz.montage.interactors.app

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toImageConfigs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// make execute function a suspend function instead

class GetConfigurations(
    private val tmdbApiService: TmdbApiService,
) {
    fun execute(
        apikey: String,
    ): Flow<DataState<ImageConfigs>> = flow {
        emit(DataState.loading())

        // may need db caching
        // handle errors more deeply

        val statusResult = runCatching {
            getConfigurationsFromNetwork(apikey)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")


//        kotlin.runCatching {
//            result.onSuccess {
//                emit(DataState.success(it.imageConfigsDto.toImageConfigs()))
//                Log.i("GetConfigurationsUC", it.imageConfigsDto.toImageConfigs().base_url)
//            }
//
//            result.onFailure {
//                Log.e("GetConfigurationsUC", it.message.orEmpty())
//                Log.e("GetConfigurationsUC", it.localizedMessage.orEmpty())
//                Log.e("GetConfigurationsUC", it.cause.toString())
//                Log.e("GetConfigurationsUC", it.stackTrace.toString())
//                Log.e("GetConfigurationsUC", it.suppressed.toString())
//
//                emit(
//                    DataState.error(
//                        it.message.toString()
//                    )
//                )
//            }
//        }
    }

    private suspend fun getConfigurationsFromNetwork(
        apikey: String,
    ): ImageConfigs {
        return tmdbApiService.configuration(
            apikey = apikey,
        ).imageConfigsDto.toImageConfigs()
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