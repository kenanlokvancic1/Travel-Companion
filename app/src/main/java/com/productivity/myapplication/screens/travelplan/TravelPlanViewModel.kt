package com.productivity.myapplication.screens.travelplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.DestinationsQueries
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.database.TravelPlanQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class TravelPlanViewModel @Inject constructor(private val travelPlan: TravelPlanQueries, private val destQueries: DestinationsQueries): ViewModel(){
    private val _state: MutableStateFlow<TravelPlanState> = MutableStateFlow(TravelPlanState())
    val state = _state.asStateFlow()

    val hashMap = mutableMapOf<String, String>(
        Pair("Berlin", "A vibrant metropolis where history meets modernity. Rich cultural scene, renowned nightlife, and diverse culinary offerings await."),
        Pair("Paris", "The epitome of romance and elegance, Paris captivates with its iconic landmarks like the Eiffel Tower and Louvre Museum. Stroll along the Seine, indulge in gourmet cuisine, and embrace the city's artistic allure."),
        Pair("Amsterdam", "A picturesque city of canals and culture, Amsterdam charms with its historic architecture, world-class museums like the Van Gogh Museum, and vibrant street life. Bicycles, tulips, and cozy cafes define its unique ambiance."),
        Pair("Istanbul", "Bridging two continents, Istanbul is a captivating fusion of East and West. Explore its majestic mosques, bustling bazaars, and ancient palaces. Delight in the flavors of Turkish cuisine and soak in the city's enchanting atmosphere."),
        Pair("Athens", "Cradle of Western civilization, Athens boasts a wealth of archaeological treasures, including the Acropolis and Parthenon. Wander through its labyrinthine streets, savor traditional Greek dishes, and experience the city's timeless charm.")

    )

    private fun createContentDescription():String{
        var contentDesc = "No data available yet!"

        hashMap.forEach(){dest ->
            if(state.value.destination.contains(dest.key)){
                contentDesc = dest.value
            }

        }
        return contentDesc
    }

    fun onEvent(e:TravelPlanEvent){
        when(e){
            is TravelPlanEvent.BudgetChanged ->{
                if(e.v.isEmpty()){
                   _state.update {
                       it.copy(
                           budget = ""
                       )
                   }
                }else{
                    _state.update {
                        it.copy(budget = e.v)
                    }
                }

            }
            is TravelPlanEvent.DestinationChanged -> {
                if(e.v.isEmpty()){
                    _state.update {
                        it.copy(
                            destination = ""
                        )
                    }
                }else{
                    _state.update {
                        it.copy(destination = e.v)
                    }
                }
            }
            is TravelPlanEvent.DurationChanged -> {
                if(e.v.isEmpty()){
                    _state.update {
                        it.copy(
                            duration = ""
                        )
                    }
                }else{
                    _state.update {
                        it.copy(duration = e.v)
                    }
                }
            }
            is TravelPlanEvent.NumberOfPeopleChanged -> {
                if(e.v.isEmpty()){
                    _state.update {
                        it.copy(
                            number_of_people = ""
                        )
                    }
                }else{
                    _state.update {
                        it.copy(number_of_people = e.v)
                    }
                }
            }
            is TravelPlanEvent.PlacesOfInterestChanged -> {
                if(e.v.isEmpty()){
                    _state.update {
                        it.copy(
                            places_of_interest = ""
                        )
                    }
                }else{
                    _state.update {
                        it.copy(places_of_interest = e.v)
                    }
                }
            }
            TravelPlanEvent.initiateListOfTravelPlans -> {
                viewModelScope.launch(Dispatchers.IO){

                        travelPlan.getAllTravelPlans().collect{changes : ResultsChange<TravelPlan> ->
                            when(changes){
                                is InitialResults ->{
                                    _state.update{it.copy(listOfTravelPlans = changes.list)}
                                }
                                is UpdatedResults -> {
                                    _state.update{it.copy(listOfTravelPlans = changes.list)}
                                }
                            }
                        }




                }

            }

            TravelPlanEvent.showDialogue -> {
                _state.update {
                    it.copy(showDialogue = !state.value.showDialogue)
                }
                }

            TravelPlanEvent.insertTravelPlan -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val doesDestinationExist = destQueries.doesDestinationExist(state.value.destination)

                    if(doesDestinationExist){
                        val destination = destQueries.getDestinationByName(state.value.destination)

                        travelPlan.addTravelPlanWithoutDestination(
                            dest = state.value.destination,
                            budg = state.value.budget.toInt(), dur = state.value.duration.toInt(),
                            nop = state.value.number_of_people.toInt(),
                            poi = state.value.places_of_interest,
                            ioi = state.value.selectedImage,
                            did = destination._id
                        )
                    }else{
                        travelPlan.addTravelPlan(
                            dest = state.value.destination,
                            budg = state.value.budget.toInt(),
                            dur = state.value.duration.toInt(),
                            nop = state.value.number_of_people.toInt(),
                            poi = state.value.places_of_interest,
                            ioi = state.value.selectedImage,
                            desc = createContentDescription())
                    }
                }
            }

            is TravelPlanEvent.selectedIndexChanged -> {
                _state.update {
                    it.copy(selectedImage = e.v)
                }
            }

            TravelPlanEvent.deleteTravelPlan -> {
                viewModelScope.launch(Dispatchers.IO) {
                    travelPlan.deleteTravelPlan(
                       tid =  state.value.selectedTravelPlan._travelPlanId,
                        did = state.value.selectedTravelPlan.destinationId

                    )
                }
            }
            TravelPlanEvent.showEditDialogue -> {
                _state.update {
                    it.copy(showEditDialogue = !state.value.showEditDialogue)
                }
            }
            TravelPlanEvent.updateTravelPlan -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if(state.value.destination.isEmpty()){
                        _state.update { it.copy(destinationError = true) }
                    }else{
                        _state.update { it.copy(destinationError = false) }
                    }

                    if(state.value.duration.isEmpty()){
                        _state.update { it.copy(durationError = true) }
                    }else{
                        _state.update { it.copy(durationError = false) }
                    }


                    if(state.value.budget.isEmpty()){
                        _state.update { it.copy(budgetError = true) }
                    }else{
                        _state.update { it.copy(budgetError = false) }
                    }

                    if(state.value.number_of_people.isEmpty()){
                        _state.update { it.copy(number_of_people_error = true) }
                    }else{
                        _state.update { it.copy(number_of_people_error = false) }
                    }

                    if(state.value.places_of_interest.isEmpty()){
                        _state.update { it.copy(places_of_interest_error = true) }
                    }else{
                        _state.update { it.copy(places_of_interest_error = false) }
                    }

                    if(!state.value.durationError &&
                        !state.value.destinationError &&
                        !state.value.budgetError &&
                        !state.value.places_of_interest_error &&
                        !state.value.number_of_people_error){

                        travelPlan.updateTravelPlan(
                            tid = state.value.selectedTravelPlan._travelPlanId,
                            dest = state.value.destination,
                            dur = state.value.duration.toInt(),
                            budg = state.value.budget.toInt(),
                            nop = state.value.number_of_people.toInt(),
                            poi = state.value.places_of_interest,
                            ioi = state.value.selectedImage,
                            wish = state.value.isWishlisted,
                            did = state.value.selectedTravelPlan.destinationId

                        )
                    }

                }
            }

            TravelPlanEvent.getSelectedTravelPlanData -> {
                viewModelScope.launch(Dispatchers.IO) {val travelPlan =
                    travelPlan.getTravelPlanBasedOnId(
                        tid = state.value.selectedTravelPlan._travelPlanId

                    )
                    _state.update {
                        it.copy(destination = travelPlan.destination, duration =  travelPlan.duration.toString(), budget = travelPlan.budget.toString(), number_of_people = travelPlan.number_of_people.toString(),
                                places_of_interest = travelPlan.places_of_interest, selectedImage = travelPlan.indexOfImageList)
                    }
                }
            }

            is TravelPlanEvent.selectedTravelPlanChanged -> {
                _state.update {
                    it.copy(selectedTravelPlan = e.v)
                }
            }

            is TravelPlanEvent.addToWishlist -> {
                viewModelScope.launch(Dispatchers.IO) {
                travelPlan.updateWishlistStatus(tid = e.ve._travelPlanId, wish = e.v)
                }
            }

            TravelPlanEvent.showInformationDialog -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var destinationDest = destQueries.getDestinationByName(state.value.selectedTravelPlan.destination)
                    _state.update {
                        it.copy(showInfoDialog = !state.value.showInfoDialog, description = destinationDest.description)
                    }
                }

            }


            is TravelPlanEvent.DestinationBeingDeletedChanged -> {
                _state.update{it.copy(destinationBeingDeleted = e.v)}
            }


        }
    }
    }