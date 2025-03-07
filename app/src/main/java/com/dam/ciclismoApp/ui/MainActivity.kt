package com.dam.ciclismoApp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.ActivityMainBinding
import com.dam.ciclismoApp.models.repositories.CyclingRepository
import com.dam.ciclismoApp.utils.P
import kotlinx.coroutines.launch

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


        mockUpApp()
    }

    // Permite que el botón de "Atrás" en el ActionBar funcione correctamente
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun mockUpApp(){
        P.init(this)
        P[P.S.JSON_PARTICIPANTS] = """
            [
            {
                "id":203,
                "user":{
                "id":1,
                "email":"pepe@gmail.com",
                "roles":[
                "ROLE_USER"
                ],
                "name":"pepe",
                "banned":false
            },
                "race":{
                "id":10,
                "name":"Carrera del Desierto",
                "description":"Recorrido extremo en el Sahara",
                "date":"2025-06-10T00:00:00Z",
                "distance_km":150,
                "location":"Sahara, Marruecos",
                "coordinates":"lat:23.4162,lng:-13.6542",
                "unevenness":5,
                "entry_fee":50,
                "available_slots":3,
                "status":"open",
                "category":"Extreme",
                "image":"https://example.com/images/carrera-desierto.jpg"
            },
                "time":"2025-04-12T00:00:00Z",
                "dorsal":88,
                "banned":true
            },
            {
                "id":829,
                "user":{
                "id":1,
                "email":"pepe@gmail.com",
                "roles":[
                "ROLE_USER"
                ],
                "name":"pepe",
                "banned":false
            },
                "race":{
                "id":10,
                "name":"Carrera del Desierto",
                "description":"Recorrido extremo en el Sahara",
                "date":"2025-06-10T00:00:00Z",
                "distance_km":150,
                "location":"Sahara, Marruecos",
               "coordinates":"lat:23.4162,lng:-13.6542",
                "unevenness":5,
                "entry_fee":50,
                "available_slots":3,
                "status":"open",
                "category":"Extreme",
                "image":"https://example.com/images/carrera-desierto.jpg"
            },
                "time":"2025-07-04T00:00:00Z",
                "dorsal":1,
                "banned":false
            },
            {
                "id":264,
                "user":{
                "id":1,
                "email":"pepe@gmail.com",
                "roles":[
                "ROLE_USER"
                ],
                "name":"pepe",
                "banned":false
            },
                "race":{
                "id":10,
                "name":"Carrera del Desierto",
                "description":"Recorrido extremo en el Sahara",
                "date":"2025-06-10T00:00:00Z",
                "distance_km":150,
                "location":"Sahara, Marruecos",
                "coordinates":"lat:23.4162,lng:-13.6542",
                "unevenness":5,
                "entry_fee":50,
                "available_slots":3,
                "status":"open",
                "category":"Extreme",
                "image":"https://example.com/images/carrera-desierto.jpg"
            },
                "time":"2025-09-25T00:00:00Z",
                "dorsal":42,
                "banned":false
            },
            {
                "id":405,
                "user":{
                "id":1,
                "email":"pepe@gmail.com",
                "roles":[
                "ROLE_USER"
                ],
                "name":"pepe",
                "banned":false
            },
                "race":{
                "id":10,
                "name":"Carrera del Desierto",
                "description":"Recorrido extremo en el Sahara",
                "date":"2025-06-10T00:00:00Z",
                "distance_km":150,
                "location":"Sahara, Marruecos",
                "coordinates":"lat:23.4162,lng:-13.6542",
                "unevenness":5,
                "entry_fee":50,
                "available_slots":3,
                "status":"open",
                "category":"Extreme",
                "image":"https://example.com/images/carrera-desierto.jpg"
            },
                "time":"2025-05-22T00:00:00Z",
                "dorsal":32,
                "banned":false
            },
            {
                "id":588,
                "user":{
                "id":1,
                "email":"pepe@gmail.com",
                "roles":[
                "ROLE_USER"
                ],
                "name":"pepe",
                "banned":false
            },
                "race":{
                "id":12,
                "name":"Tour de los Andes",
                "description":"Recorrido desafiante en los Andes",
                "date":"2025-10-05T00:00:00Z",
                "distance_km":250,
                "location":"Cusco, Perú",
                "coordinates":"lat:23.4162,lng:-13.6542",
                "unevenness":20,
                "entry_fee":90,
                "available_slots":5,
                "status":"open",
                "category":"Montaña",
                "image":"https://example.com/images/tour-andes.jpg"
            },
                "time":"2025-09-28T00:00:00Z",
                "dorsal":36,
                "banned":false
            }
        ]
        """.trimIndent()


    }

}
