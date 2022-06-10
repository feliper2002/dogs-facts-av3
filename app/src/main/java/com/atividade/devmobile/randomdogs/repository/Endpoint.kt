package com.atividade.devmobile.randomdogs.repository

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @GET("/api/facts")
    fun getRandomFact(@Query("number") number: String?) : Call<JsonObject>
}