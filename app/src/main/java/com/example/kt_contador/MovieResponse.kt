package com.example.kt_contador
import com.google.gson.annotations.SerializedName

data class MovieResponse (
    val dates: Dates,
    val page: Long,
    val results: List<Result>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)

data class Dates (
    val maximum: String,
    val minimum: String
)

data class Result (
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("genre_ids")
    val genreIDS: List<Long>,

    val id: Long,

    @SerializedName("original_language")
    val originalLanguage: OriginalLanguage,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val title: String,
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Long
)

enum class OriginalLanguage(val value: String) {
    @SerializedName("en") En("en"),
    @SerializedName("fr") Fr("fr"),
    @SerializedName("th") Th("th"),
    @SerializedName("zh") Zh("zh");
}
