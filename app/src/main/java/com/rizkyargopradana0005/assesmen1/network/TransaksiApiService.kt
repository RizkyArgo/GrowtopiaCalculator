package com.rizkyargopradana0005.assesmen1.network

import com.rizkyargopradana0005.assesmen1.model.Transaksi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://6a374310c105017aa638d23f.mockapi.io/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface TransaksiApiService {
    @GET("Transaksi")
    suspend fun getTransaksi(): List<Transaksi>

    @POST("Transaksi")
    suspend fun addTransaksi(@Body transaksi: Transaksi): Transaksi

    @PUT("Transaksi/{id}")
    suspend fun updateTransaksi(@Path("id") id: String, @Body transaksi: Transaksi): Transaksi

    @DELETE("Transaksi/{id}")
    suspend fun deleteTransaksi(@Path("id") id: String): Transaksi
}

object TransaksiApi {
    private const val BASE_URL = "https://6a374310c105017aa638d23f.mockapi.io/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val service: TransaksiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TransaksiApiService::class.java)
    }
}