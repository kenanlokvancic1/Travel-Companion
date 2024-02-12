package com.productivity.myapplication.screens.travelplan

import com.productivity.myapplication.R
import com.productivity.myapplication.database.TravelPlan
import org.mongodb.kbson.ObjectId

data class TravelPlanState(val listOfTravelPlans: List<TravelPlan> = emptyList(),
                           val selectedTravelPlan: TravelPlan = TravelPlan(),
                           var isWishlisted: Boolean = false,
                           var showEditDialogue: Boolean = false,
                           var destination:String = "",
                           var duration:String = "",
                           var budget:String = "",
                           var number_of_people:String = "",
                           var places_of_interest:String = "",
                           var showDialogue: Boolean = false,
                           var listOfImages:List<Int> = listOf(R.drawable.amsterdam, R.drawable.athens, R.drawable.berlin, R.drawable.istanbul, R.drawable.paris),
                           var selectedImage:Int = 0,
                           val destinationId:ObjectId? = ObjectId(),
                           var showInfoDialog:Boolean = false,
                           val description:String = "",
  val destinationBeingDeleted : String = "",
  val destinationError : Boolean = false,
  val durationError : Boolean = false,
  val number_of_people_error : Boolean = false,
  val budgetError :Boolean =false,
  val places_of_interest_error : Boolean = false,
)