package com.dam.ciclismoApp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dam.ciclismoApp.databinding.FragmentParticipationsBinding

class ParticipationsFragment : Fragment() {

    private var _binding: FragmentParticipationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ParticipationsViewModel::class.java)

        _binding = FragmentParticipationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}