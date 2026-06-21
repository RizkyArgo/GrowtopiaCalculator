package com.rizkyargopradana0005.assesmen1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import com.rizkyargopradana0005.assesmen1.network.TransaksiApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _data = MutableStateFlow<List<Transaksi>>(emptyList())
    val data: StateFlow<List<Transaksi>> = _data.asStateFlow()


    private fun retrieveData(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TransaksiApi.service.getTransaksi()
                Log.d("MainViewModel", "Success: $result")
                _data.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failure: ${e.message}")
            }
        }
    }
}
