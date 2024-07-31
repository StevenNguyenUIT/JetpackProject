package com.nhinhnguyenuit.jetpackproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nhinhnguyenuit.jetpackproject.ui.view.UserDetailScreen
import com.nhinhnguyenuit.jetpackproject.ui.view.UserListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {
        composable(route = Screen.ListScreen.route) {
            UserListScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(Screen.DetailArgLogin.route) { type = NavType.StringType })
        ) { backStackEntry ->
            val login = backStackEntry.arguments?.getString(Screen.DetailArgLogin.route) ?: ""
            UserDetailScreen(login = login)
        }

    }
}