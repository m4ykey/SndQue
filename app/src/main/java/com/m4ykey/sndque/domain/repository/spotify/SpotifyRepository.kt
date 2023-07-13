package com.m4ykey.sndque.domain.repository.spotify

import com.m4ykey.sndque.data.remote.model.spotify.album.Item
import com.m4ykey.sndque.data.remote.model.spotify.album.RandomAlbum
import com.m4ykey.sndque.util.Resource
import kotlinx.coroutines.flow.Flow

interface SpotifyRepository {

    suspend fun getRandomAlbum(albumName : String) : Flow<Resource<List<Item>>>

}