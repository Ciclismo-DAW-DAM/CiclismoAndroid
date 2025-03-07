package com.dam.ciclismoApp.ui.participations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dam.ciclismoApp.models.objects.Participant

class ParticipationsViewModel : ViewModel() {
    //region [Variables]
    private var _numParticipations = MutableLiveData<Int>().apply {
        value = 0
    }
    private var _mLisParticipations = MutableLiveData<List<Participant>>().apply {
        value = listOf()
    }
    //endregion


    //region [Livedata]
    var numParticipations: LiveData<Int> = _numParticipations
    var mLisParticipations: LiveData<List<Participant>> = _mLisParticipations
    //endregion


    //region [Getter & Setters]
    fun setNumParticipations(value: Int) {
        _numParticipations.value = value
    }
    fun setmListParticipations(value: List<Participant>) {
        _mLisParticipations.value = value
    }
    //endregion

}