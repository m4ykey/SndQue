package com.m4ykey.sndque.data.remote.model.spotify

import com.google.gson.annotations.SerializedName

data class SpotifyOAuthResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int
)
