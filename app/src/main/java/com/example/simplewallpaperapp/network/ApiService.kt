package com.example.simplewallpaperapp.network
import com.example.pagination.models.ImageClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {
    @GET("?key=21513068-62dda8c9c630391d0aef56784&")
    suspend fun getPhoto(
        @Query("page") page: Int,
        @Query("q") catergoru: String,
        @Query("size") size: Int = 30
    ): ImageClass
}