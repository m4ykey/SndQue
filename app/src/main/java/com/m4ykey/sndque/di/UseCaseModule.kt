package com.m4ykey.sndque.di

import com.m4ykey.sndque.domain.use_case.RandomAlbumUseCase
import com.m4ykey.sndque.domain.use_case.SpotifyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSpotifyUseCase(
        getRandomAlbumUseCase: RandomAlbumUseCase
    ) : SpotifyUseCase {
        return SpotifyUseCase(
            getRandomAlbumUseCase = getRandomAlbumUseCase
        )
    }

}