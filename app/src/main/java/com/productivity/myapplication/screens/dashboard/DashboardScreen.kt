package com.productivity.myapplication.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DashboardScreen (
    state: DashboardState,
    events: (DashboardEvent)-> Unit,
    new_state: NewDashboardState,
    navController: NavController
){

    DashboardScreenComponents(state, events, new_state, navController)



}