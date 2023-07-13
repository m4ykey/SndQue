package com.m4ykey.sndque.di

import com.google.gson.GsonBuilder
import com.m4ykey.sndque.data.remote.api.spotify.AuthApi
import com.m4ykey.sndque.data.remote.api.spotify.SpotifyApi
import com.m4ykey.sndque.data.remote.token.SpotifyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGsonConverter() : GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        spotifyInterceptor: SpotifyInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(spotifyInterceptor)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideSpotifyAuth(
        gsonConverterFactory: GsonConverterFactory
    ) : AuthApi {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl("https://accounts.spotify.com/")
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpotifyApi(
        gsonConverterFactory: GsonConverterFactory,
        httpClient: OkHttpClient
    ) : SpotifyApi {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl("https://api.spotify.com/")
            .client(httpClient)
            .build()
            .create(SpotifyApi::class.java)
    }

}