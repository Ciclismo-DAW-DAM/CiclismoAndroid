package com.dam.ciclismoApp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.FragmentLoginBinding
import com.dam.ciclismoApp.ui.MainActivity
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel by viewModels<LoginViewModel> { GenericViewModelFactory { LoginViewModel() } }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //region [Constructor & lifecycle]
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Cierra AuthActivity
        }
    }
    //endregion
}
