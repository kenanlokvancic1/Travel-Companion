package com.productivity.myapplication.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class User() : RealmObject {
    @PrimaryKey
    var _id:ObjectId = ObjectId()
    var username:String = ""
    var email:String = ""
    var password:String = ""
    var initializeData:Boolean = false
    var loggedIn:Boolean = false
}