package com.atividade.devmobile.randomdogs.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogsClient() {

    companion object {
        fun get(path: String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(path).addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}