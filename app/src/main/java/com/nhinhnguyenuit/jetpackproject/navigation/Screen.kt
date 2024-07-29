package com.nhinhnguyenuit.jetpackproject.navigation

sealed class Screen(val route: String) {
    object ListScreen: Screen("user_list")
    object DetailBase: Screen("user_detail/")
    object DetailArgId: Screen("userId")
    object DetailScreen: Screen("user_detail/{userId}")
}