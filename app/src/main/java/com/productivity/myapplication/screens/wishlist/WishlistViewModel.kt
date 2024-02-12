package com.productivity.myapplication.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.DestinationsQueries
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.database.TravelPlanQueries
import com.productivity.myapplication.database.Wishlist
import com.productivity.myapplication.screens.filterresults.FilterResultsEvent
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
class WishlistViewModel @Inject constructor(private val travelPlan: TravelPlanQueries, private val destQueries: DestinationsQueries): ViewModel(){
    private val _state: MutableStateFlow<WishlistState> = MutableStateFlow(WishlistState())
    val state = _state.asStateFlow()

    fun onEvent(e: WishlistEvent){
        when(e){
            WishlistEvent.initiateListOfTravelPlans -> {
                viewModelScope.launch(Dispatchers.IO){
                    travelPlan.getAllTravelPlans().collect{changes : ResultsChange<TravelPlan> ->
                        when(changes){
                            is InitialResults ->{
                                _state.update{it.copy(listOfTravelPlans = changes.list.filter { it.wishlisted })}
                            }
                            is UpdatedResults -> {
                                _state.update{it.copy(listOfTravelPlans = changes.list.filter { it.wishlisted })}
                            }
                        }
                    }
                }

            }

            is WishlistEvent.removeFromWishlist -> {
                viewModelScope.launch(Dispatchers.IO){
                    travelPlan.updateWishlistStatus(tid = e.ve._travelPlanId, wish = false)
                }
            }
            is WishlistEvent.selectedTravelPlanChanged -> {
                _state.update {
                    it.copy(selectedTravelPlanChanged = e.v)
                }
            }

            WishlistEvent.showInformationDialog -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var destinationDest = destQueries.getDestinationByName(state.value.selectedTravelPlanChanged.destination)
                    _state.update {
                        it.copy(showInfoDialog = !state.value.showInfoDialog, description = destinationDest.description)
                    }
                }

            }
        }
        }
    }
