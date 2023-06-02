package com.example.pokemons.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemons.R
import com.example.pokemons.adapter.PokemonStatsAdapter
import com.example.pokemons.databinding.FragmentPokemonDetailsBinding
import com.example.pokemons.databinding.FragmentPokemonDetailsDbBinding
import com.example.pokemons.model.Pokemon
import com.squareup.picasso.Picasso
import java.util.*

class PokemonDetailsDbFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailsDbBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsDbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PokemonDetailsDbFragmentArgs by navArgs()
        val pokemons = args.pokemons
        var selectedPokemonIndex = args.selectedPokemonIndex

        fun updatePokemonDetails() {
            val selectedPokemon = if (selectedPokemonIndex in pokemons!!.indices) {
                pokemons[selectedPokemonIndex]
            } else {
                null
            }
            binding.previousButton.text = pokemons[(selectedPokemonIndex - 1 + pokemons.size) % pokemons.size].name.capitalize()
            binding.nextButton.text = pokemons[(selectedPokemonIndex + 1) % pokemons.size].name.capitalize()
            (requireActivity() as AppCompatActivity).supportActionBar?.title = selectedPokemon?.name?.capitalize()
            Picasso.get().load(selectedPokemon?.sprites?.frontDefault).resize(360, 360).into(binding.imageView)
            binding.nameTextView.text = selectedPokemon?.name.toString().capitalize()
            binding.heightTextView.text = "Height: ${selectedPokemon?.height}"
            binding.weightTextView.text = "Weight: ${selectedPokemon?.weight}"

            val statsAdapter = selectedPokemon?.let { PokemonStatsAdapter(it.stats) }
            binding.statsRecyclerView.adapter = statsAdapter
            binding.statsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        updatePokemonDetails()

        binding.nextButton.setOnClickListener {
            if (pokemons != null) {
                selectedPokemonIndex = (selectedPokemonIndex + 1) % pokemons.size
            }
            updatePokemonDetails()
        }

        binding.previousButton.setOnClickListener {
            if (pokemons != null) {
                selectedPokemonIndex = (selectedPokemonIndex - 1 + pokemons.size) % pokemons.size
            }
            updatePokemonDetails()
        }
    }
}