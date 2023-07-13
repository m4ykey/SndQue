package com.m4ykey.sndque.di

import com.m4ykey.sndque.data.repository.SpotifyRepositoryImpl
import com.m4ykey.sndque.domain.repository.spotify.SpotifyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSpotifyRepository(spotifyRepository: SpotifyRepositoryImpl) : SpotifyRepository {
        return spotifyRepository
    }

}