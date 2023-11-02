package com.skillbox.unsplash.data.images.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.ImageRoomModel
import com.skillbox.unsplash.data.images.room.model.UserRoomModel
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserRoomModel

@Dao
interface ImageDao {
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImages(): List<ImageRoomModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<ImageRoomModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(authors: List<UserRoomModel>)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImagesWithAuthor(): List<ImageWithUserRoomModel>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME}")
    fun deleteImages()

    @Query("DELETE FROM ${AuthorContract.TABLE_NAME}")
    fun deleteAuthors()

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.SEARCH_QUERY}=:query")
    fun getPagingSource(query: String): PagingSource<Int, ImageWithUserRoomModel>

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getPagingSource(): PagingSource<Int, ImageWithUserRoomModel>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.SEARCH_QUERY}=:query")
    fun clear(query: String)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE id=:id")
    fun getById(id: String): ImageWithUserRoomModel
}