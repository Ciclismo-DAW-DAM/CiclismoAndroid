package com.dam.ciclismoApp.ui.participations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.utils.F.Companion.parseJsonToList
import com.dam.ciclismoApp.utils.P
import com.dam.ciclismoApp.utils.RecyclerAdapter
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
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
                    val dialogBinding = DialogParticipantBinding.inflate(layoutInflater)
                    DialogManager.showCustomDialog(requireContext(), dialogBinding, false) { dialog ->
                        dialogBinding.imgRaceDialogParticipant.load(R.drawable.race)
                        dialogBinding.lblParticipationDialog.text = "${item.race.name.toUpperCase()}\n${item.race.description}"
                        dialogBinding.btnClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
        binding.rcParticipations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            setItemViewCacheSize(100)
            setHasFixedSize(false)
            recycledViewPool.clear()
        }
    }
    //endregion


    private fun initView() {
        val lblSubtitle: TextView = binding.textView2
        viewModel.numParticipations.observe(viewLifecycleOwner) {
            lblSubtitle.text = "Has particpado en ${viewModel.numParticipations.value} carreras"
        }
        viewModel.mLisParticipations.observe(viewLifecycleOwner) {
            viewModel.mLisParticipations.value?.let { it1 -> setupRcPartipations(it1) }
        }
    }

    private fun initData() {
        viewModel.setmListParticipations(parseJsonToList(P.get(P.S.JSON_PARTICIPANTS)))
        viewModel.mLisParticipations.value?.size?.let { viewModel.setNumParticipations(it) }
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemBinding = ItemRcParticipationBinding.bind(view)
}