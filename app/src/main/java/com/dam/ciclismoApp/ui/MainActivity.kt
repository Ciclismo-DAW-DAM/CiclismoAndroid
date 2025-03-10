package com.dam.ciclismoApp.ui
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.ActivityMainBinding
import com.dam.ciclismoApp.utils.P

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_view_main) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        binding.rootView.viewTreeObserver.addOnGlobalLayoutListener {
            detectKeyboard { isVisible ->
                if (isVisible) {
                    binding.navView.visibility = View.GONE
                } else {
                    binding.navView.visibility = View.VISIBLE
                }
            }
        }
        mockUpApp()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun mockUpApp(){
        P.init(this)
        P[P.S.JSON_PARTICIPANTS] = """
            [
              {
                "id": 1,
                "user": {
                  "id": 101,
                  "email": "juan.perez@example.com",
                  "roles": ["ROLE_USER"],
                  "name": "Juan Pérez",
                  "password": "securepass123",
                  "banned": false,
                  "cyclingParticipants": [],
                  "age": "1998-06-17T20:00:00Z",
                  "gender": "Male",
                  "image": "https://example.com/images/juan_perez.jpg"
                },
                "race": {
                  "id": 201,
                  "name": "Tour de España",
                  "description": "Una carrera emocionante a través de la península ibérica.",
                  "date": "2025-06-15T08:00:00+02:00",
                  "distance_km": 250,
                  "location": "Madrid, España",
                  "coordinates": "lat=40.4168, lng=-3.7038",
                  "unevenness": 12,
                  "entry_fee": 100.0,
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
                "id": 2,
                "user": {
                  "id": 102,
                  "email": "ana.gomez@example.com",
                  "roles": ["ROLE_USER"],
                  "name": "Ana Gómez",
                  "password": null,
                  "banned": false,
                  "cyclingParticipants": [],
                  "age": "1998-06-17T20:00:00Z",
                  "gender": "Female",
                  "image": "https://example.com/images/ana_gomez.jpg"
                },
                "race": {
                  "id": 202,
                  "name": "Giro de Italia",
                  "description": "Una de las carreras más icónicas del mundo.",
                  "date": "2025-05-20T09:00:00+02:00",
                  "distance_km": 220,
                  "location": "Roma, Italia",
                  "coordinates": "lat=41.9028, lng=12.4964",
                  "unevenness": 15,
                  "entry_fee": 120.0,
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
                "user": {
                  "id": 103,
                  "email": "carlos.mendez@example.com",
                  "roles": ["ROLE_USER"],
                  "name": "Carlos Méndez",
                  "password": "bikeRider456",
                  "banned": false,
                  "cyclingParticipants": [],
                  "age": "1998-06-17T20:00:00Z",
                  "gender": "Male",
                  "image": "https://example.com/images/carlos_mendez.jpg"
                },
                "race": {
                  "id": 203,
                  "name": "Tour de Francia",
                  "description": "El evento ciclista más prestigioso del mundo.",
                  "date": "2025-07-01T10:00:00+02:00",
                  "distance_km": 280.0,
                  "location": "París, Francia",
                  "coordinates": "lat=48.8566, lng=2.3522",
                  "unevenness": 18,
                  "entry_fee": 150.0,
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
                "id": 4,
                "user": {
                  "id": 104,
                  "email": "sofia.lopez@example.com",
                  "roles": ["ROLE_USER"],
                  "name": "Sofía López",
                  "password": "riderSofi",
                  "banned": false,
                  "cyclingParticipants": [],
                  "age": "1996-06-17T20:00:00Z",
                  "gender": "Female",
                  "image": "https://example.com/images/sofia_lopez.jpg"
                },
                "race": {
                  "id": 204,
                  "name": "Vuelta a Colombia",
                  "description": "Recorriendo las montañas colombianas.",
                  "date": "2025-08-10T07:00:00-05:00",
                  "distance_km": 180,
                  "location": "Bogotá, Colombia",
                  "coordinates": "lat=4.7110, lng=-74.0721",
                  "unevenness": 20,
                  "entry_fee": 80.0,
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
                "user": {
                  "id": 105,
                  "email": "lucas.fernandez@example.com",
                  "roles": ["ROLE_USER"],
                  "name": "Lucas Fernández",
                  "password": "fastBike789",
                  "banned": false,
                  "cyclingParticipants": [],
                  "age": "1992-06-17T20:00:00Z",
                  "gender": "Male",
                  "image": "https://example.com/images/lucas_fernandez.jpg"
                },
                "race": {
                  "id": 205,
                  "name": "Tour de Argentina",
                  "description": "Carrera por los paisajes argentinos.",
                  "date": "2025-09-25T09:30:00-03:00",
                  "distance_km": 200,
                  "location": "Buenos Aires, Argentina",
                  "coordinates": "lat=-34.6037, lng=-58.3816",
                  "unevenness": 12,
                  "entry_fee": 90.0,
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
            ]
        """.trimIndent()
        P[P.S.JSON_RACES] = """
            [
              {
                "id": 1,
                "name": "Tour de Francia",
                "description": "El evento ciclista más prestigioso del mundo.",
                "date": "2025-07-01T10:00:00+02:00",
                "distance_km": 280.0,
                "location": "París, Francia",
                "coordinates": "lat=48.8566, lng=2.3522",
                "unevenness": 18,
                "entry_fee": 150.0,
                "available_slots": 12,
                "status": "open",
                "category": "Etapas",
                "image": "https://example.com/images/tour-francia.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 2,
                "name": "Giro de Italia",
                "description": "Una de las carreras más icónicas del mundo.",
                "date": "2025-05-20T09:00:00+02:00",
                "distance_km": 220,
                "location": "Roma, Italia",
                "coordinates": "lat=41.9028, lng=12.4964",
                "unevenness": 15,
                "entry_fee": 120.0,
                "available_slots": 8,
                "status": "open",
                "category": "Aventura",
                "image": "https://example.com/images/giro-italia.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 3,
                "name": "Tour de España",
                "description": "Una carrera emocionante a través de la península ibérica.",
                "date": "2025-06-15T08:00:00+02:00",
                "distance_km": 250,
                "location": "Madrid, España",
                "coordinates": "lat=40.4168, lng=-3.7038",
                "unevenness": 12,
                "entry_fee": 100.0,
                "available_slots": 5,
                "status": "open",
                "category": "Montaña",
                "image": "https://example.com/images/tour-espana.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 4,
                "name": "Vuelta a Colombia",
                "description": "Recorriendo las montañas colombianas.",
                "date": "2025-08-10T07:00:00-05:00",
                "distance_km": 180,
                "location": "Bogotá, Colombia",
                "coordinates": "lat=4.7110, lng=-74.0721",
                "unevenness": 20,
                "entry_fee": 80.0,
                "available_slots": 10,
                "status": "open",
                "category": "Montaña",
                "image": "https://example.com/images/vuelta-colombia.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 5,
                "name": "Tour de Argentina",
                "description": "Carrera por los paisajes argentinos.",
                "date": "2025-09-25T09:30:00-03:00",
                "distance_km": 200,
                "location": "Buenos Aires, Argentina",
                "coordinates": "lat=-34.6037, lng=-58.3816",
                "unevenness": 12,
                "entry_fee": 90.0,
                "available_slots": 7,
                "status": "open",
                "category": "Aventura",
                "image": "https://example.com/images/tour-argentina.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 6,
                "name": "Gran Fondo Nueva York",
                "description": "Una gran carrera en la Gran Manzana.",
                "date": "2025-10-05T07:45:00-04:00",
                "distance_km": 160.0,
                "location": "Nueva York, EE.UU.",
                "coordinates": "lat=40.7128, lng=-74.0060",
                "unevenness": 10,
                "entry_fee": 110.0,
                "available_slots": 15,
                "status": "open",
                "category": "Ciudad",
                "image": "https://example.com/images/gran-fondo-ny.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 7,
                "name": "Cape Epic",
                "description": "La carrera de MTB más exigente de Sudáfrica.",
                "date": "2025-03-12T06:00:00+02:00",
                "distance_km": 700.0,
                "location": "Ciudad del Cabo, Sudáfrica",
                "coordinates": "lat=-33.9249, lng=18.4241",
                "unevenness": 25,
                "entry_fee": 200.0,
                "available_slots": 6,
                "status": "closed",
                "category": "MTB",
                "image": "https://example.com/images/cape-epic.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 8,
                "name": "Transcontinental Race",
                "description": "Ultra distancia a través de Europa.",
                "date": "2025-07-28T05:00:00+02:00",
                "distance_km": 4000.0,
                "location": "Bruselas, Bélgica",
                "coordinates": "lat=50.8503, lng=4.3517",
                "unevenness": 30,
                "entry_fee": 250,
                "available_slots": 20,
                "status": "open",
                "category": "Ultraciclismo",
                "image": "https://example.com/images/transcontinental.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 9,
                "name": "Tour Down Under",
                "description": "Primera gran vuelta del año en Australia.",
                "date": "2025-01-14T07:00:00+10:00",
                "distance_km": 150.0,
                "location": "Adelaida, Australia",
                "coordinates": "lat=-34.9285, lng=138.6007",
                "unevenness": 8,
                "entry_fee": 70.0,
                "available_slots": 10,
                "status": "open",
                "category": "Ruta",
                "image": "https://example.com/images/tour-down-under.jpg",
                "cyclingParticipants": []
              },
              {
                "id": 10,
                "name": "La Marmotte",
                "description": "Desafío ciclista en los Alpes franceses.",
                "date": "2025-06-28T09:00:00+02:00",
                "distance_km": 174.0,
                "location": "Alpe d'Huez, Francia",
                "coordinates": "lat=45.0931, lng=6.0683",
                "unevenness": 21,
                "entry_fee": 130.0,
                "available_slots": 5,
                "status": "complete",
                "category": "Gran Fondo",
                "image": "https://example.com/images/la-marmotte.jpg",
                "cyclingParticipants": []
              }
            ]
        """.trimIndent()

    }

    private fun detectKeyboard(onKeyboardVisibilityChanged: (Boolean) -> Unit) {
        val rootView = window.decorView.rootView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootView.height
            val keyboardHeight = screenHeight - r.bottom
            val isKeyboardVisible = keyboardHeight > screenHeight * 0.15
            onKeyboardVisibilityChanged(isKeyboardVisible)
        }
    }
}
