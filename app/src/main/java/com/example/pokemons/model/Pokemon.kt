package com.example.pokemons.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: @RawValue List<PokemonStat>,
    val sprites: @RawValue PokemonSprites,
) : Parcelable

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: PokemonStatInfo
)

data class PokemonStatInfo(
    val name: String,
    val url: String
)

data class PokemonSprites(
    @SerializedName("front_default")
    val frontDefault: String,
)