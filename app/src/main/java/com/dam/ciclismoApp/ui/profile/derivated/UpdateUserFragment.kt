package com.dam.ciclismoApp.ui.profile.derivated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dam.ciclismoApp.databinding.FragmentUpdateUserBinding
import com.dam.ciclismoApp.ui.login.LoginViewModel
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

class UpdateUserFragment : Fragment() {
    private var _binding: FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel> { GenericViewModelFactory { UpdateUserViewModel() } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inicializarBinding(inflater,container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun inicializarBinding(inflater: LayoutInflater,container: ViewGroup?) {
        _binding = FragmentUpdateUserBinding.inflate(inflater, container, false)
    }
}