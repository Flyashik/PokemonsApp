package com.example.pokemons.retrofit

import android.util.Log
import com.example.pokemons.db.PokemonDao
import com.example.pokemons.db.PokemonEntity
import com.example.pokemons.model.Pokemon
import com.example.pokemons.model.PokemonSprites
import com.example.pokemons.model.PokemonStat
import com.example.pokemons.model.PokemonStatInfo
import com.google.gson.Gson
import com.google.gson.JsonParser
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
        val pokemonResponse = apiService.getPokemonByName(name.toLowerCase())
        Log.d("stat:", pokemonResponse.stats[0].baseStat.toString());

        val stats = pokemonResponse.stats.map { statResponse ->
            val statName = statResponse.stat.name
            val baseStat = statResponse.baseStat
            PokemonStat(baseStat, PokemonStatInfo(statName, ""))
        }

        val sprites = pokemonResponse.sprites

        val pokemon = Pokemon(
            name = pokemonResponse.name,
            height = pokemonResponse.height,
            weight = pokemonResponse.weight,
            stats = stats,
            sprites = pokemonResponse.sprites,
        )

        val pokemonEntity = PokemonEntity(pokemon.name, pokemon.height, pokemon.weight, pokemon.stats, pokemon.sprites.frontDefault)
        pokemonDao.insertPokemon(pokemonEntity)

        return pokemon
    }

    suspend fun getAllPokemons(): List<Pokemon> {
        val pokemonEntities = pokemonDao.getAllPokemons()
        return pokemonEntities.toPokemonList()
    }

    fun List<PokemonEntity>.toPokemonList(): List<Pokemon> {
        return map { pokemonEntity ->
            Pokemon(
                name = pokemonEntity.name,
                height = pokemonEntity.height,
                weight = pokemonEntity.weight,
                stats = pokemonEntity.stats,
                sprites = PokemonSprites(pokemonEntity.frontDefault)
            )
        }
    }

    suspend fun getAllPokemonNames(): ArrayList<String> {
        val response = apiService.getAllPokemonNames()

        val pokemonNames = ArrayList<String>()
        response.results.forEach { pokemon ->
            pokemonNames.add(pokemon.name)
        }
        return pokemonNames
    }
}