package com.example.myapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.stations.presentation.SearchScreenDestination

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.SEARCH
    ) {
        composable(
            route = Navigation.Routes.SEARCH
        ) {
            SearchScreenDestination(navController)
        }

    }
}

object Navigation {
    object Args {}

    object Routes {
        const val SEARCH = "search"
    }
}