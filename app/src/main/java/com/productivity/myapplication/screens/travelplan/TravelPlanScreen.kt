package com.productivity.myapplication.screens.travelplan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.productivity.myapplication.R
import com.productivity.myapplication.components.MyTextFieldComponent
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.navigation.AppRoutes
import com.productivity.myapplication.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun TravelPlanScreen(events: (TravelPlanEvent) -> Unit,
                    state: TravelPlanState,
                     navController: NavController
                    ){

    val snackbarHostState  = remember { SnackbarHostState() }
    val coroutineScope : CoroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        events(
            TravelPlanEvent.initiateListOfTravelPlans
        )

    })
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                events(TravelPlanEvent.showDialogue)
            }

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add button")
            }
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
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

            LazyColumn(modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(10.dp)
            ){
                items(state.listOfTravelPlans){plan->
                    func(travelPlan = plan, image = state.listOfImages[plan.indexOfImageList],
                    edit = {
                                     events(
                                         TravelPlanEvent.selectedTravelPlanChanged(plan)

                                     )
                                    events(
                                        TravelPlanEvent.getSelectedTravelPlanData
                                    )
                                    events(
                                        TravelPlanEvent.showEditDialogue
                                    )

                    },
                    delete = {travelPlan->
                        events(
                            TravelPlanEvent.selectedTravelPlanChanged(travelPlan)
                        )

                        events(
                            TravelPlanEvent.deleteTravelPlan
                        )
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("${travelPlan.destination}'s travel plan deleted ")
                        }


                    },
                    updateWishlistStatus = {
                    if (plan.wishlisted){

                        events(
                            TravelPlanEvent.addToWishlist(false, plan)
                        )

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("${plan.destination}'s travel plan removed from wishlist")
                        }
                    }else{

                        events(
                            TravelPlanEvent.addToWishlist(true, plan)
                        )
                       coroutineScope.launch {
                           snackbarHostState.showSnackbar("${plan.destination}'s travel plan added to wishlist")
                       }
                    }
                    },
                    showInfo = {
                        events(
                            TravelPlanEvent.selectedTravelPlanChanged(it)
                        )
                        events(
                            TravelPlanEvent.showInformationDialog
                        )
                    }
                    )

                }
            }

            if (state.showDialogue){
                Dialog(onDismissRequest = {

                    events(
                        TravelPlanEvent.showDialogue
                    )


                }) {
                    Card() {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)){

                            MyTextFieldComponent(labelValue = "Destination", painterResource(id = R.drawable.destinations), onValueChanged = {destination->
                                events(TravelPlanEvent.DestinationChanged(destination))
                            },
                                value = state.destination, errorOrNot = state.destinationError)
                            MyTextFieldComponent(labelValue = "Duration", painterResource(id = R.drawable.duration), onValueChanged = {duration->
                                events(TravelPlanEvent.DurationChanged(duration))
                            },
                                value = state.duration, errorOrNot = state.durationError)
                            MyTextFieldComponent(labelValue = "Budget", painterResource(id = R.drawable.budget), onValueChanged = {budget->
                                events(TravelPlanEvent.BudgetChanged(budget))
                            },
                                value = state.budget, errorOrNot = state.budgetError)
                            MyTextFieldComponent(labelValue = "Number of People", painterResource(id = R.drawable.people), onValueChanged = {people->
                                events(TravelPlanEvent.NumberOfPeopleChanged(people))
                            }, value = state.number_of_people, state.number_of_people_error)
                            MyTextFieldComponent(labelValue = "Places of Interest", painterResource(id = R.drawable.places_of_interest), onValueChanged = {places_of_interest->
                                events(TravelPlanEvent.PlacesOfInterestChanged(places_of_interest))
                            },value = state.places_of_interest, errorOrNot = state.places_of_interest_error)
                            LazyRow(){
                                items(state.listOfImages){
                                    Box(modifier = Modifier.background(color = if (state.selectedImage == state.listOfImages.indexOf(it)) Primary else Color.Transparent)) {
                                        Image(imageVector = ImageVector.vectorResource(it), contentDescription = "amsterdam", modifier = Modifier
                                            .size(60.dp)
                                            .padding(10.dp)
                                            .clickable(onClick = {
                                                events(
                                                    TravelPlanEvent.selectedIndexChanged(
                                                        state.listOfImages.indexOf(
                                                            it
                                                        )
                                                    )
                                                )
                                            }))
                                    }
                                }
                            }

                            Button(onClick = {
                                events(TravelPlanEvent.insertTravelPlan)
                                events(TravelPlanEvent.showDialogue)

                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("${state.destination}'s travel plan added successfully")
                                }
                            }) {
                                androidx.compose.material.Text(text = "Save")

                            }

                        }
                    }

                }
            }
            if (state.showEditDialogue){
                Dialog(onDismissRequest = {

                    events(
                        TravelPlanEvent.showEditDialogue
                    )


                }) {
                    Card() {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)){

                            MyTextFieldComponent(labelValue = "Destination", painterResource(id = R.drawable.destinations), onValueChanged = {destination->
                                events(TravelPlanEvent.DestinationChanged(destination))
                            },value = state.destination, errorOrNot = state.destinationError)
                            MyTextFieldComponent(labelValue = "Duration", painterResource(id = R.drawable.duration), onValueChanged = {duration->
                                events(TravelPlanEvent.DurationChanged(duration))
                            }, value = state.duration, errorOrNot = state.durationError)
                            MyTextFieldComponent(labelValue = "Budget", painterResource(id = R.drawable.budget), onValueChanged = {budget->
                                events(TravelPlanEvent.BudgetChanged(budget))
                            }, value = state.budget, errorOrNot = state.budgetError)
                            MyTextFieldComponent(labelValue = "Number of People", painterResource(id = R.drawable.people), onValueChanged = {people->
                                events(TravelPlanEvent.NumberOfPeopleChanged(people))
                            }, value = state.number_of_people, errorOrNot = state.number_of_people_error)
                            MyTextFieldComponent(labelValue = "Places of Interest", painterResource(id = R.drawable.places_of_interest), onValueChanged = {places_of_interest->
                                events(TravelPlanEvent.PlacesOfInterestChanged(places_of_interest))
                            }, value = state.places_of_interest, errorOrNot = state.places_of_interest_error)
                            LazyRow(){
                                items(state.listOfImages){
                                    Box(modifier = Modifier.background(color = if (state.selectedImage == state.listOfImages.indexOf(it)) Primary else Color.Transparent)) {
                                        Image(imageVector = ImageVector.vectorResource(it), contentDescription = "amsterdam", modifier = Modifier
                                            .size(60.dp)
                                            .padding(10.dp)
                                            .clickable(onClick = {
                                                events(
                                                    TravelPlanEvent.selectedIndexChanged(
                                                        state.listOfImages.indexOf(
                                                            it
                                                        )
                                                    )
                                                )
                                            }))
                                    }
                                }
                            }

                            Button(onClick = {
                                events(TravelPlanEvent.updateTravelPlan)

                                if(!state.destinationError &&
                                    !state.durationError &&
                                    !state.budgetError &&
                                    !state.number_of_people_error &&
                                    !state.places_of_interest_error){

                                    events(TravelPlanEvent.showEditDialogue)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("${state.selectedTravelPlan.destination}'s travel plan edited successfully")
                                    }
                                }
                            }) {
                                androidx.compose.material.Text(text = "Save")

                            }

                        }
                    }

                }
            }

            if (state.showInfoDialog){
                Dialog(onDismissRequest = {

                    events(
                        TravelPlanEvent.showInformationDialog
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
                                events(TravelPlanEvent.showInformationDialog)

                            }) {
                                androidx.compose.material.Text(text = "Close")

                            }

                        }
                    }

                }
            }
        }



    }


}

