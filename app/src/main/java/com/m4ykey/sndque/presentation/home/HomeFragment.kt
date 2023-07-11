package com.m4ykey.sndque.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.m4ykey.sndque.databinding.FragmentHomeBinding
import kotlin.random.Random
import kotlin.random.nextInt

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
                generateRandomId(20)
            }
        }
    }

    private fun generateRandomId(length: Int) {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"
        val stringBuilder = StringBuilder(length)
        val randomNumber = Random.nextInt(1..9)

        for (id in 1 until length) {
            val randomId = (characters.indices).random()
            stringBuilder.append(characters[randomId])
        }
        stringBuilder.insert((0 until length).random(), "")
        val randomId = "$randomNumber" + "$stringBuilder"
        binding.txtRandomId.text = randomId
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}