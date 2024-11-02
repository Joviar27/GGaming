package com.example.ggaming.di

import com.example.core.domain.usecase.FavoriteGameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun favoriteGameUseCase():FavoriteGameUseCase
}