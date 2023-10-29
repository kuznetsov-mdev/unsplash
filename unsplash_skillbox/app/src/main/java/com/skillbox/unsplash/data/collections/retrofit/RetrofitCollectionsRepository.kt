package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.data.model.retrofit.collection.RetrofitCollectionModel

interface RetrofitCollectionsRepository {

    suspend fun getAll(pageNumber: Int, pageSize: Int): List<RetrofitCollectionModel>
}