@Composable
fun func(travelPlan: TravelPlan, image: Int, edit:(TravelPlan) -> Unit, delete:(TravelPlan) -> Unit, updateWishlistStatus:(TravelPlan) -> Unit, showInfo: (TravelPlan) -> Unit){


    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
                ){

                Text(text = travelPlan.destination, modifier = Modifier.padding(horizontal = 10.dp))
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)){
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "information", modifier = Modifier.clickable(onClick = {showInfo(travelPlan) }))
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit", modifier = Modifier.clickable(onClick = {edit(travelPlan) }))
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete", modifier = Modifier.clickable(onClick = {delete(travelPlan) }) )
                    Icon(imageVector = Icons.Filled.Star, tint = if(travelPlan.wishlisted) Color.Magenta else Color.Black, contentDescription = "wishlist", modifier = Modifier.clickable(onClick = {updateWishlistStatus(travelPlan) }) )

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
                                    Text(text = something, modifier = Modifier.width(50.dp), textAlign = TextAlign.Center, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }

                            }

                        }
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
                    Text(text = travelPlan.destination)
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "info", modifier = Modifier.clickable(onClick = {showInfo(travelPlan)}))
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "wishlist",
                            tint = if(travelPlan.wishlisted) Color.Magenta else Color.Black , modifier = Modifier.clickable(onClick = {updateWishlistStatus(travelPlan) }) )

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
                                    Text(text = something, modifier = Modifier.width(50.dp), textAlign = TextAlign.Center, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }

                            }

                        }
                    }
                }
            }

        }
    }
}


@Composable
fun labelValues(label:String, value:String){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 16.sp)
            Text(text = label, fontSize = 11.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun previewCardColo(){
    Card(){
        Icon(imageVector = Icons.Filled.Favorite,
            contentDescription = " ",
            modifier = Modifier.size(20.dp),
            tint = Color.Magenta)
    }
}

