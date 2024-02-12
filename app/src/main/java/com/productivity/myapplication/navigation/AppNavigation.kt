package com.productivity.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.productivity.myapplication.screens.dashboard.DashboardScreen
import com.productivity.myapplication.screens.dashboard.DashboardViewModel
import com.productivity.myapplication.screens.filterresults.FilterResultsScreen
import com.productivity.myapplication.screens.filterresults.FilterResultsViewModel
import com.productivity.myapplication.screens.login.LogInViewModel
import com.productivity.myapplication.screens.login.LoginScreen
import com.productivity.myapplication.screens.profile.ProfileScreen
import com.productivity.myapplication.screens.profile.ProfileViewModel
import com.productivity.myapplication.screens.signup.SignUpScreen
import com.productivity.myapplication.screens.signup.SignUpViewModel
import com.productivity.myapplication.screens.travelplan.TravelPlanScreen
import com.productivity.myapplication.screens.travelplan.TravelPlanViewModel
import com.productivity.myapplication.screens.wishlist.WishlistScreen
import com.productivity.myapplication.screens.wishlist.WishlistViewModel

@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = AppRoutes.SignUpScreen.route){


        composable(route = AppRoutes.LogInScreen.route){backStackEntry ->
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(AppRoutes.LogInScreen.route)
            }
            val loginViewModel = hiltViewModel<LogInViewModel>(parentEntry)
            val state by loginViewModel.state.collectAsState()
            val event = loginViewModel::onEvent
            LoginScreen(navController = navController, state = state, events = event)
        }

        composable(route = AppRoutes.SignUpScreen.route){backStackEntry->
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(AppRoutes.SignUpScreen.route)
            }
            val signUpViewModel = hiltViewModel<SignUpViewModel>(parentEntry)
            val state by signUpViewModel.state.collectAsState()
            val event = signUpViewModel::onEvent
            SignUpScreen(navController = navController, state = state, events = event)
        }

        composable(route = AppRoutes.DashboardScreen.route){backStackEntry->
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(AppRoutes.DashboardScreen.route)
            }
            val dashboardViewModel = hiltViewModel<DashboardViewModel>(parentEntry)
            val state by dashboardViewModel.state.collectAsState()
            val event = dashboardViewModel::onEvent
            val new_state by dashboardViewModel.new_state.collectAsState()
            DashboardScreen(state = state, events = event, new_state = new_state, navController = navController)
        }

        composable(route = AppRoutes.WishlistScreen.route){
            val wishlistViewModel = hiltViewModel<WishlistViewModel>()
            val state by wishlistViewModel.state.collectAsState()
            val event = wishlistViewModel::onEvent
            WishlistScreen(state = state, events = event, navController = navController)
        }

        composable(route = AppRoutes.TravelPlanScreen.route){
            val travelPlanViewModel = hiltViewModel<TravelPlanViewModel>()
            val state by travelPlanViewModel.state.collectAsState()
            val event = travelPlanViewModel::onEvent
            TravelPlanScreen(state= state,events = event, navController = navController)
        }

        composable(route = AppRoutes.FilterResultsScreen.route,
            arguments = listOf(
                navArgument(name = "id"){type = NavType.StringType}
            )){backStackEntry ->
            val filterResultsViewModel = hiltViewModel<FilterResultsViewModel>()
            val state by filterResultsViewModel.state.collectAsState()
            val event = filterResultsViewModel::onEvent
            val destinationId = backStackEntry.arguments?.getString("id")
            FilterResultsScreen(state = state, events = event, destinationId = destinationId!!, navController = navController)
        }

        composable(route = AppRoutes.ProfileScreen.route){backStackEntry->
            val parentEntry = remember(backStackEntry){
                navController.getBackStackEntry(AppRoutes.DashboardScreen.route)
            }
            val profileViewModel = hiltViewModel<ProfileViewModel>(parentEntry)
            val state by profileViewModel.state.collectAsState()
            val event = profileViewModel::onEvent
            ProfileScreen(state = state, events = event, navController = navController)
        }



    }



}