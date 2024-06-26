package com.skillbox.unsplash.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skillbox.unsplash.data.local.db.contract.CollectionImageContract
import com.skillbox.unsplash.data.local.db.contract.ImageContract
import com.skillbox.unsplash.data.local.db.entities.ImageEntity
import com.skillbox.unsplash.data.local.db.entities.image.ImageWithUserEntity
import com.skillbox.unsplash.data.local.db.entities.user.UserContract

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<ImageEntity>)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImagesWithUser(): List<ImageWithUserEntity>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME}")
    fun clearAll()

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.SEARCH_QUERY}=:query")
    fun getImagesPagingSource(query: String): PagingSource<Int, ImageWithUserEntity>

    @Transaction
    @Query(
        "SELECT ${ImageContract.TABLE_NAME}.* FROM ${ImageContract.TABLE_NAME} " +
                "INNER JOIN ${CollectionImageContract.TABLE_NAME} " +
                "ON ${ImageContract.TABLE_NAME}.${ImageContract.Columns.ID} = ${CollectionImageContract.TABLE_NAME}.${CollectionImageContract.Columns.IMAGE_ID} " +
                "WHERE ${CollectionImageContract.TABLE_NAME}.${CollectionImageContract.Columns.COLLECTION_ID} = :collectionId"
    )
    fun getCollectionImagesPagingSource(collectionId: String): PagingSource<Int, ImageWithUserEntity>

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME}")
    fun getImagesPagingSource(): PagingSource<Int, ImageWithUserEntity>

    @Query("DELETE FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.SEARCH_QUERY}=:query")
    fun clearImages(query: String)

    @Query("DELETE FROM images WHERE id IN (SELECT image_id FROM collection_image WHERE collection_id = :collectionId)")
    fun clearCollectionImages(collectionId: String)

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE id=:id")
    fun getById(id: String): ImageWithUserEntity

    @Transaction
    @Query(
        "SELECT ${ImageContract.TABLE_NAME}.* " +
                "FROM ${ImageContract.TABLE_NAME} " +
                "INNER JOIN ${UserContract.TABLE_NAME} " +
                "ON ${ImageContract.TABLE_NAME}.${ImageContract.Columns.AUTHOR_ID} = ${UserContract.TABLE_NAME}.${UserContract.Columns.ID} " +
                "WHERE authors.nickname =:userName"
    )
    fun getUserImages(userName: String): PagingSource<Int, ImageWithUserEntity>

    @Transaction
    @Query("SELECT * FROM ${ImageContract.TABLE_NAME} WHERE ${ImageContract.Columns.LIKED_BY_USER}= 1")
    fun getLikedUserImages(): PagingSource<Int, ImageWithUserEntity>
}