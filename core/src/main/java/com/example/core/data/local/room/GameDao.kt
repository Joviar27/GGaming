package com.example.core.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.entity.GameEntity

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(gameEntities: List<GameEntity>)

    @Query("SELECT * FROM game")
    fun getGames(): PagingSource<Int, GameEntity>

    @Query("DELETE FROM game")
    suspend fun deleteAllGames()
}