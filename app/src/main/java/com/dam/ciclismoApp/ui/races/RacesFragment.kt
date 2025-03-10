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
                Log.d("MENSAJE",races.toString())
            } catch (e:Exception) {
                F.showToast(requireContext(),"Algo ha fallado al recibir los datos.")
                Log.d("MENSAJE",e.message.toString())
            }
        }

        viewModel.setmListRaces(races)
        viewModel.setmListRacesFiltered(races)
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