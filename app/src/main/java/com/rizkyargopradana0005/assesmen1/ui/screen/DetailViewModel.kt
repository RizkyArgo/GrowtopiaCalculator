package com.rizkyargopradana0005.assesmen1.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import com.rizkyargopradana0005.assesmen1.network.TransaksiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val context: Context) : ViewModel() {

    suspend fun getTransaksi(id: String): Transaksi? {
        return withContext(Dispatchers.IO) {
            try {
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun insert(judul: String, harga: String, jenis: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newTransaksi = Transaksi(
                    id = "",
                    judul = judul,
                    harga = harga,
                    jenis = jenis,
                    imageUrl = "",
                    email = ""
                )
                TransaksiApi.service.addTransaksi(newTransaksi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update(id: String, judul: String, harga: String, jenis: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedTransaksi = Transaksi(
                    id = id,
                    judul = judul,
                    harga = harga,
                    jenis = jenis,
                    imageUrl = "",
                    email = ""
                )
                TransaksiApi.service.updateTransaksi(id, updatedTransaksi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                TransaksiApi.service.deleteTransaksi(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}