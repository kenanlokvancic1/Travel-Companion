package com.productivity.myapplication.screens.dashboard

sealed interface DashboardEvent{
    data class SearchQueryChange(val value: String): DashboardEvent
    object initializeDestinations: DashboardEvent

}