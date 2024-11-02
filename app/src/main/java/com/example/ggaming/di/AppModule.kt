package com.example.ggaming.di

import com.example.core.domain.usecase.FavoriteGameInteractor
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.core.domain.usecase.GameInteractor
import com.example.core.domain.usecase.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideGameUseCase(gameUseCase: GameInteractor): GameUseCase

    @Binds
    @Singleton
    abstract fun provideFavoriteGameUseCase(favoriteGameUseCase: FavoriteGameInteractor): FavoriteGameUseCase
}