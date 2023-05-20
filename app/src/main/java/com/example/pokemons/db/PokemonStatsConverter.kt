package com.example.pokemons.db

import androidx.room.TypeConverter
import com.example.pokemons.model.PokemonStat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonStatsConverter {
    @TypeConverter
    fun fromString(value: String): List<PokemonStat> {
        val listType = object : TypeToken<List<PokemonStat>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(stats: List<PokemonStat>): String {
        return Gson().toJson(stats)
    }
}