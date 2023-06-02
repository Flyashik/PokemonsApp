package com.example.pokemons.model

import android.util.Log
import androidx.lifecycle.*
import com.example.pokemons.db.PokemonDao
import com.example.pokemons.retrofit.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonDao: PokemonDao) : ViewModel() {
    private val repository = PokemonRepository(pokemonDao)

    private val _pokemon = MutableLiveData<Pokemon?>()
    val pokemon: LiveData<Pokemon?> get() = _pokemon

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            try {
                val pokemon = repository.getPokemonByName(name)
                _pokemon.value = pokemon
            } catch (e: Exception) {
                println("Failed fetch Pokemon: $e")
            }
        }
    }

    suspend fun getAllPokemons() {
        viewModelScope.launch {
            try {
                val pokemons = repository.getAllPokemons()
                _pokemonList.value = pokemons
            } catch (e: Exception) {
                println("Failed to fetch Pokemon list: ${e.message}")
            }
        }
    }

    fun clearPokemon() {
        _pokemon.value = null
    }
}