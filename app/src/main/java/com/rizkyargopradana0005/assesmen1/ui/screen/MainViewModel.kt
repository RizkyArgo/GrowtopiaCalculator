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

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun clearData() {
        _data.value = emptyList()
        _isLoading.value = false
    }

    fun retrieveData(userEmail: String) {
        if (userEmail.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val result = TransaksiApi.service.getTransaksi()
                val filteredData = result.filter { it.email == userEmail }

                _data.value = filteredData
                Log.d("MainViewModel", "Success: $result")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failure: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }
}
