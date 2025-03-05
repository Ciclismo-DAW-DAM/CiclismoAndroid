package com.dam.ciclismoApp.ui.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private lateinit var user : String
    private lateinit var pass : String

    fun getUser(): String {
        return user
    }
    fun setUser(value: String) {
        user = value
    }
    fun getPass(): String {
        return pass
    }
    fun setPass(value: String) {
            pass = value
        }
}