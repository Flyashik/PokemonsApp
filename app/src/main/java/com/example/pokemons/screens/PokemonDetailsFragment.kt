package com.example.pokemons.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons.databinding.FragmentPokemonDetailsBinding
import com.example.pokemons.R
import com.example.pokemons.adapter.PokemonStatsAdapter
import com.example.pokemons.model.Pokemon

class PokemonDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val args: PokemonDetailsFragmentArgs by navArgs()
        val pokemon = args.pokemon

        binding.nameTextView.text = pokemon?.name.toString().toUpperCase()
        binding.heightTextView.text = "Height: ${pokemon?.height}"
        binding.weightTextView.text = "Weight: ${pokemon?.weight}"

        val statsAdapter = pokemon?.let { PokemonStatsAdapter(it.stats) }
        binding.statsRecyclerView.adapter = statsAdapter
        binding.statsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}