package com.productivity.myapplication.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.productivity.myapplication.navigation.AppRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DashboardScreenComponents(
    state: DashboardState,
    events: (DashboardEvent)-> Unit,
    new_state: NewDashboardState,
    navController: NavController
){
    var text by remember{ mutableStateOf("") }
    var active by remember{ mutableStateOf(false) }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    )

    {
        Scaffold(
            topBar = {
                SearchBar(
                    query = text,
                    onQueryChange ={
                        text = it
                        events(
                            DashboardEvent.SearchQueryChange(it)
                        )
                    } ,
                    onSearch ={
                        active= false
                    } ,
                    active = active,
                    onActiveChange = {
                        events(DashboardEvent.initializeDestinations)
                        active = it
                    },
                    placeholder ={
                        Text(text = "Search")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    trailingIcon = {
                        if(active){
                            Icon(
                                modifier = Modifier.clickable{
                                    if(text.isNotEmpty()){

                                        text = ""
                                    }else{
                                        active = false
                                    }

                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon"
                            )
                        }
                    }
                ) {
                    Column() {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
                            items(state.destinations){destination ->
                                Text(text = destination.name)

                            }
                        }
                    }



                }

            },
            bottomBar = {
                BottomNavigationBar(
                    items = listOf(
                        BottomNavItem(
                            screen = AppRoutes.DashboardScreen.route,
                            icon = Icons.Default.Home

                        ),
                        BottomNavItem(
                            screen = AppRoutes.WishlistScreen.route,
                            icon = Icons.Default.Star

                        ),
                        BottomNavItem(
                            screen = AppRoutes.TravelPlanScreen.route,
                            icon = Icons.Default.Map

                        ),
                        BottomNavItem(
                            screen = AppRoutes.ProfileScreen.route,
                            icon = Icons.Default.Person
                        )
                    ),
                    navController = navController,
                    onItemClick = { item ->
                        navController.navigate(item.screen)

                    }

                )
            }

        )
        {padding->
            Column(modifier = Modifier.padding(padding)) {
                LazyColumn(modifier = Modifier.padding(10.dp),verticalArrangement = Arrangement.spacedBy(10.dp)){
                    items(state.destinations){destination ->
                        DestinationCard(destinations = destination.name, onClick = {
                            navController.navigate(AppRoutes.FilterResultsScreen.passId(destination._id.toHexString()))
                        })

                    }
                }
            }



        }





    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){

    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = Modifier,
        containerColor = SnackbarDefaults.backgroundColor,
        tonalElevation = 5.dp
    ){
        items.forEach { item ->
            val selected = item.screen == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = item.screen == navController.currentDestination?.route,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Magenta
                ),
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if(item.badgeCount > 0){
                            BadgedBox(badge = {
                                Badge {
                                    Text(item.badgeCount.toString())
                                }
                            }) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = ""
                                )
                            }
                        }else{
                            Icon(
                                imageVector = item.icon,
                                contentDescription = ""
                            )
                        }
                        if(selected){
                            Text(
                                text = "",
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )

        }
    }

}
