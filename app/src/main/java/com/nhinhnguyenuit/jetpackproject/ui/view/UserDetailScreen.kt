package com.nhinhnguyenuit.jetpackproject.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nhinhnguyenuit.jetpackproject.ui.viewmodel.UserViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserDetailScreen(
    login: String, viewModel: UserViewModel = hiltViewModel()
) {
    val userDetail by viewModel.userDetail.collectAsState()

    LaunchedEffect(key1 = login) {
        viewModel.getUserDetail(login)
    }

    userDetail?.let { user->
        Column(
                modifier = Modifier.padding(16.dp)
        ) {
            Image(painter = rememberImagePainter(data = user.avatarUrl), contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Title: ${user.name}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Location: ${user.location ?: "Unknown"}")
            Text(text = "Followers: ${user.followers}")
            Text(text = "Following: ${user.following}")
            Text(text = "Html_url: ${user.htmlUrl}")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "View on GitHub")
            }
        }
    }
}