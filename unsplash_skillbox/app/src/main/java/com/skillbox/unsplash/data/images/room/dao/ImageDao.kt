package com.skillbox.unsplash.data.images.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.AuthorEntity
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity

@Dao
interface ImageDao {
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImages(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(authors: List<AuthorEntity>)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImagesWithAuthor(): List<ImageWithAuthorEntity>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME}")
    fun deleteImages()

    @Query("DELETE FROM ${AuthorContract.TABLE_NAME}")
    fun deleteAuthors()

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE description LIKE :query")
    fun getPagingSource(query: String): PagingSource<Int, ImageWithAuthorEntity>

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getPagingSource(): PagingSource<Int, ImageWithAuthorEntity>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME} WHERE description LIKE :query")
    fun clear(query: String)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE id=:id")
    fun getById(id: String): ImageWithAuthorEntity
}