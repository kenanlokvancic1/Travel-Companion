package com.productivity.myapplication.database

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class TravelPlanQueries @Inject constructor(
    private var realm: Realm

)
{
    suspend fun addTravelPlan(dest: String, dur: Int, budg: Int, nop: Int, poi: String, ioi: Int, desc:String) {
        realm.write {
            val destinationForId = copyToRealm(Destinations().apply{
                name = dest
                description = desc
            })

            copyToRealm(TravelPlan().apply {
                destination = dest
                destinationId = destinationForId._id
                duration = dur
                budget = budg
                number_of_people = nop
                places_of_interest = poi
                indexOfImageList = ioi
            })
        }
    }
    suspend fun updateTravelPlan(tid:ObjectId,dest: String, dur: Int, budg: Int, nop: Int, poi: String, ioi: Int, wish: Boolean, did:ObjectId){
        realm.write {
            val destinationUpdate = query(clazz = Destinations::class,
                query = "_id == $0", did).find().first()
            destinationUpdate.name = dest
            val qObj = query(clazz = TravelPlan::class,
                query = "_travelPlanId == $0", tid).find().first()
            qObj.destination = dest
            qObj.duration = dur
            qObj.budget = budg
            qObj.number_of_people = nop
            qObj.places_of_interest = poi
            qObj.indexOfImageList= ioi
            qObj.wishlisted = wish
        }
    }

    suspend fun deleteTravelPlan(tid:ObjectId, did:ObjectId){
        realm.write {
            val travelPlansWithThisDestinationId = query(clazz = TravelPlan::class,
                query = "destinationId == $0", did).find()
            if(travelPlansWithThisDestinationId.size > 1){

                val qObj = query(clazz = TravelPlan::class,
                    query = "_travelPlanId == $0", tid).find().first()
                delete(qObj)
            }else{

                val destinationDelete = query(clazz = Destinations::class,
                    query = "_id == $0", did).find().first()
                val qObj = query(clazz = TravelPlan::class,
                    query = "_travelPlanId == $0", tid).find().first()
                delete(qObj)
                delete(destinationDelete)
            }
        }
    }

    suspend fun getAllTravelPlans(): Flow<ResultsChange<TravelPlan>> {
        return realm.query(clazz = TravelPlan::class).asFlow()
    }
    suspend fun getTravelPlansBasedOnDestinations(destId: ObjectId): Flow<ResultsChange<TravelPlan>> {
        return realm.query(clazz = TravelPlan::class,query = "destinationId == $0", destId).asFlow()


    }
    suspend fun getTravelPlansBasedOnWishlist(): Flow<ResultsChange<TravelPlan>> {
        return realm.query(clazz = TravelPlan::class,query = "wishlisted == $0", true ).asFlow()

    }

    suspend fun getTravelPlanBasedOnId(tid: ObjectId): TravelPlan{
        return realm.query(clazz = TravelPlan::class, query = "_travelPlanId == $0", tid).find().first()
    }

    suspend fun updateWishlistStatus(tid: ObjectId, wish: Boolean){
        realm.write {
            val qObj = query(clazz = TravelPlan::class,
                query = "_travelPlanId == $0", tid).find().first()

            qObj.wishlisted = wish
        }
    }

    suspend fun addTravelPlanWithoutDestination(dest: String, dur: Int, budg: Int, nop: Int, poi: String, ioi: Int, did: ObjectId){
        realm.write {
            copyToRealm(TravelPlan().apply {
                destination = dest
                destinationId = did
                duration = dur
                budget = budg
                number_of_people = nop
                places_of_interest = poi
                indexOfImageList = ioi
            })
        }
    }


//    suspend fun getDestinations():List<String>{
//
//    }


}