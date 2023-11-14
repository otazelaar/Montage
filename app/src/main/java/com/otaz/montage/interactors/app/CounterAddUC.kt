package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.domain.model.Counter
import com.otaz.montage.domain.model.toCounterEntity
import com.otaz.montage.util.TAG

class CounterAddUC(
    private val movieDao: MovieDao,
) {
    suspend fun execute(
        counter: Counter
    ) {
        try {
            val counterToBeCached = counter.toCounterEntity()
            movieDao.insertCounter(counterToBeCached)

        } catch (e: Exception) {
            Log.e(TAG, "CounterAddUC: Error: $e")
        }
    }
}