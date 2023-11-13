package com.skillbox.unsplash.data.collections.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.collections.room.model.CollectionRoomModel
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesRoomModel

@Dao
interface CollectionDao {
    @Transaction
    @Query("SELECT * FROM ${CollectionContract.TABLE_NAME}")
    fun getAll(): List<CollectionWithUserAndImagesRoomModel>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(collections: List<CollectionRoomModel>)

    @Query("DELETE FROM ${CollectionContract.TABLE_NAME}")
    fun clearAll()

    @Transaction
    @Query("SELECT * FROM ${CollectionContract.TABLE_NAME}")
    fun getPagingSource(): PagingSource<Int, CollectionWithUserAndImagesRoomModel>
}