package com.example.core.utils

import com.example.core.data.remote.response.GameItemResponse
import com.example.core.domain.model.Game
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Platform
import com.example.core.domain.model.ScreenShoot
import com.example.core.domain.model.Store
import java.util.Date

object DataMapper {

    fun mapResponseToDomain(response: GameItemResponse, isFavorite: Boolean): Game{
        return Game(
            id = response.id,
            isFavorite = isFavorite,
            name = response.name,
            releaseDate = DateUtils.stringToDate(response.released) ?: Date(),
            rating = response.rating,
            ratingCount = response.ratingsCount,
            metacritic = response.metacritic,
            background = response.backgroundImage ?: "",
            screenShoots = response.shortScreenshots?.map {
                ScreenShoot(
                    id = it.id,
                    image = it.image
                )
            } ?: listOf(),
            genres = response.genres?.map {
                Genre(
                    id = it.id,
                    name = it.name
                )
            } ?: listOf(),
            stores = response.stores?.map {
                Store(
                    id = it.store.id,
                    name = it.store.name,
                    image = it.store.imageBackground ?: "",
                    domain = it.store.domain ?: ""
                )
            } ?: listOf(),
            platforms = response.platforms?.map {
                Platform(
                    id = it.platform.id,
                    name = it.platform.name,
                    image = it.platform.imageBackground ?: ""
                )
            } ?: listOf()
        )
    }
}