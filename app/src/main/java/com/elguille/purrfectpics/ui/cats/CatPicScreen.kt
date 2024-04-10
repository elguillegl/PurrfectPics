package com.elguille.purrfectpics.ui.cats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.extensions.pictureUrl
import com.elguille.purrfectpics.ui.components.Tags
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CatPicScreen(
    vm: CatsViewModel,
    navigator: DestinationsNavigator
) {
    val selectedCat by vm.selectedCatPic.collectAsState()

    val owner = selectedCat.owner
    val tags = selectedCat.tags
    var imageLoaded by remember { mutableStateOf(false) }

    val topBarTitle: String = if (tags.isNotEmpty()) {
        tags[tags.indices.random()].replace("_", " ").capitalize(Locale.current)
    } else {
        stringResource(R.string.awesome_cat_title)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(selectedCat.pictureUrl())
            .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
            .crossfade(true)
            .placeholder(R.mipmap.cat_list_item_loading)
            .error(R.mipmap.cat_list_item_loading_error)
            .build(),
        onSuccess = {
            imageLoaded = true
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.action_back)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val imageModifier = if (imageLoaded) {
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .zoomable(rememberZoomState())
            } else {
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            }
            Image(
                painter = painter,
                contentDescription = selectedCat.tags.joinToString(),
                contentScale = ContentScale.FillWidth,
                modifier = imageModifier
            )


            if (tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(stringResource(R.string.tags_label))

                    Spacer(modifier = Modifier.height(8.dp))

                    Tags(tags = tags)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (owner != null) {
                Text(text = stringResource(R.string.owner_label, owner))
            }
        }
    }
}