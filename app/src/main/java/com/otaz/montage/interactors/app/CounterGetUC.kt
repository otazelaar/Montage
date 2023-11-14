package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.cache.model.toCounter
import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Counter
import com.otaz.montage.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CounterGetUC(
    private val movieDao: MovieDao,
) {
    suspend fun execute(): Flow<DataState<Counter>> = flow {
        try {
            emit(DataState.loading())

            val counterFromCache = movieDao.getCounterValue()
            val counter = counterFromCache.toCounter()

            emit(DataState.success(counter))

        } catch (e: Exception) {
            Log.e(TAG, "CounterGetUC: Error: $e")
        }
    }
}