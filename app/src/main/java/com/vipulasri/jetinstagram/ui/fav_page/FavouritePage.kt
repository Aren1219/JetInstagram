package com.vipulasri.jetinstagram.ui.fav_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vipulasri.jetinstagram.data.StoriesRepository
import com.vipulasri.jetinstagram.ui.theme.grey100
import com.vipulasri.jetinstagram.ui.theme.grey50
import com.vipulasri.jetinstagram.ui.theme.lightBlue

@Preview(showBackground = true)
@Composable
fun FavScaffold() {
    var searchTerm by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier
            .background(color = grey50)
            .fillMaxWidth(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Close, contentDescription = "")
                    }
                },
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "Favorites",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Add, contentDescription = "")
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier) {

                TopNotification()
                Spacer(modifier = Modifier.height(10.dp))
                SearchBar(searchTerm) {
                    searchTerm = it
                }
                Spacer(modifier = Modifier.height(10.dp))
                ContentLazyColumn(searchTerm)
            }
        },
    )
}


@Composable
fun TopNotification() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("New posts from your favorites will appear higher in feed. Only you can see who you add or remove. ")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("How it works")
            }
        }, modifier = Modifier
            .background(grey100)
            .padding(18.dp), fontSize = 15.sp, textAlign = TextAlign.Center
    )
}


@Composable
fun SearchBar(searchTerm: String, onTextChange: (String) -> Unit) {
    TextField(
        value = searchTerm,
        onValueChange = onTextChange,
        placeholder = { Text(text = "Search") },
        modifier = Modifier
//            .height(40.dp)
            .background(grey50)
            .fillMaxWidth(1f)
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentLazyColumn(searchTerm: String) {
    val list = StoriesRepository.observeStories()
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 18.dp)
    ) {
        items(
            items = list.value.filter { it.name.contains(searchTerm, true) },
            itemContent = { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberImagePainter(item.image),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop,
                        )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = item.name)
                        Text(
                            text = item.name.replace(" ", ""),
                            style = TextStyle.Default.copy(color = Color.Gray, fontSize = 12.sp)
                        )
                    }
                    val stateFavButton = mutableStateOf(true)

                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = lightBlue,
                                contentColor = Color.White
                            ),
                            onClick = { stateFavButton.value = !stateFavButton.value },
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .height(25.dp)
                                .width(65.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = if (stateFavButton.value) {
                                    "Add"
                                } else {
                                    "Remove"
                                },
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            })
    }
}