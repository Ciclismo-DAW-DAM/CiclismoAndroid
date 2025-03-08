package com.dam.ciclismoApp.ui.races

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dam.ciclismoApp.models.objects.Participant
import com.dam.ciclismoApp.models.objects.Race

class RacesViewModel : ViewModel() {

    //region [Variables]
    private var _numRaces = MutableLiveData<Int>().apply {
        value = 0
    }
    private var _mListRaces = MutableLiveData<List<Race>>().apply {
        value = listOf()
    }
    //endregion


    //region [Livedata]
    var numRaces: LiveData<Int> = _numRaces
    var mLisRaces: LiveData<List<Race>> = _mListRaces
    //endregion


    //region [Getter & Setters]
    fun setNumParticipations(value: Int) {
        _numRaces.value = value
    }
    fun setmListRaces(value: List<Race>) {
        _mListRaces.value = value
    }
    //endregion
}