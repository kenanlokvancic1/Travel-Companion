package com.productivity.myapplication.database

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DestinationsQueries @Inject constructor(
    private var realm: Realm

){

    suspend fun getAllDestinations(): Flow<ResultsChange<Destinations>> {
        return realm.query(clazz = Destinations::class).asFlow()
    }

    suspend fun doesDestinationExist(destinationName: String): Boolean{
        return realm.query(clazz = Destinations::class, query = "name == $0", destinationName).find().isNotEmpty()
    }

    suspend fun getDestinationByName(destinationName: String): Destinations{
        return realm.query(clazz = Destinations::class, query = "name == $0", destinationName).find().first()
    }

    suspend fun addDestinations(destinationName: String, destinationDescription: String){
        realm.write {
            copyToRealm(Destinations().apply {
                name = destinationName
                description = destinationDescription
            })
        }
    }


}