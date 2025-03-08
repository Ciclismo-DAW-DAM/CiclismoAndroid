package com.dam.ciclismoApp.utils
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.memberProperties

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
            backgroundColor: Int = R.color.black,
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
            toastContainer.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, backgroundColor))

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

        suspend inline fun <reified T : Any> filterList(
            list: List<T>?,
            filterText: String,
            noinline attributes: ((T) -> List<String>)? = null
        ): List<T> = withContext(Dispatchers.IO) {
            val searchTerms = filterText.lowercase().split(" ").filter { it.isNotBlank() }

            list?.filter { item ->
                val searchableAttributes = attributes?.invoke(item)
                    ?: item::class.memberProperties.mapNotNull { prop ->
                        prop.getter.call(item)?.toString()
                    }

                searchTerms.all { term ->
                    searchableAttributes.any { it.lowercase().contains(term) }
                }
            } ?: emptyList()
        }

        inline fun openBrowser(url: String, context: Context) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showToast(context, "No se puede abrir el navegador", Toast.LENGTH_SHORT, R.color.red_spring)
            }
        }
    }
}
