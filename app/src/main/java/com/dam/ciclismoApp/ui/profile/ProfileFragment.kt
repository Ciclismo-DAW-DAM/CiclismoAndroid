package com.dam.ciclismoApp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.DialogUpdateUserBinding
import com.dam.ciclismoApp.databinding.FragmentProfileBinding
import com.dam.ciclismoApp.models.objects.User
import com.dam.ciclismoApp.ui.AuthActivity
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.utils.P
import com.dam.ciclismoApp.viewModel.GenericViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> { GenericViewModelFactory { ProfileViewModel() } }

    //region [Constructor & lifecycle]
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initView()
        initData()
        val root: View = binding.root
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    fun initView() {
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
        viewModel.name.observe(viewLifecycleOwner) {
            binding.lblName.text = viewModel.name.value
        }
        viewModel.mail.observe(viewLifecycleOwner) {
            binding.lblMail.text = viewModel.mail.value
        }
        viewModel.age.observe(viewLifecycleOwner) {
            binding.lblAge.text = viewModel.age.value.toString()
        }
        viewModel.gender.observe(viewLifecycleOwner) {
            binding.lblGender.text = viewModel.gender.value
        }
        viewModel.totalParticipations.observe(viewLifecycleOwner) {
            binding.lblTotalParticipations.text = viewModel.totalParticipations.value.toString()
        }
        viewModel.prefCategory.observe(viewLifecycleOwner) {
            binding.lblPrefCat.text = viewModel.prefCategory.value
        }
        viewModel.prefLocation.observe(viewLifecycleOwner) {
            binding.lblPrefLocation.text = viewModel.prefLocation.value
        }
        viewModel.kmTravelled.observe(viewLifecycleOwner) {
            binding.lblKmTravelled.text = viewModel.kmTravelled.value.toString()
        }
        viewModel.totalSpent.observe(viewLifecycleOwner) {
            binding.lblTotalSpent.text = viewModel.totalSpent.value.toString()
        }
        viewModel.imgProfile.observe(viewLifecycleOwner) {
            binding.imageView3.apply {
                load(viewModel.imgProfile) {
                    placeholder(R.drawable.loading)
                    error(R.drawable.ic_broken_img)
                }
            }
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

    fun initData() {
        val user: User = User().fromJson(P.get(P.S.JSON_USER))
        viewModel.setName(user.name)
        viewModel.setMail(user.email)
        viewModel.setAge(user.age)
        viewModel.setGender(user.gender)
        val participaciones = user.cyclingParticipants
        viewModel.setTotalParticipations(participaciones.size)
        viewModel.setPrefLocation(checkNotNull(participaciones.groupingBy { it.race.location }.eachCount().maxByOrNull { it.value }).key)
        viewModel.setPrefCategory(checkNotNull(participaciones.groupingBy { it.race.category }.eachCount().maxByOrNull { it.value }).key)
        viewModel.setKmTravelled(participaciones.sumOf { it.race.distance })
        viewModel.setTotalSpent(participaciones.sumOf { it.race.fee })
    }

    fun cerrar() {
        val intent = Intent(requireContext(), AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NAVIGATE_TO_FRAGMENT", "LoginFragment")
        }
        startActivity(intent)
    }
}