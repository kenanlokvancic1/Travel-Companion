package com.productivity.myapplication.database

import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class UserQueries @Inject constructor(
    private var realm: Realm


){
    suspend fun addUser(uname: String, pass: String, mail: String) {
        realm.write {
            copyToRealm(User().apply {
                username = uname
                password = pass
                email = mail

            })
        }
    }

    suspend fun getUser(uname: String) {
        realm.write {
            copyToRealm(User().apply {
                username = uname
            })
        }
    }

    suspend fun getAllUsers(): Flow<ResultsChange<User>> {

            return realm.query(clazz = User::class).asFlow()
    }

    suspend fun getAllUsersWithoutFlow(): User{
        return realm.query(clazz = User::class).find().first()
    }

    suspend fun getAllUsersList() : User{
        return realm.query(clazz = User::class).find().first()
    }

    suspend fun checkUserExists(uname: String, pass: String, mail: String): Boolean{
        var list = emptyList<User>()
        realm.write {
            list = realm.query(clazz = User::class,
                query = "username CONTAINS $0 AND password CONTAINS $1 AND email CONTAINS $2", uname, pass, mail).find()
        }
        return list.isNotEmpty()
    }

    suspend fun updateUserProfile(uname: String, pass: String, mail: String, newuname: String){
        realm.write {
            val qObj = query(clazz = User::class,
                query = "username == $0", uname).find().first()
            qObj.username = newuname
            qObj.password = pass
            qObj.email = mail

        }
    }

    suspend fun deleteUser(id : ObjectId){
        realm.write {
            val qObj = query(clazz = User::class,
                query = "_id == $0", id).find().first()

            val travelPlans = query(clazz = TravelPlan::class).find()
            val destinations = query(clazz = Destinations::class).find()

            delete(travelPlans)
            delete(destinations)
            delete(qObj)
        }
    }

    suspend fun initializationStatus(uname: String, initializeUser: Boolean){
        realm.write {
            val qObj = query(clazz = User::class,
                query = "username == $0", uname).find().first()
            qObj.initializeData = initializeUser
        }
    }


    suspend fun getUserById(uid:ObjectId):User{
        return realm.query(clazz = User::class, query = "_id == $0", uid).find().first()
    }

    suspend fun getLoggedInUser():List<User>{
        return realm.query(clazz = User::class, query = "loggedIn == $0", true).find()
    }

    suspend fun updateLogInStatus(uname: String, loginstat: Boolean){
        realm.write {
            val qObj = query(clazz = User::class,
                query = "username == $0", uname).find().first()
            qObj.loggedIn = loginstat
        }
    }

}