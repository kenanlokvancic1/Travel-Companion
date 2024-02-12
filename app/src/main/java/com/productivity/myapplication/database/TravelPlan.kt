package com.productivity.myapplication.database

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class TravelPlan() : RealmObject {
    @PrimaryKey
    var _travelPlanId:ObjectId = ObjectId()
    var destination:String = ""
    var duration:Int = 0
    var budget:Int = 0
    var number_of_people:Int = 0
    var places_of_interest:String = ""
    var wishlisted:Boolean = false
    var indexOfImageList: Int = 0
    var destinationId: ObjectId = ObjectId()
    var _userid:ObjectId = ObjectId()
}




