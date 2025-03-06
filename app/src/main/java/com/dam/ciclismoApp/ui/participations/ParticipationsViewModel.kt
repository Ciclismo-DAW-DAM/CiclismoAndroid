package com.dam.ciclismoApp.ui.participations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParticipationsViewModel : ViewModel() {
    //region [Variables]
    private var _numParticipations = MutableLiveData<Int>().apply {
        value = 0
    }
    private var _mLisParticipations = MutableLiveData<List<String>>().apply {
        value = listOf("1", "2", "3", "4", "5", "6")
    }
    //endregion


    //region [Livedata]
    var numParticipations: LiveData<Int> = _numParticipations
    var mLisParticipations: LiveData<List<String>> = _mLisParticipations
    //endregion


    //region [Getter & Setters]
    fun setNumParticipations(value: Int) {
        _numParticipations.value = value
    }
    fun setmListParticipations(value: List<String>) {
        _mLisParticipations.value = value
    }
    //endregion

}