package com.example.myapp.stations.presentation

import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.domain.model.Station
import com.example.myapp.stations.domain.model.StationKeyword
import com.example.myapp.stations.domain.use_case.GetStationByIdUseCase
import com.example.myapp.stations.domain.use_case.GetStationKeywordsUseCase
import com.example.myapp.stations.domain.use_case.GetStationsUseCase
import com.example.myapp.stations.presentation.components.AutoCompleteTextInputItem
import com.example.myapp.stations.presentation.model.StationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class StationsViewModelTest {
    private lateinit var viewModel: StationsViewModel
    private lateinit var getStationsUseCase: GetStationsUseCase
    private lateinit var getStationKeywordsUseCase: GetStationKeywordsUseCase
    private lateinit var getStationByIdUseCase: GetStationByIdUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getStationsUseCase = mock()
        getStationKeywordsUseCase = mock()
        getStationByIdUseCase = mock()
        viewModel = StationsViewModel(getStationsUseCase, getStationKeywordsUseCase, getStationByIdUseCase)
    }

    @Test
    fun `fetchData should update stations`() = runTest {
        whenever(getStationsUseCase()).doReturn(Result.Success(stations))
        whenever(getStationKeywordsUseCase()).doReturn(Result.Success(keywords))

        viewModel.fetchData()

        assertEquals(stations.size, viewModel.viewState.value.stations.size)
    }

    @Test
    fun `fetchData should handle error`() = runTest {
        val error = DataError.Network.SERVER_ERROR
        whenever(getStationsUseCase()).doReturn((Result.Error(error)))
        whenever(getStationKeywordsUseCase()).doReturn(Result.Success(emptyList()))

        viewModel.fetchData()

        assertEquals(error, viewModel.viewState.value.error)
    }

    @Test
    fun `onStationSelect should update state`() {
        val station = AutoCompleteTextInputItem(1, "Station 1", "Keyword 1")
        val stationType = StationType.START

        viewModel.onStationSelect(station, stationType)

        assertEquals(station, viewModel.viewState.value.startStation)
    }

    @Test
    fun `initial state should be correct`() {
        val initialState = viewModel.setInitialState()

        assertEquals(null, initialState.startStation)
        assertEquals(null, initialState.endStation)
        assertEquals(emptyList<AutoCompleteTextInputItem>(), initialState.stations)
        assertEquals(true, initialState.loading)
        assertEquals(null, initialState.error)
        assertEquals(null, initialState.distanceBetweenStations)
    }
}

val stations = listOf(
    Station(1, "Station 1", "station-1", 0.0, 0.0, 10, null, "City 1", "Region 1", "Country 1", "Localized Station 1", false, true, true),
    Station(2, "Station 2", "station-2", 0.0, 0.0, 5, null, "City 2", "Region 2", "Country 2", "Localized Station 2", false, false, false)
)
val keywords = listOf(
    StationKeyword(11, "Station 1", 1),
    StationKeyword(12, "Station 1", 2),
)
