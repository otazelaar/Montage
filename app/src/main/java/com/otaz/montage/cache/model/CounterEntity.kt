package com.otaz.montage.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otaz.montage.domain.model.Counter

@Entity(tableName = "counter")
data class CounterEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "counter")
    var counter: Int,
)

fun CounterEntity.toCounter(): Counter {
    return Counter(
        counter = counter,
    )
}