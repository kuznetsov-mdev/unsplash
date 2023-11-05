package com.skillbox.unsplash.data.collections.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserRoomModel

@Dao
interface CollectionDao {
    @Transaction
    @Query("SELECT * FROM ${CollectionContract.TABLE_NAME}")
    fun getAll(): List<CollectionWithUserRoomModel>
}