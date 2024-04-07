package com.example.myapp.stations.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchScreenDestination(navController: NavHostController) {
    val viewModel: StationsViewModel = hiltViewModel()

    SearchScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
    ) { event -> viewModel.setEvent(event) }
}