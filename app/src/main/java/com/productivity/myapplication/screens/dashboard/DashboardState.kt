package com.productivity.myapplication.screens.dashboard

import com.productivity.myapplication.database.Destinations

data class DashboardState(val destinations: List<Destinations> = emptyList())

//data class DashboardDestinationsState(
//    val destinations: List<String>
//): DashboardState()
//
//data object DashboardInitialState: DashboardState()