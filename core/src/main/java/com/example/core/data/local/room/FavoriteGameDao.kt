package com.example.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.entity.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteGame(favoriteGameEntity: FavoriteGameEntity)

    @Query("SELECT * FROM favorite_game")
    fun getFavoriteGames(): Flow<List<FavoriteGameEntity>>

    @Query("DELETE FROM favorite_game WHERE id = :id")
    suspend fun deleteFavoriteGame(id: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite_game WHERE id = :id)")
    fun isGameFavorite(id: String): Flow<Boolean>
}