package com.productivity.myapplication.screens.filterresults

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.productivity.myapplication.navigation.AppRoutes
import com.productivity.myapplication.screens.travelplan.TravelPlanEvent

import com.productivity.myapplication.screens.travelplan.funcWishlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FilterResultsScreen(state: FilterResultsState,
                        events: (FilterResultsEvent) -> Unit,
                        destinationId: String,
                        navController: NavController){

    LaunchedEffect(key1 = Unit, block = {
        events(
            FilterResultsEvent.idChanged(org.mongodb.kbson.ObjectId(hexString = destinationId) )
        )
        events(
            FilterResultsEvent.initiateListOfTravelPlans
        )
    })

    val snackbarHostState  = remember { SnackbarHostState() }
    val coroutineScope : CoroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) {paddingValues ->
       Column(modifier = Modifier.padding(paddingValues)){

           Column(modifier = Modifier.fillMaxSize()) {
               Card (modifier = Modifier
                   .fillMaxWidth()
                   .padding(10.dp)) {
                   Row(modifier = Modifier
                       .fillMaxWidth()
                       .padding(10.dp),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.SpaceBetween) {
                       IconButton(onClick = {
                           navController.navigate(AppRoutes.DashboardScreen.route)
                       }, modifier = Modifier) {
                           Icon(imageVector = Icons.Filled.KeyboardBackspace, contentDescription = "back button")
                       }
                       Text(text = "Travel Plans", textAlign = TextAlign.Center, fontSize = 18.sp)
                       Spacer(modifier = Modifier.width(10.dp))
                   }
               }
               if (state.showInfoDialog){
                   Dialog(onDismissRequest = {

                       events(
                           FilterResultsEvent.showInformationDialog
                       )


                   }) {
                       Card() {
                           Column(modifier = Modifier
                               .fillMaxWidth()
                               .padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
                               Text(text= state.description, modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(5.dp),
                                   textAlign = TextAlign.Center)


                               Button(onClick = {
                                   events(
                                       FilterResultsEvent.showInformationDialog
                                   )
                               }) {
                                   androidx.compose.material.Text(text = "Close")

                               }

                           }
                       }

                   }
               }
               LazyColumn(modifier = Modifier.padding(10.dp),
                   verticalArrangement = Arrangement.spacedBy(10.dp)){
                   items(state.listOfTravelPlans){item->
                       funcWishlist(travelPlan = item, image = state.listOfImages[item.indexOfImageList], updateWishlistStatus = {
                           events(
                               FilterResultsEvent.updateWishlist(item)
                           )
                           if(item.wishlisted){

                               coroutineScope.launch{
                                   snackbarHostState.showSnackbar("${item.destination} removed from wishlist")
                               }
                           }else{

                               coroutineScope.launch{
                                   snackbarHostState.showSnackbar("${item.destination} added to wishlist")
                               }
                           }
                       },
                           showInfo = {
                               events(
                                   FilterResultsEvent.selectedTravelPlanChanged(it)
                               )
                               events(
                                   FilterResultsEvent.showInformationDialog
                               )
                           })
                   }
               }
           }
       }
    }

}