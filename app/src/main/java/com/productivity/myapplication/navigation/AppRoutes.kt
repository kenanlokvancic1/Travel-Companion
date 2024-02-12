package com.productivity.myapplication.navigation

sealed class AppRoutes (val route:String){

    object LogInScreen: AppRoutes(
        route = "log_in_screen"
    )

    object SignUpScreen: AppRoutes(
        route = "sign_up_screen"
    )

    object TravelPlanScreen: AppRoutes(
        route = "travel_plan_screen"

    )

    object DashboardScreen: AppRoutes(
        route = "dashboard_screen"
    )

    object WishlistScreen: AppRoutes(
        route = "wishlist_screen"
    )

    object FilterResultsScreen: AppRoutes(
        route = "filter_results_screen/{id}"

    ){
        fun passId(id:String):String{
            return "filter_results_screen/$id"
        }
    }

    object ProfileScreen: AppRoutes(
        route = "profile_screen"
    )
}