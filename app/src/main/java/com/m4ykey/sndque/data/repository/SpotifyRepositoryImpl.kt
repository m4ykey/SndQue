package com.m4ykey.sndque.data.repository

import com.m4ykey.sndque.data.remote.api.spotify.SpotifyApi
import com.m4ykey.sndque.data.remote.model.spotify.album.Item
import com.m4ykey.sndque.data.remote.model.spotify.album.RandomAlbum
import com.m4ykey.sndque.data.remote.token.SpotifyInterceptor
import com.m4ykey.sndque.domain.repository.spotify.SpotifyRepository
import com.m4ykey.sndque.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SpotifyRepositoryImpl @Inject constructor(
    private val spotifyApi: SpotifyApi,
    private val spotifyInterceptor: SpotifyInterceptor
) : SpotifyRepository {
    override suspend fun getRandomAlbum(albumName: String): Flow<Resource<List<Item>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val albumResponse = spotifyApi.getRandomAlbum(
                    token = "Bearer ${spotifyInterceptor.getAccessKey()}",
                    query = albumName
                ).albums.items
                emit(Resource.Success(albumResponse))
            } catch (e: IOException) {
                emit(
                    Resource.Failure(
                        message = e.localizedMessage ?: "No internet connection",
                        data = null
                    )
                )
            } catch (e : HttpException) {
                emit(
                    Resource.Failure(
                        message = e.localizedMessage ?: "An unexpected error occurred",
                        data = null
                    )
                )
            }
        }
    }
}