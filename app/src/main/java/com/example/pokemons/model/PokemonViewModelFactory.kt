package com.example.pokemons.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemons.db.PokemonDao

class PokemonViewModelFactory(private val pokemonDao: PokemonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonViewModel(pokemonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}