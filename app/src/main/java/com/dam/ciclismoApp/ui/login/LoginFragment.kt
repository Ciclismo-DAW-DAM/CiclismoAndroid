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
        /*lifecycleScope.launch {
            val respuesta:LogInResponse = UsersRepository().logIn(logIn)
            if (respuesta.message.contains("200")) {
                Log.d("Mensaje", respuesta.user.toJson())
                val intent = Intent(requireActivity(), MainActivity::class.java)
                P.set(P.S.JSON_USER,respuesta.user)
                startActivity(intent)
                requireActivity().finish() // Cierra AuthActivity
            }
        }*/
        P[P.S.JSON_USER] = """
            {
              "id": 101,
              "email": "juan.perez@example.com",
              "roles": ["ROLE_USER"],
              "name": "Juan Pérez",
              "password": "securepass123",
              "banned": false,
              "cyclingParticipants": [
                {
                  "id": 1,
                  "user": null,
                  "race": {
                    "id": 1,
                    "name": "Tour de Francia",
                    "description": "El evento ciclista más prestigioso del mundo.",
                    "date": "2025-07-01T10:00:00+02:00",
                    "distance_km": 280,
                    "location": "París, Francia",
                    "coordinates": "lat=48.8566, lng=2.3522",
                    "unevenness": 18,
                    "entry_fee": 150,
                    "available_slots": 12,
                    "status": "open",
                    "category": "Etapas",
                    "image": "https://example.com/images/tour-francia.jpg",
                    "cyclingParticipants": []
                  },
                  "time": "2025-07-01T14:45:20+02:00",
                  "dorsal": 5,
                  "banned": false
                },
                {
                  "id": 2,
                  "user": null,
                  "race": {
                    "id": 2,
                    "name": "Giro de Italia",
                    "description": "Una de las carreras más icónicas del mundo.",
                    "date": "2025-05-20T09:00:00+02:00",
                    "distance_km": 220,
                    "location": "Roma, Italia",
                    "coordinates": "lat=41.9028, lng=12.4964",
                    "unevenness": 15,
                    "entry_fee": 120,
                    "available_slots": 8,
                    "status": "open",
                    "category": "Aventura",
                    "image": "https://example.com/images/giro-italia.jpg",
                    "cyclingParticipants": []
                  },
                  "time": "2025-05-20T13:10:30+02:00",
                  "dorsal": 22,
                  "banned": false
                },
                {
                  "id": 3,
                  "user": null,
                  "race": {
                    "id": 3,
                    "name": "Tour de España",
                    "description": "Una carrera emocionante a través de la península ibérica.",
                    "date": "2025-06-15T08:00:00+02:00",
                    "distance_km": 250,
                    "location": "Madrid, España",
                    "coordinates": "lat=40.4168, lng=-3.7038",
                    "unevenness": 12,
                    "entry_fee": 100,
                    "available_slots": 5,
                    "status": "open",
                    "category": "Montaña",
                    "image": "https://example.com/images/tour-espana.jpg",
                    "cyclingParticipants": []
                  },
                  "time": "2025-06-15T12:30:45+02:00",
                  "dorsal": 10,
                  "banned": false
                },
                {
                  "id": 4,
                  "user": null,
                  "race": {
                    "id": 4,
                    "name": "Vuelta a Colombia",
                    "description": "Recorriendo las montañas colombianas.",
                    "date": "2025-08-10T07:00:00-05:00",
                    "distance_km": 180,
                    "location": "Bogotá, Colombia",
                    "coordinates": "lat=4.7110, lng=-74.0721",
                    "unevenness": 20,
                    "entry_fee": 80,
                    "available_slots": 10,
                    "status": "open",
                    "category": "Montaña",
                    "image": "https://example.com/images/vuelta-colombia.jpg",
                    "cyclingParticipants": []
                  },
                  "time": "2025-08-10T11:20:35-05:00",
                  "dorsal": 32,
                  "banned": false
                },
                {
                  "id": 5,
                  "user": null,
                  "race": {
                    "id": 5,
                    "name": "Tour de Argentina",
                    "description": "Carrera por los paisajes argentinos.",
                    "date": "2025-09-25T09:30:00-03:00",
                    "distance_km": 200,
                    "location": "Buenos Aires, Argentina",
                    "coordinates": "lat=-34.6037, lng=-58.3816",
                    "unevenness": 12,
                    "entry_fee": 90,
                    "available_slots": 7,
                    "status": "open",
                    "category": "Aventura",
                    "image": "https://example.com/images/tour-argentina.jpg",
                    "cyclingParticipants": []
                  },
                  "time": "2025-09-25T14:00:50-03:00",
                  "dorsal": 44,
                  "banned": false
                }
              ],
              "age": "1998-06-17T20:00:00Z",
              "gender": "Male",
              "image": "https://example.com/images/juan_perez.jpg"
            }
        """.trimIndent()
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Cierra AuthActivity
    }

}
