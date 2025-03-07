package com.example.kt_contador

import retrofit2.http.GET
import retrofit2.http.Query

//now_playing?api_key=9f93bbd1b95ab983b75033c421b314cb&language=es-ES
interface MovieApiService {
    @GET("now_playing")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = "9f93bbd1b95ab983b75033c421b314cb",
        @Query("language") language: String = "es-ES"
    ): MovieResponse
}