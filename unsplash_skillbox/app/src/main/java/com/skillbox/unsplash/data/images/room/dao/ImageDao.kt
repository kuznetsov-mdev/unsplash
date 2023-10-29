package com.skillbox.unsplash.data.images.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.model.room.RoomImageModel
import com.skillbox.unsplash.data.model.room.RoomUserModel
import com.skillbox.unsplash.data.model.room.relations.RoomImageWithUserModel

@Dao
interface ImageDao {
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImages(): List<RoomImageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<RoomImageModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(authors: List<RoomUserModel>)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImagesWithAuthor(): List<RoomImageWithUserModel>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME}")
    fun deleteImages()

    @Query("DELETE FROM ${AuthorContract.TABLE_NAME}")
    fun deleteAuthors()

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.SEARCH_QUERY}=:query")
    fun getPagingSource(query: String): PagingSource<Int, RoomImageWithUserModel>

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getPagingSource(): PagingSource<Int, RoomImageWithUserModel>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.SEARCH_QUERY}=:query")
    fun clear(query: String)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE id=:id")
    fun getById(id: String): RoomImageWithUserModel
}