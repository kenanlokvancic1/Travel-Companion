package com.productivity.myapplication.database

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class Wishlist() : RealmObject {
    @PrimaryKey
    var _wishlistId:ObjectId = BsonObjectId()
    var travelPlans : RealmList<TravelPlan> = realmListOf()
    var _userid:ObjectId = ObjectId()
}