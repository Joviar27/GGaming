package com.example.ggaming.di

import com.example.core.domain.usecase.GetGameListInteractor
import com.example.core.domain.usecase.GetGameListUseCase
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
    abstract fun provideGetGameListUseCase(getGameListUseCase: GetGameListInteractor): GetGameListUseCase
}