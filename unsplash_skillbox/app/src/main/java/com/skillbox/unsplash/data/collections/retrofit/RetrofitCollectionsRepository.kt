package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionRetrofitModel

interface RetrofitCollectionsRepository {

    suspend fun getAll(pageNumber: Int, pageSize: Int): List<CollectionRetrofitModel>
}