package com.skillbox.unsplash.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.local.db.contract.CollectionContract
import com.skillbox.unsplash.data.local.db.entities.CollectionEntity
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.local.db.entities.user.UserContract

@Dao
interface CollectionDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collections: List<CollectionEntity>)

    @Query("DELETE FROM ${CollectionContract.TABLE_NAME}")
    suspend fun clearAll()

    @Transaction
    @Query("SELECT * FROM ${CollectionContract.TABLE_NAME}")
    fun getCollections(): PagingSource<Int, CollectionWithUserAndImagesEntity>

    @Transaction
    @Query(
        "SELECT ${CollectionContract.TABLE_NAME}.* " +
                "FROM ${CollectionContract.TABLE_NAME} " +
                "INNER JOIN ${UserContract.TABLE_NAME} " +
                "ON ${CollectionContract.TABLE_NAME}.${CollectionContract.Columns.AUTHOR_ID} = ${UserContract.TABLE_NAME}.${UserContract.Columns.ID} " +
                "WHERE authors.nickname =:userName"
    )
    fun getUserCollections(userName: String): PagingSource<Int, CollectionWithUserAndImagesEntity>
}