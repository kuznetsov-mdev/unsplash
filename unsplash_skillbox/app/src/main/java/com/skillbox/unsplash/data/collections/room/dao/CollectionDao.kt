package com.skillbox.unsplash.data.collections.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.collections.room.model.CollectionEntity
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesEntity

@Dao
interface CollectionDao {
    @Transaction
    @Query("SELECT * FROM ${CollectionContract.TABLE_NAME}")
    fun getAll(): List<CollectionWithUserAndImagesEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(collections: List<CollectionEntity>)

    @Query("DELETE FROM ${CollectionContract.TABLE_NAME}")
    fun clearAll()

    @Transaction
    @Query("SELECT * FROM ${CollectionContract.TABLE_NAME}")
    fun getPagingSource(): PagingSource<Int, CollectionWithUserAndImagesEntity>
}