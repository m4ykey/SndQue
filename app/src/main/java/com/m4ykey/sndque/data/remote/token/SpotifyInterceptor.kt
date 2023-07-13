package com.m4ykey.sndque.data.remote.token

import android.content.SharedPreferences
import android.util.Base64
import com.m4ykey.sndque.BuildConfig.SPOTIFY_CLIENT_ID
import com.m4ykey.sndque.BuildConfig.SPOTIFY_CLIENT_SECRET
import com.m4ykey.sndque.data.remote.api.spotify.AuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SpotifyInterceptor @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    private val accessToken = "access_token"

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()

        val accessToken = sharedPreferences.getString(accessToken, null) ?: getAccessKey()

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        chain.proceed(newRequest)
    }

    suspend fun getAccessKey(): String {
        val authHeader = "Basic " + Base64.encodeToString(
            "${SPOTIFY_CLIENT_ID}:${SPOTIFY_CLIENT_SECRET}".toByteArray(),
            Base64.NO_WRAP
        )

        val response = authApi.getAccessToken(authHeader, "client_credentials")

        return response.accessToken
    }
}