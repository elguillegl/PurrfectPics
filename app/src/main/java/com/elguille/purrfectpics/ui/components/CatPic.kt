package com.elguille.purrfectpics.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.extensions.pictureUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatPic(
    catPic: CatPicItem,
    modifier: Modifier = Modifier,
    scale: ContentScale = ContentScale.Crop,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
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
                contentScale = scale,
                placeholder = painterResource(R.mipmap.cat_list_item_loading),
                error = painterResource(R.mipmap.cat_list_item_loading_error),
                modifier = Modifier
                    .fillMaxSize())

            Tags(
                tags = catPic.tags, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
            )
        }
    }
}