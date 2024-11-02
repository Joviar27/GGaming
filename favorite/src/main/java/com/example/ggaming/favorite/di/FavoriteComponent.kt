package com.example.ggaming.favorite.di

import android.content.Context
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.ggaming.di.FavoriteModuleDependencies
import com.example.ggaming.favorite.ui.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(favoriteActivity: FavoriteActivity)

    fun provideFavoriteGameUseCase(favoriteGameUseCase: FavoriteGameUseCase)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun addDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}