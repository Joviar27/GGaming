package com.example.core.utils

import com.example.core.data.local.entity.FavoriteGameEntity
import com.example.core.data.local.entity.GameEntity
import com.example.core.data.remote.response.GameDetailResponse
import com.example.core.data.remote.response.GameItemResponse
import com.example.core.domain.model.Developer
import com.example.core.domain.model.Game
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Platform
import com.example.core.domain.model.Publisher
import com.example.core.domain.model.Store
import java.util.Date

object DataMapper {

    fun mapResponseToEntity(gameItemResponse: GameItemResponse, isFavorite: Boolean): GameEntity{
        return GameEntity(
            id = gameItemResponse.id.toString(),
            isFavorite = isFavorite,
            name = gameItemResponse.name,
            releaseDate = DateUtils.stringToDate(gameItemResponse.released) ?: Date(),
            rating = gameItemResponse.rating,
            ratingCount = gameItemResponse.ratingsCount,
            metacritic = gameItemResponse.metacritic,
            background = gameItemResponse.backgroundImage ?: ""
        )
    }

    fun mapEntityToDomain(gameEntity: GameEntity): Game{
        return Game(
            id = gameEntity.id,
            isFavorite = gameEntity.isFavorite,
            name = gameEntity.name,
            releaseDate = gameEntity.releaseDate,
            rating = gameEntity.rating,
            ratingCount = gameEntity.ratingCount,
            metacritic = gameEntity.metacritic,
            background = gameEntity.background,
            additionalBackground = "",
            description = "",
            genres = listOf(),
            platforms = listOf(),
            stores = listOf(),
            publishers = listOf(),
            developers = listOf()
        )
    }

    fun mapFavoriteDomainToEntity(game: Game): FavoriteGameEntity{
        return FavoriteGameEntity(
            id = game.id,
            name = game.name,
            releaseDate = game.releaseDate,
            rating = game.rating,
            ratingCount = game.ratingCount,
            metacritic = game.metacritic,
            background = game.background
        )
    }

    fun mapFavoriteEntityToDomain(favoriteGameEntity: FavoriteGameEntity): Game{
        return Game(
            id = favoriteGameEntity.id,
            isFavorite = true,
            name = favoriteGameEntity.name,
            releaseDate = favoriteGameEntity.releaseDate,
            rating = favoriteGameEntity.rating,
            ratingCount = favoriteGameEntity.ratingCount,
            metacritic = favoriteGameEntity.metacritic,
            background = favoriteGameEntity.background,
            additionalBackground = "",
            description = "",
            genres = listOf(),
            platforms = listOf(),
            stores = listOf(),
            publishers = listOf(),
            developers = listOf()
        )
    }


    fun mapDetailResponseToDomain(response: GameDetailResponse, isFavorite: Boolean): Game{
        return Game(
            id = response.id.toString(),
            isFavorite = isFavorite,
            name = response.name,
            releaseDate = DateUtils.stringToDate(response.released) ?: Date(),
            rating = response.rating,
            ratingCount = response.ratingsCount,
            metacritic = response.metacritic,
            background = response.backgroundImage ?: "",
            additionalBackground = response.backgroundImageAdditional,
            description = response.description,
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
            } ?: listOf(),
            publishers = response.publishers?.map {
                Publisher(
                    id = it.id,
                    name = it.name,
                    image = it.imageBackground
                )
            } ?: listOf(),
            developers = response.developers?.map {
                Developer(
                    id = it.id,
                    name = it.name,
                    image = it.imageBackground
                )
            } ?: listOf(),
        )
    }
}