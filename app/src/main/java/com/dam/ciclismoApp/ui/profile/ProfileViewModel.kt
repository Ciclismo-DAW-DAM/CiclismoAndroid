package com.dam.ciclismoApp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private var _name = MutableLiveData<String>().apply {
        value = ""
    }
    private var _mail = MutableLiveData<String>().apply {
        value = ""
    }
    private var _age = MutableLiveData<Int>().apply {
        value = 0
    }
    private var _gender = MutableLiveData<String>().apply {
        value = ""
    }
    private var _totalParticipations = MutableLiveData<Int>().apply {
        value = 0
    }
    private var _prefLocation = MutableLiveData<String>().apply {
        value = ""
    }
    private var _prefCategory = MutableLiveData<String>().apply {
        value = ""
    }
    private var _kmTravelled = MutableLiveData<Int>().apply {
        value = 0
    }
    private var _imgProfile = MutableLiveData<String>().apply {
        value = ""
    }
    private var _totalSpent = MutableLiveData<Int>().apply {
        value = 0
    }
//endregion

    //region [Livedata]
    var name: LiveData<String> = _name
    var mail: LiveData<String> = _mail
    var age: LiveData<Int> = _age
    var gender: LiveData<String> = _gender
    var totalParticipations: LiveData<Int> = _totalParticipations
    var prefLocation: LiveData<String> = _prefLocation
    var prefCategory: LiveData<String> = _prefCategory
    var kmTravelled: LiveData<Int> = _kmTravelled
    var imgProfile: LiveData<String> = _imgProfile
    var totalSpent: LiveData<Int> = _totalSpent
//endregion

    //region [Getter & Setters]
    fun setName(value: String) {
        _name.value = value
    }
    fun setMail(value: String) {
        _mail.value = value
    }
    fun setAge(value: Int) {
        _age.value = value
    }
    fun setGender(value: String) {
        _gender.value = value
    }
    fun setTotalParticipations(value: Int) {
        _totalParticipations.value = value
    }
    fun setPrefLocation(value: String) {
        _prefLocation.value = value
    }
    fun setPrefCategory(value: String) {
        _prefCategory.value = value
    }
    fun setKmTravelled(value: Int) {
        _kmTravelled.value = value
    }
    fun setImgProfile(value: String) {
        _imgProfile.value = value
    }
    fun setTotalSpent(value: Int) {
        _totalSpent.value = value
    }
}