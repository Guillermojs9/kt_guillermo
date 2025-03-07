package com.example.kt_contador

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstace {
    private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(MovieApiService::class.java)
}