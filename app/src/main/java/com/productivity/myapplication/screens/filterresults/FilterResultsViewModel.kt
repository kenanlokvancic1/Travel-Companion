package com.productivity.myapplication.screens.filterresults

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.DestinationsQueries
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.database.TravelPlanQueries
import com.productivity.myapplication.screens.travelplan.TravelPlanEvent
import com.productivity.myapplication.screens.travelplan.TravelPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterResultsViewModel @Inject constructor(private val travelPlan: TravelPlanQueries, private val destQueries: DestinationsQueries): ViewModel(){
    private val _state: MutableStateFlow<FilterResultsState> = MutableStateFlow(FilterResultsState())
    val state = _state.asStateFlow()


    fun onEvent(e:FilterResultsEvent){
        when(e){
            FilterResultsEvent.initiateListOfTravelPlans -> {
                viewModelScope.launch(Dispatchers.IO){
                        travelPlan.getAllTravelPlans().collect{changes : ResultsChange<TravelPlan> ->
                            when(changes){
                                is InitialResults ->{
                                    _state.update{it.copy(listOfTravelPlans = changes.list.filter { it.destinationId == state.value.destinationId})}
                                }
                                is UpdatedResults -> {
                                    _state.update{it.copy(listOfTravelPlans = changes.list.filter { it.destinationId == state.value.destinationId})}
                                }
                            }
                        }



                }
            }
            is FilterResultsEvent.updateWishlist -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if(e.ve.wishlisted){
                        travelPlan.updateWishlistStatus(tid = e.ve._travelPlanId, wish = false)
                    }else{
                        travelPlan.updateWishlistStatus(tid = e.ve._travelPlanId, wish = true)
                    }

                }

            }

            is FilterResultsEvent.idChanged -> {
                _state.update { it.copy(destinationId = e.v) }
            }

            FilterResultsEvent.showInformationDialog -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var destinationDest = destQueries.getDestinationByName(state.value.selectedTravelPlan.destination)
                    _state.update {
                        it.copy(showInfoDialog = !state.value.showInfoDialog, description = destinationDest.description)
                    }
                }

            }

            is FilterResultsEvent.selectedTravelPlanChanged -> {
                _state.update {
                    it.copy(selectedTravelPlan = e.v)
                }
            }
        }
    }
}