package com.dam.ciclismoApp.ui.participations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.placeholder
import coil3.request.error
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.DialogParticipantBinding
import com.dam.ciclismoApp.databinding.FragmentParticipationsBinding
import com.dam.ciclismoApp.databinding.ItemRcParticipationBinding
import com.dam.ciclismoApp.models.objects.Participant
import com.dam.ciclismoApp.models.objects.User
import com.dam.ciclismoApp.models.repositories.UsersRepository
import com.dam.ciclismoApp.ui.races.RaceDetailDialog
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.utils.F
import com.dam.ciclismoApp.utils.F.Companion.parseJsonToList
import com.dam.ciclismoApp.utils.GridSpacingItemDecoration
import com.dam.ciclismoApp.utils.P
import com.dam.ciclismoApp.utils.RecyclerAdapter
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory
import kotlinx.coroutines.Dispatchers
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
        viewModel.numParticipations.observe(viewLifecycleOwner) {
            binding.textView2.text = "Has particpado en ${viewModel.numParticipations.value} carreras"
        }
        viewModel.mLisParticipations.observe(viewLifecycleOwner) {
            viewModel.mLisParticipations.value?.let { it1 -> setupRcPartipations(it1) }
        }
        binding.txtFilterPart.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Acción antes de que el texto cambie (opcional)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setFilter(s.toString())
            }
        })
        binding.btnCleanFilterPart.setOnClickListener {
            binding.txtFilterPart.text.clear()
        }
        viewModel.filter.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    DialogManager.showLoadingDialog(requireContext()) // Asegurar que esté en el hilo principal
                }
                val filteredList = F.filterList(viewModel.mLisParticipations.value, it)
                withContext(Dispatchers.Main) {
                    viewModel.setmListParticipationsFiltered(filteredList) // Actualiza LiveData en Main
                    DialogManager.dismissLoadingDialog()
                }
            }
        }
        viewModel.mLisParticipationsFiltered.observe(viewLifecycleOwner) {
            viewModel.mLisParticipationsFiltered.value?.let { it1 -> setupRcPartipations(it1) }
            viewModel.setNumParticipations(it.size)
        }
        binding.swipeRefreshLayoutPart.apply {
            this.setProgressViewOffset(true, 200, 400)
            this.setOnRefreshListener {
                lifecycleScope.launch {
                    binding.swipeRefreshLayoutPart.isRefreshing = false
//                    viewModel.setFilter("Tour")
                }
            }
        }
    }

    private fun initData() {
        /*var participaciones:List<Participant> = emptyList()
        lifecycleScope.launch {
            try {
                participaciones = UsersRepository().getUser(User().fromJson(P.get(P.S.JSON_USER)).id).cyclingParticipants
            } catch (e:Exception) {
                F.showToast(requireContext(),"Algo ha fallado al recibir los datos.")
            }

            viewModel.setmListParticipations(participaciones)
        }*/

        viewModel.setmListParticipations(parseJsonToList(P.get(P.S.JSON_PARTICIPANTS)))
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