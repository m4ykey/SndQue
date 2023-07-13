package com.m4ykey.sndque.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.sndque.domain.use_case.SpotifyUseCase
import com.m4ykey.sndque.presentation.home.uistate.RandomAlbumUiState
import com.m4ykey.sndque.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: SpotifyUseCase
) : ViewModel() {

    private val _randomAlbumUiState = MutableStateFlow(RandomAlbumUiState())
    val randomAlbumUiState : StateFlow<RandomAlbumUiState> = _randomAlbumUiState

    suspend fun getRandomAlbum(albumName : String) {
        useCase.getRandomAlbumUseCase(albumName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _randomAlbumUiState.value = randomAlbumUiState.value.copy(
                        isLoading = false,
                        randomAlbumData = result.data ?: emptyList()
                    )
                }
                is Resource.Failure -> {
                    _randomAlbumUiState.value = randomAlbumUiState.value.copy(
                        isLoading = false,
                        randomAlbumData = result.data ?: emptyList(),
                        isError = result.message ?: "Unknown error"
                    )
                }
                is Resource.Loading -> {
                    _randomAlbumUiState.value = randomAlbumUiState.value.copy(
                        isLoading = true,
                        randomAlbumData = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}