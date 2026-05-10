package com.rizkyargopradana0005.assesmen1.ui.screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rizkyargopradana0005.assesmen1.database.TransaksiDao
import com.rizkyargopradana0005.assesmen1.model.Transaksi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: TransaksiDao): ViewModel() {
    val data: StateFlow<List<Transaksi>> = dao.getTransaksi().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun Factory(dao: TransaksiDao): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(dao) as T
        }
    }
}