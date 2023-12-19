package com.example.repository.favouriteads.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteAdDao {
    @Query("SELECT * FROM FAVOURITE_AD")
    fun getFavouritesFlow(): Flow<List<FavouriteAdDb>>

    @Insert
    fun insertFavourite(favourite: FavouriteAdDb)

    @Query("DELETE FROM FAVOURITE_AD WHERE :favouriteId == id")
    fun removeFavourite(favouriteId: String)
}