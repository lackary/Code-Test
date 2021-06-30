package com.lacklab.app.codetest.api

import com.lacklab.app.codetest.data.MigoincStatus
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface MigoincService {

    @GET("status")
    suspend fun getStatus() : Response<MigoincStatus>

    companion object {
        private const val BASE_URL = "https://code-test.migoinc-dev.com/"

        fun create(url:String) : MigoincService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MigoincService::class.java)
        }
    }
}