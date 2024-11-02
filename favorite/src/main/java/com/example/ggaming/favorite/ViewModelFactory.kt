package com.example.ggaming.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.domain.usecase.FavoriteGameUseCase
import com.example.ggaming.favorite.ui.FavoriteViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor(
    private val favoriteGameUseCase: FavoriteGameUseCase
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(favoriteGameUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}