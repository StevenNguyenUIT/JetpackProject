package com.nhinhnguyenuit.jetpackproject.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nhinhnguyenuit.jetpackproject.navigation.Screen
import com.nhinhnguyenuit.jetpackproject.ui.viewmodel.UserViewModel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.nhinhnguyenuit.jetpackproject.R
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.utils.Helper.roundNumber

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserDetailScreen(
    login: String, viewModel: UserViewModel = hiltViewModel(),
    innerPadding: PaddingValues
) {
    val userDetail by viewModel.userDetail.collectAsState()

    LaunchedEffect(key1 = login) {
        viewModel.getUserDetail(login)
    }

    userDetail?.let { user ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(16.dp)
        ) {
            UserItem(user = user,
                onClick = { },
                content = {
                    Row(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "location"
                        )
                        Text(
                            text = user.location ?: "Unknown",
                            fontSize = 18.sp,
                        )
                    }
                })
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FollowCard(
                    image = R.drawable.followers,
                    num = user.followers.roundNumber(),
                    content = "Follower"
                )
                FollowCard(
                    image = R.drawable.following,
                    num = user.following.roundNumber(),
                    content = "Following"
                )
            }
            Text(
                text = "Blog",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = user.htmlUrl, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
private fun FollowCard(image: Int, num: Int, content: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "follower",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .padding(15.dp)
                    .size(30.dp)
                    .clip(CircleShape)

            )
        }
        Text(text = "${num}+", fontWeight = FontWeight.Bold)
        Text(text = content)
    }
}