package com.elguille.purrfectpics.ui.cats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.extensions.pictureUrl
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun CatsScreen(
    vm: CatsViewModel = hiltViewModel()
) {
    val catPicsList by vm.catPics.collectAsState()

    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(catPicsList, key = { item ->  item.id }){ item ->
                ListItem(catPic = item) {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ListItem(
    catPic: CatPicItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(286.dp)
            .padding(8.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current)
                    .data(catPic.pictureUrl())
                    .crossfade(true)
                    .build(),
                contentDescription = catPic.tags.joinToString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize())

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)

            ) {
                catPic.tags.forEach {
                    Tag(text = it)
                }
            }
        }
    }
}

@Composable
fun Tag(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

