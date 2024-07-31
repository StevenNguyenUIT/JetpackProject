package com.nhinhnguyenuit.jetpackproject.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
            MainScaffold(centerTopContent = "Github Users",
                content = { UserListScreen(navController = navController, paddingValues = it) },
                onClick = {})
        }
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(Screen.DetailArgLogin.route) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val login = backStackEntry.arguments?.getString(Screen.DetailArgLogin.route) ?: ""
            MainScaffold(centerTopContent = "User Details",
                content = { UserDetailScreen(login = login, innerPadding = it) },
                onClick = {navController.navigateUp()})
        }

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainScaffold(
    centerTopContent: String,
    content: @Composable (innerPadding: PaddingValues) -> Unit,
    onClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = centerTopContent) },
                navigationIcon = {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        }
    ) { paddingValue ->
        content(paddingValue)
    }
}