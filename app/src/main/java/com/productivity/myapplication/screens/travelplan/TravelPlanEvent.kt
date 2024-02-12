package com.productivity.myapplication.screens.travelplan

import com.productivity.myapplication.database.TravelPlan

sealed interface TravelPlanEvent {
    object initiateListOfTravelPlans: TravelPlanEvent
    data class DestinationChanged(val v: String): TravelPlanEvent
    data class DestinationBeingDeletedChanged(val v : String) : TravelPlanEvent
    data class DurationChanged(val v: String): TravelPlanEvent
    data class BudgetChanged(val v : String): TravelPlanEvent
    data class NumberOfPeopleChanged(val v : String): TravelPlanEvent
    data class PlacesOfInterestChanged(val v : String): TravelPlanEvent
    object showDialogue: TravelPlanEvent
    object insertTravelPlan: TravelPlanEvent
    data class selectedIndexChanged(val v: Int): TravelPlanEvent
    object showEditDialogue: TravelPlanEvent
    object updateTravelPlan: TravelPlanEvent
    object deleteTravelPlan: TravelPlanEvent
    object getSelectedTravelPlanData: TravelPlanEvent
    data class selectedTravelPlanChanged (val v: TravelPlan): TravelPlanEvent
    data class addToWishlist(val v: Boolean, val ve:TravelPlan): TravelPlanEvent
    object showInformationDialog: TravelPlanEvent

}