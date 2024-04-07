package com.example.myapp.stations.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapp.core.domain.cache.utils.isCacheDataExpired
import com.example.myapp.core.domain.model.Result
import com.example.myapp.core.presentation.base.BaseViewModel
import com.example.myapp.stations.domain.model.Station
import com.example.myapp.stations.domain.use_case.GetLatestDataCheckTimeUseCase
import com.example.myapp.stations.domain.use_case.GetStationByIdUseCase
import com.example.myapp.stations.domain.use_case.GetStationKeywordsUseCase
import com.example.myapp.stations.domain.use_case.GetStationsUseCase
import com.example.myapp.stations.presentation.components.AutoCompleteTextInputItem
import com.example.myapp.stations.presentation.model.StationType
import com.example.myapp.stations.utils.Km
import com.example.myapp.stations.utils.LatLng
import com.example.myapp.stations.utils.getDistanceBetweenTwoPoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase,
    private val getStationKeywordsUseCase: GetStationKeywordsUseCase,
    private val getStationByIdUseCase: GetStationByIdUseCase,
    private val getLatestDataCheckTimeUseCase: GetLatestDataCheckTimeUseCase
) : BaseViewModel<StationsContract.Event, StationsContract.State, StationsContract.Effect>() {

    private val _latestDataCheckTime = MutableStateFlow<Long>(0)
    private val _stations = MutableStateFlow<List<Station>>(emptyList())

    private fun getLatestDataCheckTime() {
        viewModelScope.launch(Dispatchers.IO) {
            getLatestDataCheckTimeUseCase().flowOn(Dispatchers.IO).collectLatest {
                it?.let { dataCheckTime ->
                    _latestDataCheckTime.update { dataCheckTime.timestamp }
                    val station = _stations.value.firstOrNull()
                    station?.let {
                        val isCacheDataExpired =
                            isCacheDataExpired(station.createdAt, station.cacheTime)
                        if (isCacheDataExpired) {
                            fetchData()
                        }
                    }
                }
            }
        }
    }

    init {
        getLatestDataCheckTime()
    }

    suspend fun fetchData() {
        setState { copy(loading = true) }
        when (val stationKeywordsResult = getStationKeywordsUseCase()) {
            is Result.Success -> {
                when (val stationsResult = getStationsUseCase()) {
                    is Result.Success -> {
                        val sortedStationsByHits = stationsResult.data.sortedBy { it.hits }
                        _stations.update { sortedStationsByHits }
                        setState {
                            copy(
                                stations = sortedStationsByHits.map { station ->
                                    val stationKeyword =
                                        stationKeywordsResult.data.find { it.stationId == station.id }

                                    AutoCompleteTextInputItem(
                                        id = station.id,
                                        label = station.name,
                                        keyword = stationKeyword?.keyword
                                    )
                                },
                                loading = false
                            )
                        }
                    }

                    is Result.Error -> setState {
                        copy(
                            loading = false,
                            error = stationsResult.error
                        )
                    }
                }
            }

            is Result.Error -> setState {
                copy(
                    loading = false,
                    error = stationKeywordsResult.error
                )
            }
        }
    }

    fun onStationSelect(
        selectedStation: AutoCompleteTextInputItem,
        stationType: StationType
    ) {
        when (stationType) {
            StationType.START -> setState { copy(startStation = selectedStation) }
            StationType.END -> setState { copy(endStation = selectedStation) }
        }

        viewModelScope.launch(Dispatchers.IO) {
            fetchDistanceBetweenTwoPoints()
        }
    }

    private suspend fun fetchDistanceBetweenTwoPoints() {
        if (viewState.value.startStation == null || viewState.value.endStation == null) {
            return
        }

        when (val startStationResult =
            getStationByIdUseCase(viewState.value.startStation?.id ?: -1)) {
            is Result.Success -> {
                when (val endStationResult =
                    getStationByIdUseCase(viewState.value.endStation?.id ?: -1)) {
                    is Result.Success -> {
                        val distanceBetweenTwoPoints = getDistanceBetweenTwoPoints(
                            startPoint = LatLng(
                                startStationResult.data.latitude,
                                startStationResult.data.longitude
                            ),
                            endPoint = LatLng(
                                endStationResult.data.latitude,
                                endStationResult.data.longitude
                            ),
                        )
                        setDistanceBetweenStations(distanceBetweenTwoPoints)
                    }

                    is Result.Error -> Unit
                }
            }

            is Result.Error -> Unit
        }
    }

    private fun setDistanceBetweenStations(distanceBetweenTwoPoints: Km) {
        setState {
            copy(
                distanceBetweenStations = distanceBetweenTwoPoints.toString(),
            )
        }gi
    }

    override fun setInitialState(): StationsContract.State = StationsContract.State(
        startStation = null,
        endStation = null,
        stations = emptyList(),
        loading = true,
        error = null,
        distanceBetweenStations = null
    )

    override fun handleEvents(event: StationsContract.Event) {
        when (event) {
            is StationsContract.Event.Retry,
            is StationsContract.Event.FetchData -> viewModelScope.launch(Dispatchers.IO) {
                fetchData()
            }

            is StationsContract.Event.OnStationSelect -> onStationSelect(
                event.station,
                event.stationType
            )
        }
    }
}