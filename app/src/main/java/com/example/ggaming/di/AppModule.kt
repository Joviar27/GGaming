package com.example.ggaming.di

import com.example.core.domain.usecase.FavoriteGameInteractor
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.core.domain.usecase.GameInteractor
import com.example.core.domain.usecase.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGameUseCase(gameUseCase: GameInteractor): GameUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideFavoriteGameUseCase(favoriteGameUseCase: FavoriteGameInteractor): FavoriteGameUseCase
}