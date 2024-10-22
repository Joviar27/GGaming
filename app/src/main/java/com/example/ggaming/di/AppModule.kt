package com.example.ggaming.di

import com.example.core.domain.usecase.GetGameDetailInteractor
import com.example.core.domain.usecase.GetGameDetailUseCase
import com.example.core.domain.usecase.GetGameListInteractor
import com.example.core.domain.usecase.GetGameListUseCase
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
    abstract fun provideGetGameListUseCase(getGameListUseCase: GetGameListInteractor): GetGameListUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetGameDetailUseCase(getGameDetailUseCase: GetGameDetailInteractor): GetGameDetailUseCase
}