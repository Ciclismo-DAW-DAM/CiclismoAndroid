package com.dam.ciclismoApp.ui.races

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.FragmentRacesBinding
import com.dam.ciclismoApp.ui.AuthActivity
import com.dam.ciclismoApp.utils.DialogManager

class RacesFragment : Fragment() {

    private var _binding: FragmentRacesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(RacesViewModel::class.java)

        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                            }
                        )
                    } else {
                        findNavController().navigateUp()
                    }
                }
            }
        )


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class ViewHolderRut(view: View) : RecyclerView.ViewHolder(view) {
//    var itemBinding = ItemRutasRecyclerviewLoginBinding.bind(view)
}