package com.dam.ciclismoApp.utils
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
        inline fun formatFecha(fecha: String, mostrarHora: Boolean): String {
            val offsetDateTime = OffsetDateTime.parse(fecha)
            val pattern = if (mostrarHora) "dd/MM/yyyy HH:mm" else "dd/MM/yyyy"
            val formatter = DateTimeFormatter.ofPattern(pattern)

            return offsetDateTime.format(formatter)
        }
    }
}
