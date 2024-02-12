package com.productivity.myapplication.screens.wishlist

import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.screens.filterresults.FilterResultsEvent
import com.productivity.myapplication.screens.travelplan.TravelPlanEvent

sealed interface WishlistEvent {
    object initiateListOfTravelPlans: WishlistEvent
    data class removeFromWishlist(val v: Boolean, val ve:TravelPlan): WishlistEvent
    data class selectedTravelPlanChanged(val v: TravelPlan): WishlistEvent
    object showInformationDialog: WishlistEvent



}