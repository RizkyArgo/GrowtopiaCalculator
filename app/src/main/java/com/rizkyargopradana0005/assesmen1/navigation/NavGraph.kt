package com.rizkyargopradana0005.assesmen1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rizkyargopradana0005.assesmen1.ui.screen.*

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.Harvest.route) {
            HarvestScreen(navController)
        }
        composable(route = Screen.Vending.route) {
            VendingScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.History.route) {
            HistoryScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Transaksi.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.EditTransaksi.route,
            arguments = listOf(
                navArgument(KEY_ID_TRANSAKSI) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(KEY_ID_TRANSAKSI)
            DetailScreen(navController, id)
        }
    }
}