package com.m4ykey.sndque.domain.use_case

import com.m4ykey.sndque.data.remote.model.spotify.album.Item
import com.m4ykey.sndque.data.remote.model.spotify.album.RandomAlbum
import com.m4ykey.sndque.domain.repository.spotify.SpotifyRepository
import com.m4ykey.sndque.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RandomAlbumUseCase @Inject constructor(
    private val repository: SpotifyRepository
){
    suspend operator fun invoke(albumName : String) : Flow<Resource<List<Item>>> {
        return repository.getRandomAlbum(albumName)
    }
}