package com.example.pokemons.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokemons.model.PokemonStat

@Entity(tableName = "pokemons")
@TypeConverters(PokemonStatsConverter::class)
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStat>
)