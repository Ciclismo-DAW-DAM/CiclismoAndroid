package com.dam.ciclismoApp.ui.participations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.placeholder
import coil3.request.error
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.FragmentParticipationsBinding
import com.dam.ciclismoApp.databinding.ItemRcParticipationBinding
import com.dam.ciclismoApp.models.objects.Participant
import com.dam.ciclismoApp.models.objects.User
import com.dam.ciclismoApp.models.repositories.UsersRepository
import com.dam.ciclismoApp.ui.races.RaceDetailDialog
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

class ParticipationsFragment : Fragment() {
    private val viewModel by viewModels<ParticipationsViewModel> { GenericViewModelFactory { ParticipationsViewModel() } }
    private var _binding: FragmentParticipationsBinding? = null
    private val binding get() = _binding!!

    //region [Constructor & lifecycle]
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParticipationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        /*       MainScope().launch {
                    withContext(Dispatchers.Main) {
                    }
                }
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    private fun initView() {
        viewModel.numParticipations.observe(viewLifecycleOwner) { num ->
            binding.textView2.text = "Has participado en $num carreras"
        }
        viewModel.mLisParticipations.observe(viewLifecycleOwner) { list ->
            list?.let {
                setupRcPartipations(it)
            }
        }
        binding.txtFilterPart.addTextChangedListener(object : TextWatcher {
            private var filterJob: Job? = null

            override fun afterTextChanged(s: Editable?) {
                filterJob?.cancel()
                filterJob = lifecycleScope.launch {
                    delay(300)
                    s?.let { viewModel.setFilter(it.toString()) }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Acción antes de que el texto cambie (opcional)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.btnCleanFilterPart.setOnClickListener {
            binding.txtFilterPart.text.clear()
        }
        viewModel.mLisParticipationsFiltered.observe(viewLifecycleOwner) { filteredList ->
            filteredList?.let {
                setupRcPartipations(it)
                viewModel.setNumParticipations(it.size)
            }
        }
        binding.swipeRefreshLayoutPart.apply {
            this.setProgressViewOffset(true, 200, 400)
            this.setOnRefreshListener {
                lifecycleScope.launch {
                    binding.swipeRefreshLayoutPart.isRefreshing = false
                }
            }
        }
        viewModel.filter.observe(viewLifecycleOwner) { filterText ->
            lifecycleScope.launch {
                val races = viewModel.mLisParticipations.value ?: return@launch
                val filteredList = F.filterList(races, filterText)

                withContext(Dispatchers.Main) {
                    if (filteredList != viewModel.mLisParticipationsFiltered.value) {
                        viewModel.setmListParticipationsFiltered(filteredList)
                    }
                }
            }
        }

    }

    private fun initData() {
        var participaciones:List<Participant> = emptyList()
        DialogManager.showLoadingDialog(requireContext())
        lifecycleScope.launch {
            try {
                participaciones = UsersRepository().getUser(User().fromJson(P.get(P.S.JSON_USER)).id).cyclingParticipants
            } catch (e:Exception) {
                P[P.S.JSON_PARTICIPANTS] = """
                [
                  {
                    "id": 1,
                    "user": {
                      "id": 101,
                      "email": "juan.perez@example.com",
                      "roles": ["ROLE_USER"],
                      "name": "Juan Pérez",
                      "password": "securepass123",
                      "banned": false,
                      "cyclingParticipants": [],
                      "age": 19,
                      "gender": "Male",
                      "image": "https://example.com/images/juan_perez.jpg"
                    },
                    "race": {
                      "id": 201,
                      "name": "Tour de España",
                      "description": "Una carrera emocionante a través de la península ibérica.",
                      "date": "2025-06-15T08:00:00+02:00",
                      "distance": 250,
                      "location": "Madrid, España",
                      "coordinates": { "lat": 48.8566, "lng": 2.3522 },
                      "unevenness": 12,
                      "fee": 100.0,
                      "slots": 5,
                      "status": "open",
                      "category": "Montaña",
                      "image": "https://example.com/images/tour-espana.jpg",
                      "participants": []
                    },
                    "time": "2025-06-15T12:30:45+02:00",
                    "dorsal": 10,
                    "banned": false
                  },
                  {
                    "id": 2,
                    "user": {
                      "id": 102,
                      "email": "ana.gomez@example.com",
                      "roles": ["ROLE_USER"],
                      "name": "Ana Gómez",
                      "password": null,
                      "banned": false,
                      "cyclingParticipants": [],
                      "age": 18,
                      "gender": "Female",
                      "image": "https://example.com/images/ana_gomez.jpg"
                    },
                    "race": {
                      "id": 202,
                      "name": "Giro de Italia",
                      "description": "Una de las carreras más icónicas del mundo.",
                      "date": "2025-05-20T09:00:00+02:00",
                      "distance": 220,
                      "location": "Roma, Italia",
                      "coordinates": { "lat": 41.9028, "lng": 12.4964 },
                      "unevenness": 15,
                      "fee": 120.0,
                      "slots": 8,
                      "status": "open",
                      "category": "Aventura",
                      "image": "https://example.com/images/giro-italia.jpg",
                      "participants": []
                    },
                    "time": "2025-05-20T13:10:30+02:00",
                    "dorsal": 22,
                    "banned": false
                  },
                  {
                    "id": 3,
                    "user": {
                      "id": 103,
                      "email": "carlos.mendez@example.com",
                      "roles": ["ROLE_USER"],
                      "name": "Carlos Méndez",
                      "password": "bikeRider456",
                      "banned": false,
                      "cyclingParticipants": [],
                      "age": 20,
                      "gender": "Male",
                      "image": "https://example.com/images/carlos_mendez.jpg"
                    },
                    "race": {
                      "id": 203,
                      "name": "Tour de Francia",
                      "description": "El evento ciclista más prestigioso del mundo.",
                      "date": "2025-07-01T10:00:00+02:00",
                      "distance": 280.0,
                      "location": "París, Francia",
                      "coordinates": { "lat": 40.4168, "lng": -3.7038 },
                      "unevenness": 18,
                      "fee": 150.0,
                      "slots": 12,
                      "status": "open",
                      "category": "Etapas",
                      "image": "https://example.com/images/tour-francia.jpg",
                      "participants": []
                    },
                    "time": "2025-07-01T14:45:20+02:00",
                    "dorsal": 5,
                    "banned": false
                  },
                  {
                    "id": 4,
                    "user": {
                      "id": 104,
                      "email": "sofia.lopez@example.com",
                      "roles": ["ROLE_USER"],
                      "name": "Sofía López",
                      "password": "riderSofi",
                      "banned": false,
                      "cyclingParticipants": [],
                      "age": 21,
                      "gender": "Female",
                      "image": "https://example.com/images/sofia_lopez.jpg"
                    },
                    "race": {
                      "id": 204,
                      "name": "Vuelta a Colombia",
                      "description": "Recorriendo las montañas colombianas.",
                      "date": "2025-08-10T07:00:00-05:00",
                      "distance": 180,
                      "location": "Bogotá, Colombia",
                      "coordinates": { "lat": 4.7110, "lng": -74.0721 },
                      "unevenness": 20,
                      "fee": 80.0,
                      "slots": 10,
                      "status": "open",
                      "category": "Montaña",
                      "image": "https://example.com/images/vuelta-colombia.jpg",
                      "participants": []
                    },
                    "time": "2025-08-10T11:20:35-05:00",
                    "dorsal": 32,
                    "banned": false
                  },
                  {
                    "id": 5,
                    "user": {
                      "id": 105,
                      "email": "lucas.fernandez@example.com",
                      "roles": ["ROLE_USER"],
                      "name": "Lucas Fernández",
                      "password": "fastBike789",
                      "banned": false,
                      "cyclingParticipants": [],
                      "age": 22,
                      "gender": "Male",
                      "image": "https://example.com/images/lucas_fernandez.jpg"
                    },
                    "race": {
                      "id": 205,
                      "name": "Tour de Argentina",
                      "description": "Carrera por los paisajes argentinos.",
                      "date": "2025-09-25T09:30:00-03:00",
                      "distance": 200,
                      "location": "Buenos Aires, Argentina",
                      "coordinates": { "lat": 19.1234, "lng": -99.5678 },
                      "unevenness": 12,
                      "fee": 90.0,
                      "slots": 7,
                      "status": "open",
                      "category": "Aventura",
                      "image": "https://example.com/images/tour-argentina.jpg",
                      "participants": []
                    },
                    "time": "2025-09-25T14:00:50-03:00",
                    "dorsal": 44,
                    "banned": false
                  }
                ]
                """.trimIndent()
                participaciones = F.parseJsonToList(P.get(P.S.JSON_PARTICIPANTS))
                F.showToast(
                    requireContext(),
                    "Algo ha fallado al recibir los datos. Se mostarán los datos de prueba",
                    Toast.LENGTH_SHORT,
                    R.color.red_spring)
            }finally {
                viewModel.setmListParticipations(participaciones)
                viewModel.setmListParticipationsFiltered(participaciones)
                DialogManager.dismissLoadingDialog()
            }

        }
    }

    //region [RC & data]
    private fun setupRcPartipations(mList: List<Participant>) {
        val mAdapter = object : RecyclerAdapter<ViewHolder>(
            mList,
            R.layout.item_rc_participation,
            ViewHolder::class.java
        ) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                var item = mList[position]
                holder.itemBinding.lblraceTitle.text =  item.race.name
                holder.itemBinding.lblLocation.text =  item.race.location
                holder.itemBinding.lblNumPart.text =  item.race.participants.size.toString() + " participantes"
                holder.itemBinding.lblScorePart.text =  item.time
                holder.itemBinding.imgParticipation.apply {
                    load(item.race.image){
                        placeholder(R.drawable.loading)
                        error(R.drawable.ic_broken_img)
                    }
                }
                holder.itemBinding.clParent.setOnClickListener {
                    RaceDetailDialog.setupRcDetailDialog(item.race, requireContext(), layoutInflater)
                }
            }
        }
        binding.rcParticipations.apply {
            val spanCount = 1
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
    var itemBinding = ItemRcParticipationBinding.bind(view)
}