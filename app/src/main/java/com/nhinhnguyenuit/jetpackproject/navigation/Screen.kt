package com.nhinhnguyenuit.jetpackproject.navigation

sealed class Screen(val route: String) {
    data object ListScreen: Screen("user_list")
    data object DetailBase: Screen("user_detail/")
    data object DetailArgLogin: Screen("login")
    data object DetailScreen: Screen("user_detail/{login}")
}