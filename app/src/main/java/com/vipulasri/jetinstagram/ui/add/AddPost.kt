package com.vipulasri.jetinstagram.ui.add

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vipulasri.jetinstagram.data.PostsRepository
import com.vipulasri.jetinstagram.model.currentUser
import com.vipulasri.jetinstagram.model.names

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddPost() {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AddPostTopBar()
        Post()
        Divider()
        TagPeople()
        Divider()
        AddLocation()
        Divider()
        AddFundraiser()
        Divider()
        PostToOtherInstagramAccounts()
        Divider()
        MediaPlatformTogglesList()
        Divider()
        Spacer(modifier = Modifier.weight(1f))
        AdvancedSettings()
    }
}

@Composable
private fun AddPostTopBar() {
    TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
            Text(text = "New Post", style = MaterialTheme.typography.h5)
            ClickableText(
                text = AnnotatedString("Share", SpanStyle(Color.Blue)),
                style = MaterialTheme.typography.h6,
                onClick = {},
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Post() {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Row() {
        Image(
            rememberImagePainter(PostsRepository.posts.value[0].image),
            "",
            Modifier
                .size(100.dp)
                .padding(12.dp),
            contentScale = ContentScale.Crop
        )
        TextField(
            modifier = Modifier.padding(top = 4.dp),
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Write a caption") }
        )
    }
}

@Composable
private fun TagPeople() {
    ExpandableSection(name = "Tag People") {
        InstagramAccountToggle(
            name = names[2],
            image = "https://randomuser.me/api/portraits/men/2.jpg"
        )
        InstagramAccountToggle(
            name = names[3],
            image = "https://randomuser.me/api/portraits/men/3.jpg"
        )
        InstagramAccountToggle(
            name = names[4],
            image = "https://randomuser.me/api/portraits/men/4.jpg"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddLocation() {
    ExpandableSection(name = "Add Location") {
        Text(
            text = "Tap here to show location suggestions",
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun AddFundraiser() {
    ExpandableSection(name = "Add Fundraiser") {
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun PostToOtherInstagramAccounts() {
    ExpandableSection(name = "Post To Other Instagram Accounts") {
        InstagramAccountToggle(name = currentUser.name, image = currentUser.image)
        InstagramAccountToggle(
            name = names[0],
            image = "https://randomuser.me/api/portraits/men/1.jpg"
        )
        InstagramAccountToggle(
            name = names[1],
            image = "https://randomuser.me/api/portraits/men/2.jpg"
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun InstagramAccountToggle(name: String, image: String) {
    var selected by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .background(color = Color.LightGray, shape = CircleShape)
                .clip(CircleShape)
        ) {
            Image(
                painter = rememberImagePainter(image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = name)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = selected,
            onCheckedChange = { selected = !selected },
        )
    }
}

@Composable
private fun ExpandableSection(name: String, content: @Composable () -> Unit) {
    var shouldExpand by remember {
        mutableStateOf(false)
    }
    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { shouldExpand = !shouldExpand }) {
            Text(
                text = name,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(12.dp)
            )
            IconButton(onClick = { shouldExpand = !shouldExpand }) {
                Icon(
                    if (!shouldExpand) Icons.Default.KeyboardArrowRight
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = ""
                )
            }
        }
        if (shouldExpand) content()
    }
}

@Composable
private fun MediaPlatformTogglesList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MediaPlatformToggle(name = "Face Book")
        MediaPlatformToggle(name = "Twitter")
    }
}

@Composable
private fun MediaPlatformToggle(name: String) {
    var selected by remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
    ) {
        Text(text = name)
        Switch(checked = selected, onCheckedChange = { selected = !selected })
    }
}

@Composable
private fun AdvancedSettings() {
    var shouldExpand by remember {
        mutableStateOf(false)
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable { shouldExpand = !shouldExpand }
        ) {
            Text(text = "Advanced Settings")
            IconButton(onClick = { shouldExpand = !shouldExpand }) {
                Icon(
                    if (!shouldExpand)
                        Icons.Default.KeyboardArrowRight
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        if (shouldExpand) {
            Spacer(modifier = Modifier.size(40.dp))
            Divider()
        }
    }
}