package com.dam.ciclismoApp.utils


import android.content.Context
import android.content.SharedPreferences

object P {

    private var instance: SharedPreferences? = null
    private var listener: OnSharedPreferencesChangeListener? = null
    private const val pName = "prefs"

    enum class I { //Int

    }

    enum class S { //String
        JSON_PARTICIPANTS, JSON_USER, JSON_RACES
    }

    enum class L { //Long

    }

    enum class B { //Boolean

    }

    fun init(context: Context) {
        if (instance == null) {
            instance = context.getSharedPreferences(pName, Context.MODE_PRIVATE)
        }
    }

    fun setOnSharedPreferencesChangeListener(listener: OnSharedPreferencesChangeListener?) {
        this.listener = listener
        registerListener()
    }

    private fun registerListener() {
        if (listener != null) {
            instance?.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
                listener?.onPreferenceChanged(sharedPreferences, key)
            }
        }
    }

    private fun unregisterListener() {
        if (listener != null) {
            instance?.unregisterOnSharedPreferenceChangeListener { _, _ -> }
        }
    }

    operator fun get(settingName: I): Int {
        return instance?.getInt(settingName.toString(), 0) ?: 0
    }

    operator fun get(settingName: S): String {
        return instance?.getString(settingName.toString(), "") ?: ""
    }

    operator fun get(settingName: L): Long {
        return instance?.getLong(settingName.toString(), 0L) ?: 0L
    }

    operator fun get(settingName: B): Boolean {
        return instance?.getBoolean(settingName.toString(), false) ?: false
    }

    operator fun set(settingName: I, value: Int) {
        instance?.edit()?.putInt(settingName.toString(), value)?.apply()
    }

    operator fun set(settingName: S, value: String) {
        instance?.edit()?.putString(settingName.toString(), value)?.apply()
    }

    operator fun set(settingName: L, value: Long) {
        instance?.edit()?.putLong(settingName.toString(), value)?.apply()
    }

    operator fun set(settingName: B, value: Boolean) {
        instance?.edit()?.putBoolean(settingName.toString(), value)?.apply()
    }

    // Agregado para acceder directamente por enum
    operator fun get(settingName: Enum<*>): Any? {
        return when (settingName) {
            is I -> get(settingName)
            is S -> get(settingName)
            is L -> get(settingName)
            is B -> get(settingName)
            else -> null
        }
    }

    // Agregado para establecer directamente por enum
    operator fun set(settingName: Enum<*>, value: Any) {
        when (settingName) {
            is I -> set(settingName, value as Int)
            is S -> set(settingName, value as String)
            is L -> set(settingName, value as Long)
            is B -> set(settingName, value as Boolean)
        }
    }

    fun inc(settingName: I) {
        set(settingName, get(settingName) + 1)
    }

    fun clear() {
        instance?.edit()?.clear()?.apply()
    }

    interface OnSharedPreferencesChangeListener {
        fun onPreferenceChanged(sharedPreferences: SharedPreferences, key: String?)
    }
}
