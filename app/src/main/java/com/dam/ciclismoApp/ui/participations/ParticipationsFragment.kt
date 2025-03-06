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
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.FragmentParticipationsBinding
import com.dam.ciclismoApp.databinding.ItemRcParticipationBinding
import com.dam.ciclismoApp.utils.RecyclerAdapter
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
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
        val lblSubtitle: TextView = binding.textView2
        viewModel.numParticipations.observe(viewLifecycleOwner) {
            lblSubtitle.text = "Has particpado en ${viewModel.numParticipations.value} carreras"
        }
        viewModel.mLisParticipations.observe(viewLifecycleOwner){
            viewModel.mLisParticipations.value?.let { it1 -> setupRcPartipations(it1) }
        }
        return root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainScope().launch {
            withContext(Dispatchers.Main) {
                delay(2000)
                viewModel.setNumParticipations(10)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    //region [RC & data]
    private fun setupRcPartipations(mList: List<String>) {
        val mAdapter = object : RecyclerAdapter<ViewwHolder>(
            mList,
            R.layout.item_rc_participation,
            ViewwHolder::class.java
        ) {
            override fun onBindViewHolder(holder: ViewwHolder, position: Int) {
                var item = mList[position]
//                holder.itemBinding.lblraceTitle.text =
//                holder.itemBinding.tv1.text = "${item.IdSurvey}. ${item.DescSurvey}"
//                holder.itemBinding.tv3.text = spannable {
//                    normal("${item.FechaIni} - ${item.FechaIni}")
//                }
//                holder.itemBinding.cardView.setOnClickListener { navToOption(item) }
            }
        }

        binding.rcParticipations.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

    }
    //endregion

}

class ViewwHolder(view: View) : RecyclerView.ViewHolder(view) {
    var itemBinding = ItemRcParticipationBinding.bind(view)
}