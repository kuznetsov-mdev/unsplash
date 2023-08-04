package com.skillbox.unsplash.data.images.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
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

    fun insertImagesWithAuthor(remoteImage: List<RemoteImage>) {

    }
}