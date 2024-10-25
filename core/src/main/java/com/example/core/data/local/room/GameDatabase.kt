package com.example.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.local.entity.FavoriteGameEntity
import com.example.core.data.local.entity.GameEntity
import com.example.core.data.local.entity.RemoteKeyEntity

@Database(
    entities = [GameEntity::class, RemoteKeyEntity::class, FavoriteGameEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract fun favoriteGameDao(): FavoriteGameDao
    abstract fun gameDao(): GameDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}