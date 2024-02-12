package com.productivity.myapplication.screens.filterresults

import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.screens.travelplan.TravelPlanEvent
import org.mongodb.kbson.ObjectId


sealed interface FilterResultsEvent{
    data class updateWishlist(val ve: TravelPlan): FilterResultsEvent
    object initiateListOfTravelPlans: FilterResultsEvent
    data class idChanged(val v: ObjectId): FilterResultsEvent
    object showInformationDialog: FilterResultsEvent
    data class selectedTravelPlanChanged (val v: TravelPlan): FilterResultsEvent
}