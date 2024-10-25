package com.example.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_key")
data class RemoteKeyEntity(

    @field:ColumnInfo("id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo("nextKey")
    val nextKey: Int?,

    @field:ColumnInfo("prevKey")
    val prevKey: Int?,
)