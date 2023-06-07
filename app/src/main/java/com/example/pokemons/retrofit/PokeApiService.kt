package com.example.pokemons.retrofit

import com.example.pokemons.model.Pokemon
import com.example.pokemons.model.PokemonListResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon

    @GET("pokemon?limit=1281")
    suspend fun getAllPokemonNames(): PokemonListResponse
}