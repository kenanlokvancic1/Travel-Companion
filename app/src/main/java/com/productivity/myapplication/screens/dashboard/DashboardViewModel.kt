package com.productivity.myapplication.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productivity.myapplication.database.Destinations
import com.productivity.myapplication.database.DestinationsQueries
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.database.TravelPlanQueries
import com.productivity.myapplication.database.UserQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val destinationsQueries: DestinationsQueries, private val travelPlanQueries: TravelPlanQueries, private val userQueries:UserQueries): ViewModel(){

    private val _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()
    private val _new_state = MutableStateFlow(NewDashboardState())
    val new_state: StateFlow<NewDashboardState> = _new_state.asStateFlow()

    init {
if (state.value.destinations.isEmpty()){
    viewModelScope.launch(Dispatchers.IO)
    {
        createTravelPlans()
        getAllDestinations()
    }
}
    }

    val hashMap = mutableMapOf<String, String>(
        Pair("Berlin", "A vibrant metropolis where history meets modernity. Rich cultural scene, renowned nightlife, and diverse culinary offerings await."),
        Pair("Paris", "The epitome of romance and elegance, Paris captivates with its iconic landmarks like the Eiffel Tower and Louvre Museum. Stroll along the Seine, indulge in gourmet cuisine, and embrace the city's artistic allure."),
        Pair("Amsterdam", "A picturesque city of canals and culture, Amsterdam charms with its historic architecture, world-class museums like the Van Gogh Museum, and vibrant street life. Bicycles, tulips, and cozy cafes define its unique ambiance."),
        Pair("Istanbul", "Bridging two continents, Istanbul is a captivating fusion of East and West. Explore its majestic mosques, bustling bazaars, and ancient palaces. Delight in the flavors of Turkish cuisine and soak in the city's enchanting atmosphere."),
        Pair("Athens", "Cradle of Western civilization, Athens boasts a wealth of archaeological treasures, including the Acropolis and Parthenon. Wander through its labyrinthine streets, savor traditional Greek dishes, and experience the city's timeless charm.")

    )


    private fun destinationFunction(destinationName:String):String{

        var listOfPlacesBerlin = listOf<String>("Brandenburg Gate", "Berlin Wall", "Museum Island", "Reichstag", "East Side Gallery")
        var listOfPlacesParis = listOf<String>("Louvre", "Eiffel Tower", "Notre-Dame", "Montmarte", "Seine River Cruise")
        var listOfPlacesIstanbul = listOf<String>("Bosphorus Cruise", "Blue Mosque", "Grand Bazaar", "Hagia Sophia", "Topkapi Palace")
        var listOfPlacesAmsterdam = listOf<String>("Van Gogh Museum", "Anne Frank House", "Rijksmuseum", "Vondelpark", "Jordaan")
        var listOfPlacesAthens = listOf<String>("Plaka", "Monastiraki", "Temple of Zeus", "Acropolis", "Archeological Museum")

        return when(destinationName){
            "Berlin" -> {
                var places = ""
               var placesCount = (3..5).random()
                val countOfPlaces = placesCount
                var listOfPlaces = mutableListOf<String>()

                while (listOfPlaces.size !== countOfPlaces){
                    var place = listOfPlacesBerlin.random()

                    if (!listOfPlaces.contains(place)) {
                        if (placesCount == 1) {
                            places += (place)
                        } else {
                            places += (place + ",")
                        }
                        listOfPlaces.add(place)
                    }
                    placesCount -= 1

                }
                var cleanPlaces = places.dropLast(1)
                return cleanPlaces
            }
            "Paris" -> {
                var places = ""
                var placesCount = (3..5).random()
                val countOfPlaces = placesCount
                var listOfPlaces = mutableListOf<String>()

                while (listOfPlaces.size !== countOfPlaces){
                    var place = listOfPlacesParis.random()

                    if (!listOfPlaces.contains(place)) {
                        if (placesCount == 1) {
                            places += (place)
                        } else {
                            places += (place + ",")
                        }
                        listOfPlaces.add(place)
                    }
                    placesCount -= 1

                }
                var cleanPlaces = places.dropLast(1)
                return cleanPlaces
            }
            "Istanbul" ->{
                var places = ""
                var placesCount = (3..5).random()
                val countOfPlaces = placesCount
                var listOfPlaces = mutableListOf<String>()

                while (listOfPlaces.size !== countOfPlaces){
                    var place = listOfPlacesIstanbul.random()

                    if (!listOfPlaces.contains(place)) {
                        if (placesCount == 1) {
                            places += (place)
                        } else {
                            places += (place + ",")
                        }
                        listOfPlaces.add(place)
                    }
                    placesCount -= 1

                }
                var cleanPlaces = places.dropLast(1)
                return cleanPlaces
            }
            "Amsterdam" ->{
                var places = ""
                var placesCount = (3..5).random()
                val countOfPlaces = placesCount
                var listOfPlaces = mutableListOf<String>()

                while (listOfPlaces.size !== countOfPlaces){
                    var place = listOfPlacesAmsterdam.random()

                    if (!listOfPlaces.contains(place)) {
                        if (placesCount == 1) {
                            places += (place)
                        } else {
                            places += (place + ",")
                        }
                        listOfPlaces.add(place)
                    }
                    placesCount -= 1

                }
                var cleanPlaces = places.dropLast(1)
                return cleanPlaces
            }
            "Athens"->{
                var places = ""
                var placesCount = (3..5).random()
                val countOfPlaces = placesCount
                var listOfPlaces = mutableListOf<String>()

                while (listOfPlaces.size !== countOfPlaces){
                    var place = listOfPlacesAthens.random()

                    if (!listOfPlaces.contains(place)) {
                        if (placesCount == 1) {
                            places += (place)
                        } else {
                            places += (place + ",")
                        }
                        listOfPlaces.add(place)
                    }
                    placesCount -= 1

                }
                var cleanPlaces = places.dropLast(1)
                return cleanPlaces
            }
            else -> {
                ""
            }
        }
    }

    private fun imageFunction(images: String):Int{
        return when(images){
            "Berlin" -> {
                2
            }
            "Paris" -> {
                4
            }
            "Istanbul" ->{
                3
            }
            "Amsterdam" ->{
                0
            }
            "Athens"->{
                1
            }
            else -> {
                5
            }
        }
    }


    suspend fun createTravelPlans(){

        hashMap.forEach{dest ->
            var numberEquals = 2
            while (numberEquals > 0){
                val doesDestinationExist = destinationsQueries.doesDestinationExist(dest.key)

                if(doesDestinationExist){
                    val destination = destinationsQueries.getDestinationByName(dest.key)

                    travelPlanQueries.addTravelPlanWithoutDestination(
                        dest = dest.key,
                        budg = (3000..6000).random(),
                        dur = (5..14).random(),
                        nop = (3..5).random(),
                        poi = destinationFunction(dest.key),
                        ioi = imageFunction(dest.key),
                        did = destination._id
                    )
                }else{
                    travelPlanQueries.addTravelPlan(
                        dest = dest.key,
                        budg = (3000..6000).random(),
                        dur = (5..14).random(),
                        nop = (3..5).random(),
                        poi = destinationFunction(dest.key),
                        ioi = imageFunction(dest.key),
                        desc = dest.value
                    )
                }
                numberEquals -= 1


            }

            numberEquals = 2

        }


    }

    fun getAllDestinations() = viewModelScope.launch(Dispatchers.IO) {
        destinationsQueries.getAllDestinations().collect{changes : ResultsChange<Destinations> ->
            when(changes){
                is InitialResults ->{
                    _state.update{it.copy(destinations = changes.list)}
                }
                is UpdatedResults -> {
                    _state.update{it.copy(destinations = changes.list)}
                }
            }
        }
    }

    fun onEvent(e: DashboardEvent) {
        when(e){
            is DashboardEvent.SearchQueryChange -> viewModelScope.launch(Dispatchers.IO) {
                if(e.value.isNotEmpty()){
                    destinationsQueries.getAllDestinations().collect{changes : ResultsChange<Destinations> ->
                        when(changes){
                            is InitialResults ->{
                                _state.update{it.copy(destinations = changes.list.filter { it.name.lowercase().contains(e.value.lowercase()) })}
                            }
                            is UpdatedResults -> {
                                _state.update{it.copy(destinations = changes.list.filter { it.name.lowercase().contains(e.value.lowercase()) })}
                            }
                        }
                    }
                }else {
                    destinationsQueries.getAllDestinations()
                        .collect { changes: ResultsChange<Destinations> ->
                            when (changes) {
                                is InitialResults -> {
                                    _state.update { it.copy(destinations = changes.list) }
                                }

                                is UpdatedResults -> {
                                    _state.update { it.copy(destinations = changes.list) }
                                }
                            }
                        }
                }
            }
            DashboardEvent.initializeDestinations -> {
                getAllDestinations()
            }
        }
    }

}