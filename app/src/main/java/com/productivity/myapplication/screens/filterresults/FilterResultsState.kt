package com.productivity.myapplication.screens.filterresults

import com.productivity.myapplication.R
import com.productivity.myapplication.database.TravelPlan
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

data class FilterResultsState(val listOfTravelPlans: List<TravelPlan> = emptyList(),
                              var listOfImages:List<Int> = listOf(R.drawable.amsterdam, R.drawable.athens, R.drawable.berlin, R.drawable.istanbul, R.drawable.paris),
                              val destinationId:ObjectId = ObjectId(),
                              val selectedTravelPlan: TravelPlan = TravelPlan(),
                              var showInfoDialog:Boolean = false,
                              val description:String = ""




)
