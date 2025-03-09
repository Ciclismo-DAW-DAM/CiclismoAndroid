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
    private var _mListRacesFiltered = MutableLiveData<List<Race>>().apply {
        value = listOf()
    }
    private var _filter = MutableLiveData<String>().apply {
        value = ""
    }
    //endregion


    //region [Livedata]
    var numRaces: LiveData<Int> = _numRaces
    var mLisRaces: LiveData<List<Race>> = _mListRaces
    var mLisRacesFiltered: LiveData<List<Race>> = _mListRacesFiltered
    var filter: LiveData<String> = _filter
    //endregion


    //region [Getter & Setters]
    fun setNumRace(value: Int) {
        _numRaces.value = value
    }
    fun setmListRaces(value: List<Race>) {
        _mListRaces.value = value
    }
    fun setmListRacesFiltered(value: List<Race>) {
        _mListRacesFiltered.value = value
    }
    fun setFilter(value: String) {
        _filter.value = value
    }

    //endregion
}