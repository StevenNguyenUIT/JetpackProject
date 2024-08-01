package com.nhinhnguyenuit.jetpackproject.ui.view

import android.view.Display
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nhinhnguyenuit.jetpackproject.data.model.User
import com.nhinhnguyenuit.jetpackproject.navigation.Screen
import com.nhinhnguyenuit.jetpackproject.ui.viewmodel.UserViewModel
import okhttp3.internal.http2.Http2Reader

@Composable
fun UserListScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val users = viewModel.userFlow.collectAsLazyPagingItems()
    val localUsers by viewModel.localUsers.collectAsState()

    if (localUsers.isNotEmpty()) {
//        Display users from local users
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp)
        ) {
            items(localUsers) { user ->
                UserCard(user = user, navController = navController)
            }
        }
    } else {
//        Display users from paging source
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp)
        ) {
            items(users.itemCount) { index ->
                val user = users[index]
                user?.let { UserCard(user, navController) }
            }
//            Handle Loading state
            when {
                users.loadState.refresh === LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                users.loadState.append === LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }

                users.loadState.refresh is LoadState.Error -> {
                    val e = users.loadState.refresh as LoadState.Error
                    item {
                        Text(
                            text = "Error: ${e.error.localizedMessage}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                users.loadState.append is LoadState.Error -> {
                    val e = users.loadState.append as LoadState.Error
                    item {
                        Text(
                            text = "Error loading more users: ${e.error.localizedMessage}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// UserCard will be reused for loading from local and from paging
@Composable
private fun UserCard(
    user: User,
    navController: NavHostController
) {
    UserItem(user = user,
        onClick = { navController.navigate("${Screen.DetailBase.route}${user.name}") },
        content = {
            Text(
                text = user.htmlUrl,
                fontSize = 15.sp,
                color = Color.Blue,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.padding(top = 10.dp)
            )
        })
}

// UserItem will be reused for ListScreen and DetailScreen
@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserItem(user: User, onClick: () -> Unit, content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(30.dp),
        modifier = Modifier.padding(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Card {
                Image(
                    painter = rememberImagePainter(data = user.avatarUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                HorizontalDivider(thickness = 2.dp)
                content()
            }
        }
    }
}