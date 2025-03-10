package com.dam.ciclismoApp.ui.races

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.DialogRaceDetailBinding
import com.dam.ciclismoApp.databinding.FragmentRacesBinding
import com.dam.ciclismoApp.databinding.ItemRcRaceBinding
import com.dam.ciclismoApp.models.objects.Race
import com.dam.ciclismoApp.models.objects.User
import com.dam.ciclismoApp.models.repositories.CyclingRepository
import com.dam.ciclismoApp.ui.AuthActivity
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.utils.F
import com.dam.ciclismoApp.utils.GridSpacingItemDecoration
import com.dam.ciclismoApp.utils.P
import com.dam.ciclismoApp.utils.RecyclerAdapter
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RacesFragment : Fragment() {
    private val viewModel by viewModels<RacesViewModel> { GenericViewModelFactory { RacesViewModel() } }
    private var _binding: FragmentRacesBinding? = null
    private val binding get() = _binding!!

    //region [Constructor & lifecycle]
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //endregion

    private fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (findNavController().currentDestination?.id == R.id.navigation_race) {
                        DialogManager.showConfirmationDialog(
                            requireContext(),
                            "Confirmación",
                            "¿Estás seguro de salir de la aplicación?",
                            onConfirm = {
                                val intent = Intent(requireContext(), AuthActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    putExtra("NAVIGATE_TO_FRAGMENT", "LoginFragment")
                                }
                                startActivity(intent)
//                                requireActivity().finish()
                            }
                        )
                    } else {
                        findNavController().navigateUp()
                    }
                }
            })
        binding.txtFilter.addTextChangedListener(object : TextWatcher {
            private var filterJob: Job? = null

            override fun afterTextChanged(s: Editable?) {
                filterJob?.cancel()
                filterJob = lifecycleScope.launch {
                    delay(300)
                    s?.let { viewModel.setFilter(s.toString())}
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Acción antes de que el texto cambie (opcional)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setFilter(s.toString())
            }
        })
        binding.btnCleanFilter.setOnClickListener {
            binding.txtFilter.text.clear()
        }
        viewModel.numRaces.observe(viewLifecycleOwner) { num ->
            binding.textView2.text = "Has encontrado $num carreras"
        }
        viewModel.filter.observe(viewLifecycleOwner) { filterText ->
            lifecycleScope.launch {
                val races = viewModel.mLisRaces.value ?: return@launch
                val filteredList = F.filterList(races, filterText)
                withContext(Dispatchers.Main) {
                    if (filteredList != viewModel.mLisRacesFiltered.value) {
                        viewModel.setmListRacesFiltered(filteredList)
                    }
                }
            }
        }
        binding.swipeRefreshLayoutRaces.apply {
            this.setProgressViewOffset(true, 200, 400)
            this.setOnRefreshListener {
                lifecycleScope.launch {
                    binding.swipeRefreshLayoutRaces.isRefreshing = false
//                    viewModel.setFilter("Tour")
                }
            }
        }
        viewModel.mLisRacesFiltered.observe(viewLifecycleOwner) { filteredList ->
            filteredList?.let {
                setupRcPartipations(it)
                viewModel.setNumRace(it.size)
            }
        }
    }

    private fun initData() {
        var races:List<Race> = emptyList()
        lifecycleScope.launch {
            try {
                races = CyclingRepository().getRaces()
                val a = races.toString()
            } catch (e:Exception) {
                P[P.S.JSON_RACES] = """
                  [
                    {
                      "id": 1,
                      "name": "Tour de Francia",
                      "description": "El evento ciclista más prestigioso del mundo.",
                      "date": "2025-07-01T10:00:00+00:00",
                      "distance": 280,
                      "location": "París, Francia",
                      "coordinates": { "lat": 48.8566, "lng": 2.3522 },
                      "unevenness": 18,
                      "fee": 150,
                      "slots": 12,
                      "status": "open",
                      "category": "Etapas",
                      "image": "https://example.com/images/tour-francia.jpg",
                      "participants": []
                    },
                    {
                      "id": 2,
                      "name": "Giro de Italia",
                      "description": "Una de las carreras más icónicas del mundo.",
                      "date": "2025-05-20T09:00:00+00:00",
                      "distance": 220,
                      "location": "Roma, Italia",
                      "coordinates": { "lat": 41.9028, "lng": 12.4964 },
                      "unevenness": 15,
                      "fee": 120,
                      "slots": 8,
                      "status": "open",
                      "category": "Aventura",
                      "image": "https://example.com/images/giro-italia.jpg",
                      "participants": []
                    },
                    {
                      "id": 3,
                      "name": "Tour de España",
                      "description": "Una carrera emocionante a través de la península ibérica.",
                      "date": "2025-06-15T08:00:00+00:00",
                      "distance": 250,
                      "location": "Madrid, España",
                      "coordinates": { "lat": 40.4168, "lng": -3.7038 },
                      "unevenness": 12,
                      "fee": 100,
                      "slots": 5,
                      "status": "open",
                      "category": "Montaña",
                      "image": "https://example.com/images/tour-espana.jpg",
                      "participants": []
                    },
                    {
                      "id": 4,
                      "name": "Vuelta a Colombia",
                      "description": "Recorriendo las montañas colombianas.",
                      "date": "2025-08-10T07:00:00+00:00",
                      "distance": 180,
                      "location": "Bogotá, Colombia",
                      "coordinates": { "lat": 4.7110, "lng": -74.0721 },
                      "unevenness": 20,
                      "fee": 80,
                      "slots": 10,
                      "status": "open",
                      "category": "Montaña",
                      "image": "https://example.com/images/vuelta-colombia.jpg",
                      "participants": []
                    },
                    {
                      "id": 5,
                      "name": "Tour de Argentina",
                      "description": "Carrera por los paisajes argentinos.",
                      "date": "2025-09-25T09:30:00+00:00",
                      "distance": 200,
                      "location": "Buenos Aires, Argentina",
                      "coordinates": { "lat": -34.6037, "lng": -58.3816 },
                      "unevenness": 12,
                      "fee": 90,
                      "slots": 7,
                      "status": "open",
                      "category": "Aventura",
                      "image": "https://example.com/images/tour-argentina.jpg",
                      "participants": []
                    },
                    {
                      "id": 6,
                      "name": "Desafío de Verano",
                      "description": "Una carrera desafiante en el caluroso verano.",
                      "date": "2025-06-15T09:00:00+00:00",
                      "distance": 75,
                      "location": "Montañas de la Sierra",
                      "coordinates": { "lat": 19.1234, "lng": -99.5678 },
                      "unevenness": 500,
                      "fee": 30,
                      "slots": 50,
                      "status": "open",
                      "category": "Femenino",
                      "image": "https://example.com/images/desafio_verano.jpg",
                      "participants": []
                    },
                    {
                      "id": 7,
                      "name": "Maratón de Otoño",
                      "description": "Una maratón espectacular en el otoño.",
                      "date": "2025-09-20T09:00:00+00:00",
                      "distance": 100,
                      "location": "Ciudad de México",
                      "coordinates": { "lat": 19.4326, "lng": -99.1332 },
                      "unevenness": 300,
                      "fee": 40,
                      "slots": 75,
                      "status": "closed",
                      "category": "Élite",
                      "image": "https://example.com/images/maraton_otoño.jpg",
                      "participants": []
                    },
                    {
                      "id": 8,
                      "name": "Tour de Francia",
                      "description": "El evento ciclista más prestigioso del mundo.",
                      "date": "2025-07-01T10:00:00+00:00",
                      "distance": 280,
                      "location": "París, Francia",
                      "coordinates": { "lat": 48.8566, "lng": 2.3522 },
                      "unevenness": 18,
                      "fee": 150,
                      "slots": 12,
                      "status": "completed",
                      "category": "Etapas",
                      "image": "https://example.com/images/tour-francia.jpg",
                      "participants": []
                    },
                    {
                      "id": 9,
                      "name": "Giro de Italia",
                      "description": "Una de las carreras más icónicas del mundo.",
                      "date": "2025-05-20T09:00:00+00:00",
                      "distance": 220,
                      "location": "Roma, Italia",
                      "coordinates": { "lat": 41.9028, "lng": 12.4964 },
                      "unevenness": 15,
                      "fee": 120,
                      "slots": 8,
                      "status": "open",
                      "category": "Aventura",
                      "image": "https://example.com/images/giro-italia.jpg",
                      "participants": []
                    },
                    {
                      "id": 10,
                      "name": "Tour de España",
                      "description": "Una carrera emocionante a través de la península ibérica.",
                      "date": "2025-06-15T08:00:00+00:00",
                      "distance": 250,
                      "location": "Madrid, España",
                      "coordinates": { "lat": 40.4168, "lng": -3.7038 },
                      "unevenness": 12,
                      "fee": 100,
                      "slots": 5,
                      "status": "completed",
                      "category": "Montaña",
                      "image": "https://example.com/images/tour-espana.jpg",
                      "participants": []
                    },
                    {
                      "id": 11,
                      "name": "Vuelta a Colombia",
                      "description": "Recorriendo las montañas colombianas.",
                      "date": "2025-08-10T07:00:00+00:00",
                      "distance": 180,
                      "location": "Bogotá, Colombia",
                      "coordinates": { "lat": 4.7110, "lng": -74.0721 },
                      "unevenness": 20,
                      "fee": 80,
                      "slots": 10,
                      "status": "open",
                      "category": "Aventura",
                      "image": "https://example.com/images/vuelta-colombia.jpg",
                      "participants": []
                    },
                    {
                      "id": 12,
                      "name": "Tour de Argentina",
                      "description": "Una carrera que atraviesa los paisajes argentinos.",
                      "date": "2025-09-05T06:00:00+00:00",
                      "distance": 200,
                      "location": "Buenos Aires, Argentina",
                      "coordinates": { "lat": -34.6037, "lng": -58.3816 },
                      "unevenness": 10,
                      "fee": 90,
                      "slots": 15,
                      "status": "completed",
                      "category": "Etapas",
                      "image": "https://example.com/images/tour-argentina.jpg",
                      "participants": []
                    }
                  ]
                """.trimIndent()
                races = F.parseJsonToList(P.get(P.S.JSON_RACES))
                F.showToast(
                    requireContext(),
                    "Algo ha fallado al recibir los datos. Se mostarán los datos de prueba",
                    Toast.LENGTH_SHORT,
                    R.color.red_spring)
            }finally {
                viewModel.setmListRaces(races)
                viewModel.setmListRacesFiltered(races)
            }
        }
        //viewModel.setmListRaces(F.parseJsonToList(P.get(P.S.JSON_RACES)))
    }

    //region [RC & data]
    private fun setupRcPartipations(mList: List<Race>) {
        val mAdapter = object : RecyclerAdapter<ViewHolder>(
            mList,
            R.layout.item_rc_race,
            ViewHolder::class.java
        ) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                var item = mList[position]
                holder.itemBinding.imgRace.apply {
                    load(item.image){
                        placeholder(R.drawable.loading)
                        error(R.drawable.race)
                    }
                }
                holder.itemBinding.lblLocation.text = item.location
                holder.itemBinding.lblDescription.text = item.description
                holder.itemBinding.lblraceTitle.text = item.name
                holder.itemBinding.lblDate.text ="${F.formatFecha(item.date, true)}h"
                holder.itemBinding.clIndicator.apply {
                    when (item.status) {
                        "open"      -> this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green_spring))
                        "closed"    -> this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red_spring))
                        "complete"  -> this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.orange_spring))
                        else -> println("Opción no válida")
                    }
                }
                holder.itemBinding.imgInscrito.apply {
                    var u: User = F.parseJsonToObject<User>(P.get(P.S.JSON_USER))!!
                    u.cyclingParticipants.forEach{par ->
                        if (par.race.id == item.id){
                            this.visibility = View.VISIBLE
                        }
                    }
                }
                holder.itemBinding.clParent.setOnClickListener {
                    RaceDetailDialog.setupRcDetailDialog(item, requireContext(), layoutInflater)
                }
            }
        }
        binding.rcParticipations.apply {
            val spanCount = 2
            val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            while (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, true))
            adapter = mAdapter
            setItemViewCacheSize(100)
            setHasFixedSize(false)
            recycledViewPool.clear()
        }
    }
    //endregion

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemBinding = ItemRcRaceBinding.bind(view)
}