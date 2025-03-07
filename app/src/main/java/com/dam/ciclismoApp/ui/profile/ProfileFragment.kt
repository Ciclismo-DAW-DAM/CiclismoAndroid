package com.dam.ciclismoApp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dam.ciclismoApp.databinding.DialogUpdateUserBinding
import com.dam.ciclismoApp.databinding.FragmentProfileBinding
import com.dam.ciclismoApp.ui.AuthActivity
import com.dam.ciclismoApp.ui.login.LoginViewModel
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> { GenericViewModelFactory { ProfileViewModel() } }

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

//        val textView: TextView = binding.textView3
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun inicializarBotones() {
        binding.btnUpdate.setOnClickListener {
            val dialogBinding = DialogUpdateUserBinding.inflate(layoutInflater)
            DialogManager.showCustomDialog(requireContext(), dialogBinding, false) { dialog ->
                dialogBinding.etUserUpd.setText(viewModel.name.value)
                dialogBinding.btnCloseUpd.setOnClickListener {
                    dialog.dismiss()
                }
                dialogBinding.btnUpdateUser.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
        binding.btnCloseSesion.setOnClickListener {
            DialogManager.showConfirmationDialog(
                requireContext(),
                "Confirmación",
                "¿Estás seguro de salir de la aplicación?",
                onConfirm = {
                    cerrar()
                }
            )
        }
        /*
        binding.btnDeleteUser.setOnClickListener{
            DialogManager.showConfirmationDialog(
                requireContext(),
                "¡ATENCIÓN!",
                "¿Estás seguro de eliminar la cuenta? (Esta acción no se puede deshacer)",
                onConfirm = {
                    lifecycleScope.launch {
                        //Se borra
                    }
                    cerrar()
                }
            )
        }
         */
    }


    fun cerrar() {
        val intent = Intent(requireContext(), AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NAVIGATE_TO_FRAGMENT", "LoginFragment")
        }
        startActivity(intent)
        requireActivity().finish()
    }

    fun inicializarBinding(inflater: LayoutInflater,container: ViewGroup?) {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
    }
}