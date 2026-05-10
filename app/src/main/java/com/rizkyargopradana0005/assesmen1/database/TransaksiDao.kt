package com.rizkyargopradana0005.assesmen1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaksiDao {

    @Insert
    suspend fun insert(transaksi: Transaksi)

    @Update
    suspend fun update(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi ORDER BY id asc")
    fun getTransaksi(): Flow<List<Transaksi>>

    @Query("SELECT * FROM transaksi WHERE id = :id")
    suspend fun getTransaksiById(id: Long): Transaksi?

    @Query("DELETE FROM transaksi WHERE id = :id")
    suspend fun deleteById(id: Long)
}