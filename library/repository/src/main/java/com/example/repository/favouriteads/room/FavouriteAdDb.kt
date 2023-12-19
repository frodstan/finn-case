package com.example.repository.favouriteads.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FAVOURITE_AD")
data class FavouriteAdDb(
    @PrimaryKey
    val id: String,
)
