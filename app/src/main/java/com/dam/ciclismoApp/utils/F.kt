package com.dam.ciclismoApp.utils
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

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
    }
}
