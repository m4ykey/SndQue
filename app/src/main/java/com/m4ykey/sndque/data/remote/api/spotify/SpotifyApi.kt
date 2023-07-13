package com.m4ykey.sndque.data.remote.api.spotify

import com.m4ykey.sndque.data.remote.model.spotify.album.RandomAlbum
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApi {

    @GET("v1/search")
    suspend fun getRandomAlbum(
        @Query("q") query : String,
        @Query("type") type : String = "album",
        @Query("limit") limit : Int = 1,
        @Header("Authorization") token : String
    ) : RandomAlbum
}