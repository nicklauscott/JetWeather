package com.example.jetweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_tbl")
data class Favorite(
    @Nonnull // so we can't save null as a city
    @PrimaryKey
    @ColumnInfo(name = "city") // actual colum name different from field name
    val city: String,

    @ColumnInfo(name = "country") // actual colum name
    val country: String
)
