package com.dam.ciclismoApp.ui.races

import android.content.Context
import android.view.LayoutInflater
import android.webkit.WebView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.dam.ciclismoApp.R
import com.dam.ciclismoApp.databinding.DialogRaceDetailBinding
import com.dam.ciclismoApp.models.objects.Race
import com.dam.ciclismoApp.models.repositories.ParticipantsRepository
import com.dam.ciclismoApp.utils.DialogManager
import com.dam.ciclismoApp.utils.F
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object RaceDetailDialog {
    fun setupRcDetailDialog(item: Race, context: Context, layoutInflater: LayoutInflater) {
        val dialogBinding = DialogRaceDetailBinding.inflate(layoutInflater)
        DialogManager.showCustomDialog(context, dialogBinding, false) { dialog ->
                // Obtener participaciones antes de cargar el mapa
                CoroutineScope(Dispatchers.Main).launch {
                    val participations = withContext(Dispatchers.IO) {
                        //CARGAR PARTICIPACIONES
                }}
            dialogBinding.apply {
                imgCloseButton.setOnClickListener {
                    dialog.dismiss()
                }
                txtMainName.setText(item.name)
                txtMainLocation.setText(item.location)
                imgRaceImage.apply {
                    load(item.image) {
                        placeholder(R.drawable.loading)
                        error(R.drawable.race)
                    }
                }
                //Check ban!
                if (false) {
                    txtStatus.setText("Vetado")
                    btnParticipate.setEnabled(false)
                } else {
                    txtStatus.setText(item.status)
                }
                txtDescription.setText(item.description)
                txtLocation.setText(item.location)
                txtDate.setText(F.formatFecha(item.date, true))
                txtSlotsAviable.setText(calcAviableSlots(item.slots).toString())
                txtEntryFee.setText(item.fee.toString())
                txtDistance.setText(item.distance.toString())
                txtUneveness.setText(item.unevenness.toString())
                txtCategory.setText(item.category)
                //Añadir dato del género (mas, fem, todos)
                var coords = item.coordinates
                    .replace("lat=", "")
                    .replace("lng=", "")
                    .replace(" ", "")
                initMap(webView, coords)
                //Cargar participantes si existen
                //Acción botón inscribirse
            }
        }
    }

    fun initMap(webView: WebView, coords: String) {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        val mapHtml = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
            <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
            <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
            <style> 
                html, body { margin: 0; padding: 0; height: 100%; }  
                #map { height: 100vh; width: 100vw; } 
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script>
                var coords = [$coords];
                var map = L.map('map').setView(coords, 13);
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
                var marker = L.marker(coords).addTo(map);
            </script>
        </body>
        </html>
    """.trimIndent()


        webView.loadDataWithBaseURL(null, mapHtml, "text/html", "UTF-8", null)
    }

    fun calcAviableSlots(maxSlots: Int): Int {
        //Temporal
        return 69
    }

}
