package com.rizkyargopradana0005.assesmen1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rizkyargopradana0005.assesmen1.ui.screen.AboutScreen
import com.rizkyargopradana0005.assesmen1.ui.screen.HarvestScreen
import com.rizkyargopradana0005.assesmen1.ui.screen.MainScreen
import com.rizkyargopradana0005.assesmen1.ui.screen.VendingScreen

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
    }
}