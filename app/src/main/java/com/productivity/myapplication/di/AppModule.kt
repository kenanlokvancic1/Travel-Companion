package com.productivity.myapplication.di

import com.productivity.myapplication.database.Destinations
import com.productivity.myapplication.database.TravelPlan
import com.productivity.myapplication.database.TravelPlanQueries
import com.productivity.myapplication.database.User
import com.productivity.myapplication.database.UserQueries
import com.productivity.myapplication.database.Wishlist
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRealmConfig(): Realm
    {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Wishlist::class,
                User::class,
                TravelPlan::class,
                Destinations::class)
        ).compactOnLaunch().build()
        return Realm.open(configuration = config)
    }

    @Provides
    @Singleton
    fun provideUserQueries(r: Realm): UserQueries {
        return UserQueries(r)
    }

    @Provides
    @Singleton
    fun provideTravelPlanQueries(r : Realm): TravelPlanQueries {
        return TravelPlanQueries(r)
    }

}