package com.example.pokemons.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemons.R
import com.example.pokemons.adapter.PokemonsAdapter
import com.example.pokemons.databinding.FragmentPokemonsBinding
import com.example.pokemons.db.AppDatabase
import com.example.pokemons.model.Pokemon
import com.example.pokemons.model.PokemonViewModel
import kotlinx.coroutines.launch

class PokemonsFragment : Fragment() {
    private lateinit var binding: FragmentPokemonsBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var navController: NavController
    private lateinit var adapter: PokemonsAdapter

    private var selectedPokemonIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val pokemonDao = AppDatabase.getInstance(requireContext()).pokemonDao()

        adapter = PokemonsAdapter(emptyList(), selectedPokemonIndex) { pokemon ->
            selectedPokemonIndex = adapter.getPokemonIndex(pokemon)
            navigateToPokemonDbDetails()
        }
        binding.pokemonsRecyclerView.adapter = adapter
        binding.pokemonsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = PokemonViewModel(pokemonDao)

        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemons ->
            adapter.setPokemons(pokemons)
            adapter.notifyDataSetChanged()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val pokemons = viewModel.getAllPokemons()
        }
    }

    private fun navigateToPokemonDbDetails() {
        val action = PokemonsFragmentDirections.actionPokemonsFragmentToPokemonDetailDbFragment()
        action.selectedPokemonIndex = selectedPokemonIndex
        action.pokemons = viewModel.pokemonList.value?.toTypedArray()
        navController.navigate(action)
    }
}