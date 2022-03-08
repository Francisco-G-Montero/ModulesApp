package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.frommetoyou.modulesapp2.R
import com.frommetoyou.modulesapp2.data.model.Place
import com.frommetoyou.modulesapp2.databinding.FragmentMapsBinding
import com.frommetoyou.modulesapp2.presentation.utils.PlaceRenderer
import com.frommetoyou.modulesapp2.presentation.utils.PlacesReader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager

/**
 * Esta clase muestra la lógica para el inicio de un mapa de Google Maps
 *
 * @since 1.0.0
 * @author F.G.M
 * */
class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private var circle: Circle? = null
    private val places: List<Place> by lazy {
        PlacesReader(requireContext()).read()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * configuras maps, una vez listo se llama a getmapasync y se añaden
     * los marcadores, clusters o lo que hayas configurado.
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            // En mi caso llamo a un metodo que añade los clusteres
            addClusteredMarkers(googleMap)
            // y muevo la camara al promedio de la posicion de los marcadores
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                places.forEach { bounds.include(it.latLng) }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
            }
        }
    }

    /**
     * Adds marker representations of the places list on the provided GoogleMap object
     * Adds a [member] to this group.
     * @return the new size of the group.
     */
    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(place.latLng)
            )
        }
    }

    /**
     * Adds markers to the map with clustering support.
     *
     * Para configurar un cluster (conjunto de markers) se usa un clustermanager.
     *
     * Este manager necesita de un renderer que se encarga de renderizar el conjunto
     * de marcadores cuando contraes (titulo, snippet o desc, icono)
     *
     * @param googleMap Es el parametro necesario para crear el clusterManager
     * @return Esta clase es de setup y no retorna ningún valor
     */
    fun addClusteredMarkers(googleMap: GoogleMap) {
        // Create the ClusterManager class and set the custom renderer.
        val clusterManager = ClusterManager<Place>(requireContext(), googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                requireContext(),
                googleMap,
                clusterManager
            )

        // Add the places to the ClusterManager.
        clusterManager.addItems(places)
        clusterManager.cluster()

        // se maneja el evento de click del manager para un marker, se añade un
        // circulo alrededor de este
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(googleMap, item)
            return@setOnClusterItemClickListener false
        }

        // se añade un listener para cuando se mueve la camara, todos los items de
        // la pantalla se transparentan para mejorar la navegacion del mapa
        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        // manejo listeners de cuando la camara esta idle, esto me lo devuelve el sdk de maps
        // Revierto el efecto de transparentacion anterior
        googleMap.setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque.
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that reclustering
            // can be performed when the camera stops moving.
            clusterManager.onCameraIdle()
        }

        // y se dibuja un custom layout para mostrar la info de un marcador
        // al tocar un marcador se muestra la info del mismo con titulo y snipper (desc)
        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            // Return null here, so that getInfoContents() is called next.
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate the layouts for the info window, title and snippet.
                val infoWindow = layoutInflater.inflate(
                    R.layout.custom_info_contents,
                    binding.root.findViewById<FrameLayout>(R.id.map_fragment), false
                )
                val title = infoWindow.findViewById<TextView>(R.id.title)
                title.text = marker.title
                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet
                return infoWindow
            }
        })
    }

    /**
     * Adds a [Circle] around the provided [item]
     */
    private fun addCircle(googleMap: GoogleMap, item: Place) {
        circle?.remove()
        circle = googleMap.addCircle(
            CircleOptions()
                .center(item.latLng)
                .radius(1000.0)
                .fillColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.secondaryLightColorTranslucent
                    )
                )
                .strokeColor(ContextCompat.getColor(requireContext(), R.color.secondaryColor))
        )
    }
}
