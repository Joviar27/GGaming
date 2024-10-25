package com.example.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorite_game")
data class FavoriteGameEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "releaseDate")
    val releaseDate: Date = Date(),

    @field:ColumnInfo(name = "rating")
    val rating: Double,

    @field:ColumnInfo(name = "ratingCount")
    val ratingCount: Int,

    @field:ColumnInfo(name = "metacritic")
    val metacritic: Int,

    @field:ColumnInfo(name = "background")
    val background: String
)