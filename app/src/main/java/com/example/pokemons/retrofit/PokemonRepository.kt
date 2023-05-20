package com.example.pokemons.retrofit

import android.util.Log
import com.example.pokemons.db.PokemonDao
import com.example.pokemons.db.PokemonEntity
import com.example.pokemons.model.Pokemon
import com.example.pokemons.model.PokemonStat
import com.example.pokemons.model.PokemonStatInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepository(private val pokemonDao: PokemonDao) {
    private val apiService: PokeApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PokeApiService::class.java)
    }

    suspend fun getPokemonByName(name: String): Pokemon {
        val pokemonResponse = apiService.getPokemonByName(name)
        Log.d("stat:", pokemonResponse.stats[0].baseStat.toString());

        val stats = pokemonResponse.stats.map { statResponse ->
            val statName = statResponse.stat.name
            val baseStat = statResponse.baseStat
            PokemonStat(baseStat, PokemonStatInfo(statName, ""))
        }

        val pokemon = Pokemon(
            name = pokemonResponse.name,
            height = pokemonResponse.height,
            weight = pokemonResponse.weight,
            stats = stats
        )

        val pokemonEntity = PokemonEntity(pokemon.name, pokemon.height, pokemon.weight, pokemon.stats)
        pokemonDao.insertPokemon(pokemonEntity)

        return pokemon
    }
}