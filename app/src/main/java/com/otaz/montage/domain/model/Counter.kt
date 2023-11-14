package com.otaz.montage.domain.model

import com.otaz.montage.cache.model.CounterEntity

data class Counter(
    val counter: Int,
){
    companion object {
        val EMPTY_COUNTER = Counter(
            counter = 0,
        )
    }
}

fun Counter.toCounterEntity(): CounterEntity {
    return CounterEntity(
        counter = counter,
    )
}