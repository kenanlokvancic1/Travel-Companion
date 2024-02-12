package com.productivity.myapplication.screens.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.database.Wishlist
import com.productivity.myapplication.navigation.AppRoutes
import com.productivity.myapplication.screens.filterresults.FilterResultsEvent
import com.productivity.myapplication.screens.travelplan.TravelPlanEvent
import com.productivity.myapplication.screens.travelplan.TravelPlanState
import com.productivity.myapplication.screens.travelplan.labelValues
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WishlistScreen(events: (WishlistEvent) -> Unit,
                   state: WishlistState,
                   navController: NavController
){

    val snackbarHostState  = remember { SnackbarHostState() }
    val coroutineScope : CoroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        events(
            WishlistEvent.initiateListOfTravelPlans
        )
    })

    Scaffold(
   snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ){paddingValues->
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
                Text(text = "Wishlist", textAlign = TextAlign.Center, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        if (state.showInfoDialog){
            Dialog(onDismissRequest = {

                events(
                    WishlistEvent.showInformationDialog
                )


            }) {
                Card() {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
                        androidx.compose.material3.Text(text= state.description, modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                            textAlign = TextAlign.Center)


                        Button(onClick = {
                            events(
                                WishlistEvent.showInformationDialog
                            )
                        }) {
                            Text(text = "Close")

                        }

                    }
                }

            }
        }
        LazyColumn(modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)){
            items(state.listOfTravelPlans){plan->
                funcWishlist(travelPlan = plan, image = state.listOfImages[plan.indexOfImageList], updateWishlistStatus = {
                    events(
                        WishlistEvent.removeFromWishlist(false, plan)
                    )

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("${plan.destination}'s travel plan removed from wishlist")
                    }

                },

                    showInfo = {
                        events(
                            WishlistEvent.selectedTravelPlanChanged(it)
                        )
                        events(
                            WishlistEvent.showInformationDialog
                        )
                    })
            }
        }
    }
}
    }


}

@Composable
fun funcWishlist(travelPlan: TravelPlan, image: Int, updateWishlistStatus:(TravelPlan) -> Unit, showInfo: (TravelPlan) -> Unit){


    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Spacer(modifier = Modifier.width(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)){
                    androidx.compose.material3.Text(text = travelPlan.destination)
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "info", modifier = Modifier.clickable(onClick = {showInfo(travelPlan)}))
                    Icon(imageVector = Icons.Filled.Star, contentDescription = "wishlist", modifier = Modifier.clickable(onClick = {updateWishlistStatus(travelPlan) }) )

                }

            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Image(imageVector = ImageVector.vectorResource(image), contentDescription = "amsterdam", modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        labelValues(label = "duration", value = "${travelPlan.duration}")
                        labelValues(label = "budget", value = "${travelPlan.budget}" )
                        labelValues(label = "number of people", value = "${travelPlan.number_of_people}")


                    }
                    LazyVerticalGrid(columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)){
                        items(if(travelPlan.places_of_interest.split(",").isEmpty()) emptyList() else travelPlan.places_of_interest.split(",") ){ something->
                            OutlinedCard(modifier = Modifier.height(30.dp)) {
                                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    androidx.compose.material3.Text(text = something, modifier = Modifier.width(50.dp), textAlign = TextAlign.Center, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }

                            }

                        }
                    }
                }
            }

        }
    }
}