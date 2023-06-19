package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.common.network.Networking
import com.skillbox.unsplash.common.network.ServerItemsWrapper
import com.skillbox.unsplash.data.images.model.RemoteImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagesRepositoryImpl : ImagesRepositoryApi {

    override fun getAll(onComplete: (List<RemoteImage>) -> Unit, onError: (Throwable) -> Unit) {
        Networking.unsplashApi.searchImages().enqueue(
            object : Callback<ServerItemsWrapper<RemoteImage>> {
                override fun onResponse(
                    call: Call<ServerItemsWrapper<RemoteImage>>,
                    response: Response<ServerItemsWrapper<RemoteImage>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.items.orEmpty())
                    } else {
                        onError(Error(response.errorBody().toString()))
                    }
                }

                override fun onFailure(call: Call<ServerItemsWrapper<RemoteImage>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}