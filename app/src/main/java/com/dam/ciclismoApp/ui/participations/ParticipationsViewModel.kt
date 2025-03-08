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
    private var _filter = MutableLiveData<String>().apply {
        value = ""
    }
    private var _mLisParticipations = MutableLiveData<List<Participant>>().apply {
        value = listOf()
    }
    private var _mLisParticipationsFiltered = MutableLiveData<List<Participant>>().apply {
        value = listOf()
    }
    //endregion


    //region [Livedata]
    var numParticipations: LiveData<Int> = _numParticipations
    var filter: LiveData<String> = _filter
    var mLisParticipations: LiveData<List<Participant>> = _mLisParticipations
    var mLisParticipationsFiltered: LiveData<List<Participant>> = _mLisParticipationsFiltered
    //endregion


    //region [Getter & Setters]
    fun setNumParticipations(value: Int) {
        _numParticipations.value = value
    }
    fun setFilter(value: String) {
        _filter.value = value
    }
    fun setmListParticipations(value: List<Participant>) {
        _mLisParticipations.value = value
    }
    fun setmListParticipationsFiltered(value: List<Participant>) {
        _mLisParticipationsFiltered.value = value
    }
    //endregion

}