package com.dam.ciclismoApp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dam.ciclismoApp.databinding.FragmentProfileBinding
import com.dam.ciclismoApp.ui.AuthActivity
import com.dam.ciclismoApp.ui.login.LoginViewModel
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel> { GenericViewModelFactory { ProfileViewModel() } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        inicializarBinding(inflater,container)
        inicializarBotones()
        val root: View = binding.root

        val textView: TextView = binding.textView3
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    fun inicializarBotones() {
        val nc = findNavController()
        val flecha = ProfileFragmentDirections.actionNavigationProfileToUpdateUserFragment()
        binding.btnUpdate.setOnClickListener {
            nc.navigate(flecha)
        }

        inicializarEliminar()

        cerrar(binding.btnCloseSesion)
    }

    fun inicializarEliminar() {
        cerrar(binding.btnDeleteUser)
    }

    fun cerrar(boton: Button) {
        boton.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("NAVIGATE_TO_FRAGMENT", "LoginFragment")
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun inicializarBinding(inflater: LayoutInflater,container: ViewGroup?) {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
    }
}