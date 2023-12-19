package com.example.repository.favouriteads.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteAdDb::class],
    version = 1,
)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouriteAdDao

    companion object {
        fun makeDao(context: Context): FavouriteAdDao = Room
            .databaseBuilder(context, FavouritesDatabase::class.java, "favourites_db")
            .build()
            .favouritesDao()
    }
}