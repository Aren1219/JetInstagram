package com.vipulasri.jetinstagram.ui.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vipulasri.jetinstagram.data.PostsRepository
import com.vipulasri.jetinstagram.data.StoriesRepository
import com.vipulasri.jetinstagram.model.Post
import com.vipulasri.jetinstagram.ui.components.*
import com.vipulasri.jetinstagram.ui.home.StoriesSection
import com.vipulasri.jetinstagram.ui.theme.lightBlue


@Composable
fun Profile() {
    Scaffold(
        topBar = { ProfileToolbar() }) {
        val posts by PostsRepository.posts
        val stories by StoriesRepository.observeStories()

        Column {
            Row(modifier = Modifier.padding(end = 16.dp)) {
                ProfileDescription(modifier = Modifier.weight(3f))
                ProfileImage(
                    imageUrl = stories.first().image,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
            ProfileButtons()
            LazyColumn {
                item {
                    StoriesSection(stories)
                    Divider()
                }
            }
            ProfileTopBar()
            ShoppingGrid()
        }
    }
}


@Composable
fun ProfileToolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "katespadeny",
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            Icons.Default.MoreVert,
            contentDescription = ""
        )
    }
}

@Composable
fun ProfileDescription(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        val muted = Color.LightGray

        Text(
            text = "kate spade new york",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Clothing (Brand)",
            color = muted
        )
        Text(
            text = "quick and curious and playful and strong. \n" +
                    "follow us for a glimpse into the world of \n" +
                    "kate spade new york",
            fontSize = 13.sp
        )
        Text(
            text = "www.katespade.com/instagram\n" +
                    "            ",
            color = Color.Blue,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier)
        Row(modifier = Modifier) {
            Text(
                text = "2.5m ",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = "Followers  ",
                fontSize = 13.sp
            )
            Text(
                text = "1600 ",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = "Following",
                fontSize = 13.sp
            )
        }
        Row(modifier = Modifier) {
            Text(
                text = "Followed by ",
                color = Color.LightGray,
                fontSize = 13.sp
            )
            Text(
                text = "astridddzx ",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = "and ",
                color = Color.LightGray,
                fontSize = 13.sp
            )
            Text(
                text = "keysik",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ProfileImage(imageUrl: String, modifier: Modifier = Modifier) {
    val shape = CircleShape
    Box(
        modifier = modifier
            .diagonalGradientBorder(
                colors = listOf(
                    Color(0xFFd71069),
                    Color(0xFFe25d6a),
                    Color(0xFFe9ad55),
                ),
                shape = shape,
                isFromRight = true
            )
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .background(color = Color.LightGray, shape = shape)
                .clip(shape)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ProfileButtons() {
    Column() {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Button(
                modifier = Modifier.width(180.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = lightBlue),
            ) {
                Text(
                    text = "Follow",
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                modifier = Modifier.width(180.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            ) {
                Text(
                    text = "Message",
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Button(
                modifier = Modifier.width(180.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            ) {
                Text(
                    text = "Email",
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                modifier = Modifier.width(180.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            ) {
                Text(
                    text = "Browse",
                )
            }
        }
    }
}

@Composable
fun ProfileTopBar(
) {
    var tabIndex by remember {
        mutableStateOf(0)
    }
    val tabData = listOf("Grid", "List", "IGTV", "Shop", "Tagged")
    TabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier.height(50.dp),
        backgroundColor = Color.White
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(selected = tabIndex == index, onClick = { tabIndex = index }) {
                Text(text = text)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ShoppingCard(
    post: Post,
    modifier: Modifier = Modifier
) {

    Card(
        elevation = 12.dp,
        shape = RoundedCornerShape(12.dp),

        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberImagePainter(post.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(100.dp)
            )
            Text(
                text = (post.user.name),
                style = MaterialTheme.typography.h3.copy(fontSize = 12.sp),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(PostsRepository.posts.value) { item ->
            ShoppingCard(modifier = Modifier.padding(16.dp), post = item)
        }
    }
}
