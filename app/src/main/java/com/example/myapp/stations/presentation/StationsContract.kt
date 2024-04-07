package com.example.myapp.stations.presentation

import androidx.annotation.StringRes
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.presentation.base.ViewEvent
import com.example.myapp.core.presentation.base.ViewSideEffect
import com.example.myapp.core.presentation.base.ViewState
import com.example.myapp.stations.presentation.components.AutoCompleteTextInputItem
import com.example.myapp.stations.presentation.model.StationType

class StationsContract {
    sealed class Event : ViewEvent {
        data class OnStationSelect(
            val station: AutoCompleteTextInputItem,
            val stationType: StationType
        ) : Event()

        data object Retry : Event()
        data object FetchData : Event()
    }

    data class State(
        val stations: List<AutoCompleteTextInputItem>,
        val startStation: AutoCompleteTextInputItem?,
        val endStation: AutoCompleteTextInputItem?,
        val distanceBetweenStations: String?,
        val loading: Boolean,
        val error: DataError?
    ) : ViewState

    sealed class Effect : ViewSideEffect
}