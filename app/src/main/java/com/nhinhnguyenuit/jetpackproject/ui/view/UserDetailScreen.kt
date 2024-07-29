package com.nhinhnguyenuit.jetpackproject.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhinhnguyenuit.jetpackproject.ui.viewmodel.UserViewModel

@Composable
fun UserDetailScreen(
    id: Int, viewModel: UserViewModel = hiltViewModel()
) {
    val users by viewModel.users.collectAsState()
    val user = users.find { it.id == id }
    user?.let {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Title: ${it.name}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Avatar: ${it.avatarUrl}", style = MaterialTheme.typography.titleMedium)
        }
    }
}