package com.example.pokemons.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemons.db.PokemonDao
import com.example.pokemons.retrofit.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonDao: PokemonDao) : ViewModel() {
    private val repository = PokemonRepository(pokemonDao)

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> get() = _pokemon

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            try {
                val pokemon = repository.getPokemonByName(name)
                _pokemon.value = pokemon
            } catch (_: Exception) {
                println("Failed fetch Pokemon")
            }
        }
    }
}