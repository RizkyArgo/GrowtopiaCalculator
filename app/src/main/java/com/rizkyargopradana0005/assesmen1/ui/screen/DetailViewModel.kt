package com.rizkyargopradana0005.assesmen1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkyargopradana0005.assesmen1.database.TransaksiDao
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private  val dao: TransaksiDao): ViewModel(){
    fun insert(judul: String, jumlah: String, jenis:String){
        val transaksi = Transaksi(
            judul = judul,
            jumlah = jumlah,
            jenis = jenis
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(transaksi)
        }
    }
    suspend fun getTransaksi(id: Long): Transaksi? {
        return dao.getTransaksiById(id)
    }

    fun update(id: Long,judul: String,jumlah: String,jenis: String){
        val transaksi = Transaksi(
            id = id,
            judul = judul,
            jumlah = jumlah,
            jenis = jenis
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(transaksi)
        }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}