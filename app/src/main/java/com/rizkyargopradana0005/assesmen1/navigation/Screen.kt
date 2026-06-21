package com.rizkyargopradana0005.assesmen1.navigation

const val KEY_ID_TRANSAKSI = "idTransaksi"
sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object Harvest : Screen("harvestScreen")
    data object Vending : Screen("vendingScreen")
    data object About : Screen("aboutScreen")
    data object History : Screen("historyScreen")
    data object Transaksi : Screen("detailScreen")
    data object EditTransaksi : Screen("detailScreen/{$KEY_ID_TRANSAKSI}") {
        fun withId(id: String) = "detailScreen/$id"
    }
}