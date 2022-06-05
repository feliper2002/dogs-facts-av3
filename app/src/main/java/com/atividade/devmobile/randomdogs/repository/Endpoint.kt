package com.atividade.devmobile.randomdogs.repository

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {

    @GET("/api/facts")
    fun getRandomFact() : Call<JsonObject>
}