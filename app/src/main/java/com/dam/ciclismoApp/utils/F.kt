package com.dam.ciclismoApp.utils
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dam.ciclismoApp.R
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class F {
    companion object {
        inline fun <reified T> parseJsonToList(json: String): List<T> {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val type = Types.newParameterizedType(List::class.java, T::class.java)
            val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
            return adapter.fromJson(json) ?: emptyList()
        }
        inline fun <reified T> parseJsonToObject(json: String): T? {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
            return adapter.fromJson(json)
        }
        inline fun formatFecha(fecha: String, mostrarHora: Boolean): String {
            val offsetDateTime = OffsetDateTime.parse(fecha)
            val pattern = if (mostrarHora) "dd/MM/yyyy HH:mm" else "dd/MM/yyyy"
            val formatter = DateTimeFormatter.ofPattern(pattern)

            return offsetDateTime.format(formatter)
        }

        inline fun showToast(
            context: Context,
            message: String,
            duration: Int = Toast.LENGTH_SHORT,
            backgroundColor: Int = R.color.black, // Color por defecto
            textColor: Int = Color.WHITE,
            icon: Int? = null
        ) {
            val inflater = LayoutInflater.from(context)
            val layout: View = inflater.inflate(R.layout.custom_toast, null)

            val toastText: TextView = layout.findViewById(R.id.toast_text)
            val toastIcon: ImageView = layout.findViewById(R.id.toast_icon)
            val toastContainer: View = layout.findViewById(R.id.toast_container)

            toastText.text = message
            toastText.setTextColor(textColor)
            toastContainer.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))

            icon?.let {
                toastIcon.setImageResource(it)
                toastIcon.visibility = View.VISIBLE
            } ?: run {
                toastIcon.visibility = View.GONE
            }

            val toast = Toast(context)
            toast.duration = duration
            toast.view = layout
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
        }
    }
}
