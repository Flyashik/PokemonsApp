package com.example.pokemons.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons.R
import com.example.pokemons.model.Pokemon

class PokemonsAdapter(
    private var pokemons: List<Pokemon>,
    private var selectedPokemonIndex: Int,
    private val onItemClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>() {

    fun setSelectedPokemonIndex(index: Int) {
        selectedPokemonIndex = index
        notifyDataSetChanged()
    }

    fun getPokemonIndex(pokemon: Pokemon): Int {
        return pokemons.indexOf(pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int = pokemons.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokemonButton: Button = itemView.findViewById(R.id.pokemonButton)

        fun bind(pokemon: Pokemon) {
            pokemonButton.text = pokemon.name
            pokemonButton.setOnClickListener {
                onItemClick(pokemon)
            }
        }
    }

    internal fun setPokemons(pokemons: List<Pokemon>) {
        this.pokemons = pokemons
        notifyDataSetChanged()
    }
}