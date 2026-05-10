package com.rizkyargopradana0005.assesmen1.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object Harvest : Screen("harvestScreen")
    data object Vending : Screen("vendingScreen")
    data object About : Screen("aboutScreen")
    data object History : Screen("historyScreen")
    data object Transaksi : Screen("detailScreen")
}