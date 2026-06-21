package com.rizkyargopradana0005.assesmen1.model
import com.squareup.moshi.Json

data class Transaksi(
    @Json(name = "id") val id: String = "",
    @Json(name = "judul") val judul: String,
    @Json(name = "harga") val harga: String,
    @Json(name = "jenis") val jenis: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "email") val email: String = "unknown"
)