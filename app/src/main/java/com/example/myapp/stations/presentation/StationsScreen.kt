package com.example.myapp.stations.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.core.presentation.components.Indicator
import com.example.myapp.core.presentation.components.NetworkError
import com.example.myapp.core.presentation.utils.asUiText
import com.example.myapp.stations.presentation.components.AutoCompleteTextInput
import com.example.myapp.stations.presentation.components.AutoCompleteTextInputItem
import com.example.myapp.stations.presentation.model.StationType
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    state: StationsContract.State,
    effectFlow: Flow<StationsContract.Effect>?,
    onEventSent: (event: StationsContract.Event) -> Unit
) {
    LaunchedEffect(true) {
        onEventSent(StationsContract.Event.FetchData)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        when {
            state.loading -> LoadingComponent()
            state.error != null -> ErrorComponent(
                errorText = state.error.asUiText().asString(),
                onRetryClick = {
                    onEventSent(StationsContract.Event.Retry)
                })

            else -> SearchComponent(
                distanceBetweenStations = state.distanceBetweenStations,
                stations = state.stations,
                onStationSelect = { station, stationType ->
                    onEventSent(StationsContract.Event.OnStationSelect(station, stationType))
                }
            )
        }
    }
}

@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Indicator()
    }
}

@Composable
fun ErrorComponent(errorText: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkError(text = errorText, onRetryClick = onRetryClick)
    }
}

@Composable
fun SearchComponent(
    distanceBetweenStations: String?,
    stations: List<AutoCompleteTextInputItem>,
    onStationSelect: (station: AutoCompleteTextInputItem, stationType: StationType) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AutoCompleteTextInput(
            searchOnlyByKeyword = true,
            data = stations,
            label = context.getString(R.string.start_station_label),
            placeholderRes = R.string.start_station_placeholder,
            onSelect = {
                onStationSelect(it, StationType.START)
            }
        )
        AutoCompleteTextInput(
            searchOnlyByKeyword = true,
            data = stations,
            label = context.getString(R.string.end_station_label),
            placeholderRes = R.string.end_station_placeholder,
            onSelect = {
                onStationSelect(it, StationType.END)
            }
        )
        distanceBetweenStations?.let {
            Text(text = context.getString(R.string.distance_between_stations_in_km, it))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MyAppTheme {
        SearchScreen(
            state = StationsContract.State(
                startStation = null,
                endStation = null,
                stations = emptyList(),
                loading = false,
                error = null,
                distanceBetweenStations = null
            ),
            effectFlow = null,
            onEventSent = {}
        )
    }
}