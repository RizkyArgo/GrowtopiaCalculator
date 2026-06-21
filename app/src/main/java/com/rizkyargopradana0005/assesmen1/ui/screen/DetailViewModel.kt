package com.rizkyargopradana0005.assesmen1.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import com.rizkyargopradana0005.assesmen1.network.TransaksiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModelProvider

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

    fun insert(judul: String, harga: String, jenis: String, email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newTransaksi = Transaksi(
                    id = "",
                    judul = judul,
                    harga = harga,
                    jenis = jenis,
                    imageUrl = "",
                    email = email
                )

                val response = TransaksiApi.service.addTransaksi(newTransaksi)
                println("Response API: $response")
                withContext(Dispatchers.Main) { onResult(true) }

            } catch (e: Exception) {
                println("Error POST: ${e.message}")
                e.printStackTrace()
                withContext(Dispatchers.Main) { onResult(false) }
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

    class DetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}