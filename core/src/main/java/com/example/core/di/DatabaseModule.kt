package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.room.FavoriteGameDao
import com.example.core.data.local.room.GameDao
import com.example.core.data.local.room.GameDatabase
import com.example.core.data.local.room.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideGameDatabase(@ApplicationContext context: Context): GameDatabase{
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "Game.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun favoriteGameDao(gameDatabase: GameDatabase): FavoriteGameDao = gameDatabase.favoriteGameDao()

    @Provides
    fun gameDao(gameDatabase: GameDatabase): GameDao = gameDatabase.gameDao()

    @Provides
    fun remoteKeyDao(gameDatabase: GameDatabase): RemoteKeyDao = gameDatabase.remoteKeyDao()
}