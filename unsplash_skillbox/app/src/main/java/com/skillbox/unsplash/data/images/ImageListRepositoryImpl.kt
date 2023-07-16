package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.model.RemoteImage
import okhttp3.ResponseBody
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

    override fun setLike(imageId: String, onComplete: () -> Unit, onError: (Throwable) -> Unit) {
        network.unsplashApi.setLike(imageId).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    onComplete()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    override fun removeLike(imageId: String, onComplete: () -> Unit, onError: (Throwable) -> Unit) {
        network.unsplashApi.removeLike(imageId).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    onComplete()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}