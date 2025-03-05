package com.dam.ciclismoApp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.ui.MainActivity
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel by viewModels<LoginViewModel> { GenericViewModelFactory { LoginViewModel() } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Cierra AuthActivity
        }
    }
}
