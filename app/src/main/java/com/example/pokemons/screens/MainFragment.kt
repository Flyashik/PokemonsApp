package com.example.pokemons.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.pokemons.MainActivity
import com.example.pokemons.R
import com.example.pokemons.databinding.FragmentMainBinding
import com.example.pokemons.db.AppDatabase
import com.example.pokemons.model.PokemonViewModel
import com.example.pokemons.model.PokemonViewModelFactory
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: PokemonViewModel
    private lateinit var navController: NavController
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val pokemonDao = AppDatabase.getInstance(requireContext()).pokemonDao()
        val viewModelFactory = PokemonViewModelFactory(pokemonDao)
        viewModel = ViewModelProvider(this, viewModelFactory)[PokemonViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllPokemonNames()
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ArrayList())
        binding.pokemonNameEditText.setAdapter(adapter)
        viewModel.pokemonNames.observe(viewLifecycleOwner) { names ->
            names?.let {
                adapter.clear()
                adapter.addAll(names)
                adapter.notifyDataSetChanged()
            }
        }

        binding.searchButton.setOnClickListener {
            val pokemonName = binding.pokemonNameEditText.text.toString().trim()
            viewModel.clearPokemon()
            viewModel.fetchPokemonByName(pokemonName)

            viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
                if (pokemon != null) {
                    val action = MainFragmentDirections.actionMainFragmentToPokemonDetailFragment()
                    action.pokemon = pokemon
                    action.label = pokemon.name
                    binding.pokemonNameEditText.setText("")
                    navController.navigate(R.id.pokemonDetailFragment, action.arguments)
                }
            }
        }

        binding.getPokemonListButton.setOnClickListener {
            navController.navigate(R.id.pokemonsFragment)
        }
    }

    override fun onDestroyView() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        super.onDestroyView()
    }
}