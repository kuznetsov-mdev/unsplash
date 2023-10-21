package com.skillbox.unsplash.data.images.room.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.data.images.storage.RetrofitImageRepository
import com.skillbox.unsplash.data.images.storage.RoomImageRepository

@OptIn(ExperimentalPagingApi::class)
class ImgRemoteMediator(
    private val roomImageRepository: RoomImageRepository,
    private val retrofitImageRepository: RetrofitImageRepository,
    private val query: String?
) : RemoteMediator<Int, ImageWithAuthorEntity>() {

    private var pageIndex = 0

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ImageWithAuthorEntity>): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> --pageIndex
                LoadType.APPEND -> ++pageIndex
            }
            val limit = state.config.pageSize
            val offset = pageIndex * limit

            val response = retrofitImageRepository.searchImages(query, offset, limit)

            MediatorResult.Success(true)
        } catch (e: Error) {
            MediatorResult.Error(e)
        }
    }
}