package com.skillbox.unsplash.data.collections.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.skillbox.unsplash.data.collections.room.contract.CollectionImageContract

@Dao
interface CollectionImageDao {

    @Query(
        "INSERT INTO ${CollectionImageContract.TABLE_NAME} (" +
                "${CollectionImageContract.Columns.COLLECTION_ID}, " +
                "${CollectionImageContract.Columns.IMAGE_ID})" +
                "VALUES (:collectionId, :imageId)"
    )
    suspend fun insertCollectionImages(collectionId: String, imageId: String)

    @Query("DELETE FROM ${CollectionImageContract.TABLE_NAME} WHERE collection_id=:query")
    fun clearCollectionImages(query: String)
}