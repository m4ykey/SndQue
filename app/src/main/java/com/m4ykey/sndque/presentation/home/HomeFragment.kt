package com.m4ykey.sndque.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.m4ykey.sndque.databinding.FragmentHomeBinding
import com.m4ykey.sndque.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModels()

    private val displayedAlbumId = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnClick.setOnClickListener {
                getRandomCharacter()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        homeViewModel.randomAlbumUiState.collect { uiState ->
                            with(binding) {
                                when {
                                    uiState.isLoading -> {
                                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                                    }
                                    uiState.isError?.isNotEmpty() == true -> {
                                        Toast.makeText(requireContext(), "${uiState.isError}", Toast.LENGTH_SHORT).show()
                                    }
                                    uiState.randomAlbumData.isNotEmpty() -> {
                                        val randomAlbum = uiState.randomAlbumData[0]
                                        val randomAlbumId = randomAlbum.id

                                        if (!displayedAlbumId.contains(randomAlbumId)) {
                                            displayedAlbumId.add(randomAlbumId)

                                            val albumCover = randomAlbum.images.find { it.width == 640 && it.height == 640 }
                                            imgAlbum.load(albumCover?.url)
                                            val artist = randomAlbum.artists.joinToString(separator = ", ") { it.name }
                                            txtRandomAlbum.text = artist

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getRandomCharacter() {
        val characters = "abcdefghijklmnopqrstuvwxyz"
        val randomCharacter = characters.random()
        lifecycleScope.launch {
            homeViewModel.getRandomAlbum("$randomCharacter")
            Toast.makeText(requireContext(), "$randomCharacter", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRandomAlbum() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}