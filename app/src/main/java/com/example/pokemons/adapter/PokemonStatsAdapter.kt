package com.example.pokemons.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons.R
import com.example.pokemons.model.PokemonStat

class PokemonStatsAdapter(private val stats: List<PokemonStat>) : RecyclerView.Adapter<PokemonStatsAdapter.StatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return StatViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val stat = stats[position]
        holder.bind(stat)
    }

    override fun getItemCount(): Int {
        return stats.size
    }

    inner class StatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val statNameTextView: TextView = itemView.findViewById(R.id.statNameTextView)
        private val baseStatTextView: TextView = itemView.findViewById(R.id.baseStatTextView)

        fun bind(stat: PokemonStat) {
            statNameTextView.text = "${stat.stat.name}: "
            baseStatTextView.text = stat.baseStat.toString()
        }
    }
}