package com.example.may_tinh_v4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.may_tinh_v4.screens.*

object Screen {
    const val MAIN = "main_screen"
    const val BUTTON_CALCULATOR = "button_calculator"
    const val SPINNER_CALCULATOR = "spinner_calculator"
    const val LISTVIEW_CALCULATOR = "listview_calculator"
    const val RADIO_CALCULATOR = "radio_calculator"
}

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN
    ) {
        composable(route = Screen.MAIN) {
            MainScreen(navController = navController)
        }
        
        composable(route = Screen.BUTTON_CALCULATOR) {
            ButtonCalculatorScreen(navController = navController)
        }
        
        composable(route = Screen.SPINNER_CALCULATOR) {
            SpinnerCalculatorScreen(navController = navController)
        }
        
        composable(route = Screen.LISTVIEW_CALCULATOR) {
            ListViewCalculatorScreen(navController = navController)
        }
        
        composable(route = Screen.RADIO_CALCULATOR) {
            RadioCalculatorScreen(navController = navController)
        }
    }
}
