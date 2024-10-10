package com.example.core.domain.model

import java.time.LocalDate
import java.util.Date

data class Game (
    val id: Int,
    val isFavorite: Boolean,
    val name: String,
    val releaseDate: Date = Date(),
    val rating: Double,
    val ratingCount: Int,
    val metacritic: Int,
    val background: String,
    val screenShoots: List<ScreenShoot>,
    val genres: List<Genre>,
    val stores: List<Store>,
    val platforms: List<Platform>
)

data class ScreenShoot(
    val id: Int,
    val image: String
)

data class Genre(
    val id: Int,
    val name: String
)

data class Store(
    val id: Int,
    val name: String,
    val domain: String
)

data class Platform(
    val id: Int,
    val name: String,
    val image: String
)

fun createDummyGame() = Game(
    id = 0,
    isFavorite = false,
    name = "Dummy Game",
    releaseDate = Date(),
    rating = 4.44,
    ratingCount = 6777,
    metacritic = 87,
    background = "",
    screenShoots = listOf(ScreenShoot(
        id = 0,
        image = ""
    )),
    genres = listOf(Genre(
        id = 0,
        name = ""
    )),
    stores = listOf(Store(
        id = 0,
        name = "",
        domain = ""
    )),
    platforms = listOf(Platform(
        id = 0,
        name = "",
        image = ""
    ))
)