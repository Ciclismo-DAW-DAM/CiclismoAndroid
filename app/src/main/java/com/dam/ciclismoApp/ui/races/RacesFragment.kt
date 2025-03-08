package com.dam.ciclismoApp.ui.races

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.dam.ciclismoApp.ui.AuthActivity
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.utils.F.Companion.parseJsonToList
import com.dam.ciclismoApp.utils.P
import com.dam.ciclismoApp.utils.RecyclerAdapter
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

class RacesFragment : Fragment() {
    private val viewModel by viewModels<RacesViewModel> { GenericViewModelFactory { RacesViewModel() } }
    private var _binding: FragmentRacesBinding? = null
    private val binding get() = _binding!!

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

    private fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
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
            }
        )
        val lblSubtitle: TextView = binding.textView2
        viewModel.numRaces.observe(viewLifecycleOwner) {
            lblSubtitle.text = "Has encontrado ${viewModel.numRaces.value} carreras"
        }
        viewModel.mLisRaces.observe(viewLifecycleOwner) {
            viewModel.mLisRaces.value?.let { it1 -> setupRcPartipations(it1) }
        }
    }

    private fun initData() {
        viewModel.setmListRaces(parseJsonToList(P.get(P.S.JSON_RACES)))
        viewModel.mLisRaces.value?.size?.let { viewModel.setNumParticipations(it) }
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
                /*  holder.itemBinding.lblraceTitle.text =  item.race.name
                holder.itemBinding.lblLocation.text =  item.race.location
                holder.itemBinding.lblNumPart.text =  item.race.participants.size.toString() + " participantes"
                holder.itemBinding.lblScorePart.text =  item.time
                holder.itemBinding.imgParticipation.apply {
                    load(item.race.image){
                        placeholder(R.drawable.loading)
                        error(R.drawable.ic_broken_img)
                    }
                }*/
                holder.itemBinding.clParent.setOnClickListener {
                    val dialogBinding = DialogRaceDetailBinding.inflate(layoutInflater)
                    DialogManager.showCustomDialog(requireContext(), dialogBinding, false) { dialog ->
//                        dialogBinding.imgRaceDialogParticipant.load(R.drawable.race)
//                        dialogBinding.lblParticipationDialog.text = "${item.race.name.toUpperCase()}\n${item.race.description}"
                        dialogBinding.imgCloseButton.setOnClickListener {
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


}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemBinding = ItemRcRaceBinding.bind(view)
}