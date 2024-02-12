package com.productivity.myapplication.screens.wishlist

import com.productivity.myapplication.R
import com.productivity.myapplication.database.TravelPlan

data class WishlistState(
    val listOfTravelPlans: List<TravelPlan> = emptyList(),
    var listOfImages:List<Int> = listOf(R.drawable.amsterdam, R.drawable.athens, R.drawable.berlin, R.drawable.istanbul, R.drawable.paris),
    val selectedTravelPlanChanged: TravelPlan = TravelPlan(),
    var showInfoDialog:Boolean = false,
    val description:String = ""
)