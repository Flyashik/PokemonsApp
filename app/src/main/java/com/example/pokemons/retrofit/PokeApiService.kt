package com.example.pokemons.retrofit

import com.example.pokemons.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon
}