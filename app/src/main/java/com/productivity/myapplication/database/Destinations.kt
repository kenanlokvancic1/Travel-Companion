package com.productivity.myapplication.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Destinations (): RealmObject{
    @PrimaryKey
    var _id:ObjectId = ObjectId()
    var name: String = ""
    var description: String = ""
    var _userid:ObjectId = ObjectId()
}