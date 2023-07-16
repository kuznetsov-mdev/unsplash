package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.model.RemoteImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ImageListRepositoryImpl @Inject constructor(
    private val network: Network
) : ImageListRepositoryApi {
    private val imagePerPage = 40

    override fun getImageList(onComplete: (List<RemoteImage>) -> Unit, onError: (Throwable) -> Unit) {
        network.unsplashApi.searchImages(imagePerPage).enqueue(
            object : Callback<List<RemoteImage>> {
                override fun onResponse(
                    call: Call<List<RemoteImage>>,
                    response: Response<List<RemoteImage>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.toList().orEmpty())
                    } else {
                        onError(Error(response.errorBody().toString()))
                    }
                }

                override fun onFailure(call: Call<List<RemoteImage>>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}