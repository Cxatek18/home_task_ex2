package com.team.gamecircle.data.network

import com.team.gamecircle.data.network.model.ImageInfo
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @GET("320/240/dog")
    @Headers("Accept: application/json")
    suspend fun getImage(): ImageInfo
}