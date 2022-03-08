package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.ApolloClient
import com.frommetoyou.modulesapp2.BuildConfig
import com.frommetoyou.modulesapp2.MyQuery
import com.frommetoyou.modulesapp2.R
import com.frommetoyou.modulesapp2.databinding.FragmentMainBinding
import com.frommetoyou.modulesapp2.presentation.ui.state.MainViewState
import com.frommetoyou.modulesapp2.presentation.viewmodel.MainViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

const val OBJECT_DETECTION_PATH = "image_labeling_1.tflite"
const val SELECTED_TEXT_STATE = "S"
const val POKEMON_ENDPOINT_SV = "https://graphql-pokemon2.vercel.app"
const val BTN_1 = "btn1"
const val BTN_2 = "btn2"

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var selectedButton = ""
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = BuildConfig.MAPS_API_KEY
        Places.initialize(requireContext(), apiKey)
        Places.createClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { viewState ->
                processViewState(viewState)
            }
        }
        binding.btnSave.setOnClickListener {
            viewModel.onSaveClicked(binding.etText.text.toString())
        }
        binding.btnGetText.setOnClickListener {
            viewModel.onGetClicked()
        }
        binding.btnSaveJetpack.setOnClickListener {
            viewModel.onJetpackSaveClicked(binding.etText.text.toString())
        }
        binding.btnGetJetpackText.setOnClickListener {
            viewModel.onJetpackGetClicked()
        }
        binding.btn1.setOnClickListener {
            selectedButton1()
        }
        binding.btn2.setOnClickListener {
            selectedButton2()
        }
        binding.btnGenLink.setOnClickListener {
            viewModel.genLinkWithSelectedButton(selectedButton, requireActivity())
        }
        binding.btnFetchPokemons.setOnClickListener {
            execApolloQueries()
        }
        binding.btnCamerax.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCameraFragment()
            findNavController().navigate(action)
        }
        binding.btnPurchases.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToPurchasesFragment()
            findNavController().navigate(action)
        }
        binding.btnWorkmanager.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToWorkManagerFragment()
            findNavController().navigate(action)
        }
        binding.btnMaps.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToMapsFragment()
            findNavController().navigate(action)
        }
        viewModel.getSelectedButtonLinkData(requireActivity())
        initializePlacesFragment()
    }

    private fun processViewState(viewState: MainViewState) {
        binding.tvEncriptado.text = viewState.storedText
        binding.etText.setText("")
        binding.tvEncriptado.text = viewState.getFlowText
        if (viewState.dynamicLinkCreated) {
            binding.tvDynamicHelper.text = getString(R.string.main_dynamic_link_helper)
        }
        getDynamicLinkIfAvailable(viewState.dynamicLinkBtnData)
    }

    private fun initializePlacesFragment() {
        // Initialize the AutocompleteSupportFragment.
        val TAG = "PLACES_FRAGMENT"
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
            as AutocompleteSupportFragment
        // Se especifican los campos que quiero que me devuelva, entre mas campos ponga mas me cobran
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
        // se filtran las predicciones para que sean del tipo address y sólo en Argentina
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS)
        autocompleteFragment.setCountries("AR")

        // Al ser clickeado una prediccion se devuelve el place con los campos elegidos
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    private fun selectedButton2() {
        binding.btn2.text = SELECTED_TEXT_STATE
        binding.btn1.text = ""
        selectedButton = BTN_2
    }

    private fun selectedButton1() {
        binding.btn1.text = SELECTED_TEXT_STATE
        binding.btn2.text = ""
        selectedButton = BTN_1
    }

    private fun execApolloQueries() {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(POKEMON_ENDPOINT_SV)
            .build()
        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(MyQuery(2)).execute()
            binding.tvPokemons.text = response.data.toString()
        }
    }

    private fun getDynamicLinkIfAvailable(btnSelectedData: String) {
        when (btnSelectedData) {
            BTN_1 -> {
                selectedButton1()
            }
            BTN_2 -> {
                selectedButton2()
            }
            else -> {}
        }
        Toast.makeText(requireContext(), btnSelectedData, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkForUpdates(requireActivity())
    }
}
