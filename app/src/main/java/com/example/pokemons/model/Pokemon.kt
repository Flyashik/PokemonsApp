package com.example.pokemons.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStat>,
) : java.io.Serializable

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: PokemonStatInfo
)

data class PokemonStatInfo(
    val name: String,
    val url: String
)