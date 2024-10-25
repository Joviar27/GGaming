package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(

	@field:SerializedName("added")
	val added: Int,

	@field:SerializedName("developers")
	val developers: List<DevelopersItem>? = null,

	@field:SerializedName("name_original")
	val nameOriginal: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("game_series_count")
	val gameSeriesCount: Int,

	@field:SerializedName("playtime")
	val playtime: Int,

	@field:SerializedName("platforms")
	val platforms: List<PlatformsItem>? = null,

	@field:SerializedName("rating_top")
	val ratingTop: Int,

	@field:SerializedName("reviews_text_count")
	val reviewsTextCount: Int,

	@field:SerializedName("publishers")
	val publishers: List<PublishersItem>? = null,

	@field:SerializedName("achievements_count")
	val achievementsCount: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("parent_platforms")
	val parentPlatforms: List<ParentPlatformsItem>,

	@field:SerializedName("reddit_name")
	val redditName: String,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("released")
	val released: String,

	@field:SerializedName("youtube_count")
	val youtubeCount: Int,

	@field:SerializedName("movies_count")
	val moviesCount: Int,

	@field:SerializedName("description_raw")
	val descriptionRaw: String,

	@field:SerializedName("tags")
	val tags: List<TagsItem>,

	@field:SerializedName("background_image")
	val backgroundImage: String? = null,

	@field:SerializedName("tba")
	val tba: Boolean,

	@field:SerializedName("dominant_color")
	val dominantColor: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("reddit_description")
	val redditDescription: String,

	@field:SerializedName("reddit_logo")
	val redditLogo: String,

	@field:SerializedName("updated")
	val updated: String,

	@field:SerializedName("reviews_count")
	val reviewsCount: Int,

	@field:SerializedName("metacritic")
	val metacritic: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("metacritic_url")
	val metacriticUrl: String,

	@field:SerializedName("alternative_names")
	val alternativeNames: List<String>,

	@field:SerializedName("parents_count")
	val parentsCount: Int,

	@field:SerializedName("user_game")
	val userGame: Any,

	@field:SerializedName("metacritic_platforms")
	val metacriticPlatforms: List<MetacriticPlatformsItem>,

	@field:SerializedName("creators_count")
	val creatorsCount: Int,

	@field:SerializedName("ratings")
	val ratings: List<RatingsItem>,

	@field:SerializedName("genres")
	val genres: List<GenresItem>? = null,

	@field:SerializedName("saturated_color")
	val saturatedColor: String,

	@field:SerializedName("added_by_status")
	val addedByStatus: AddedByStatus,

	@field:SerializedName("reddit_url")
	val redditUrl: String,

	@field:SerializedName("reddit_count")
	val redditCount: Int,

	@field:SerializedName("parent_achievements_count")
	val parentAchievementsCount: Int,

	@field:SerializedName("website")
	val website: String,

	@field:SerializedName("suggestions_count")
	val suggestionsCount: Int,

	@field:SerializedName("stores")
	val stores: List<StoresItem>? = null,

	@field:SerializedName("additions_count")
	val additionsCount: Int,

	@field:SerializedName("twitch_count")
	val twitchCount: Int,

	@field:SerializedName("background_image_additional")
	val backgroundImageAdditional: String,

	@field:SerializedName("esrb_rating")
	val esrbRating: EsrbRating,

	@field:SerializedName("screenshots_count")
	val screenshotsCount: Int,

	@field:SerializedName("clip")
	val clip: Any
)

data class PublishersItem(

	@field:SerializedName("games_count")
	val gamesCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_background")
	val imageBackground: String,

	@field:SerializedName("slug")
	val slug: String
)

data class DevelopersItem(

	@field:SerializedName("games_count")
	val gamesCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_background")
	val imageBackground: String,

	@field:SerializedName("slug")
	val slug: String
)

data class MetacriticPlatformsItem(

	@field:SerializedName("metascore")
	val metascore: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("platform")
	val platform: Platform
)