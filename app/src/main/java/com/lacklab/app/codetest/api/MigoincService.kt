package com.lacklab.app.codetest.api

import com.lacklab.app.codetest.data.MigoincStatus
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MigoincService {

    @GET("status")
    suspend fun getStatus() : Response<MigoincStatus>

    companion object {
        private const val BASE_URL = "https://code-test.migoinc-dev.com/"

        fun create(url:String) : MigoincService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(MigoincService::class.java)
        }
    }
}