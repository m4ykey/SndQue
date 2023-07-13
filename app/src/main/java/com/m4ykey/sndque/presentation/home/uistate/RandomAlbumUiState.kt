package com.m4ykey.sndque.presentation.home.uistate

import com.m4ykey.sndque.data.remote.model.spotify.album.Item
import com.m4ykey.sndque.data.remote.model.spotify.album.RandomAlbum

data class RandomAlbumUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val randomAlbumData : List<Item> = emptyList()
)
