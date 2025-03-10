package com.dam.ciclismoApp.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.FragmentLoginBinding
import com.dam.ciclismoApp.databinding.FragmentParticipationsBinding
import com.dam.ciclismoApp.models.objects.LogIn
import com.dam.ciclismoApp.models.objects.LogInResponse
import com.dam.ciclismoApp.models.repositories.UsersRepository
import com.dam.ciclismoApp.ui.MainActivity
import com.dam.ciclismoApp.utils.F
import com.dam.ciclismoApp.utils.P
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory
import kotlinx.coroutines.launch

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
            iniciarSesion()
        }
        binding.lblSignUp.setOnClickListener {
            F.openBrowser("https://www.google.com", requireContext())
        }
    }
    //endregion

    fun iniciarSesion() {
        P.init(requireContext())
        val logIn:LogIn = LogIn(binding.etUsername.text.toString(),binding.etPasswordUpd.text.toString())
        lifecycleScope.launch {
            val respuesta:LogInResponse = UsersRepository().logIn(logIn)
            if (respuesta.message.contains("Logged in successfully")) {
                val a = respuesta.user.toJson()
                Log.d("Mensaje", respuesta.user.toJson())
                val intent = Intent(requireActivity(), MainActivity::class.java)
                P[P.S.JSON_USER] = respuesta.user.toJson()
                startActivity(intent)
                requireActivity().finish() // Cierra AuthActivity
            }
        }
    }

}
