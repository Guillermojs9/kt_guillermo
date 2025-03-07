package com.example.kt_contador

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kt_contador.RetroFitInstace.service
import kotlinx.coroutines.launch


class MoviesViewModel: ViewModel(){

    private val _movies = MutableLiveData<MovieResponse>()
    val movies: LiveData<MovieResponse> = _movies

    fun fetchMovies(){
        viewModelScope.launch {
            try {
                val movies = service.getMovies()
                _movies.value = movies
            } catch (e: Exception) {
                Log.d(
                    TAG, "Excepcion: " + e.message)
            }
        }
    }
}