package com.nhinhnguyenuit.jetpackproject.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nhinhnguyenuit.jetpackproject.navigation.Screen
import com.nhinhnguyenuit.jetpackproject.ui.viewmodel.UserViewModel

@Composable
fun UserListScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
){
    val users by viewModel.users.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(items = users){
            user ->
            Card(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("${Screen.DetailBase.route}${user.id}")
                        }
                        .padding(16.dp)
                    )
            }
        }
    }
}