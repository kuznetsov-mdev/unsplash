package com.skillbox.unsplash.data.remote.retrofit

import com.skillbox.unsplash.data.remote.dto.ImageDto
import com.skillbox.unsplash.data.remote.dto.image.ImageDetailDto
import com.skillbox.unsplash.data.remote.dto.image.ImageSearchResultDto
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesApi {
    @GET("photos")
    suspend fun getImages(
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<ImageDto>

    @POST("/photos/{id}/like")
    suspend fun setLike(
        @Path("id") photoId: String
    ): ResponseBody

    @DELETE("/photos/{id}/like")
    suspend fun removeLike(
        @Path("id") photoId: String
    ): ResponseBody

    @GET("/photos/{id}")
    suspend fun getImageDetailInfo(
        @Path("id") imageId: String
    ): ImageDetailDto

    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): ImageSearchResultDto

    @GET("/collections/{id}/photos")
    suspend fun getCollectionImages(
        @Path("id") collectionId: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<ImageDto>

    @GET("/users/{userName}/photos")
    suspend fun getUserImages(
        @Path("userName") username: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<ImageDto>

    @GET("/users/{userName}/likes")
    suspend fun getLikedUserImages(
        @Path("userName") username: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<ImageDto>
}