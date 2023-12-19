package com.example.repository.favouriteads

import com.example.repository.favouriteads.room.FavouriteAdDao
import com.example.repository.favouriteads.room.FavouriteAdDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FavouriteAdsRepository(
    private val favouritesDao: FavouriteAdDao
) {
    fun getFavouritesFlow(): Flow<List<FavouriteAdDb>> = favouritesDao
        .getFavouritesFlow()
        .flowOn(Dispatchers.IO)

    suspend fun insertFavourite(favouriteAdDb: FavouriteAdDb) {
        withContext(Dispatchers.IO) {
            favouritesDao.insertFavourite(favouriteAdDb)
        }
    }

    suspend fun removeFavourite(favourite: FavouriteAdDb) {
        withContext(Dispatchers.IO) {
            favouritesDao.removeFavourite(favourite)
        }
    }
}