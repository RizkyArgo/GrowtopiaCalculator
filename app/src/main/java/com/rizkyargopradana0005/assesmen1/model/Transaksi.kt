package com.rizkyargopradana0005.assesmen1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val judul: String,
    val jumlah: String,
    val jenis: String
)
