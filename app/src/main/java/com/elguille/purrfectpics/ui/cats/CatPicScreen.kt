package com.elguille.purrfectpics.ui.cats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.domain.Resource
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
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (val selectedCatResource = uiState.selectedCatResource) {
                is Resource.Empty -> {

                }
                is Resource.Loading -> {
                    CuteCatLoading(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is Resource.Success -> {
                    CatDetails(
                        catPic = selectedCatResource.data,
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.TopCenter)
                    )
                }
                is Resource.Error -> {

                }
            }
        }

    }
}

@Composable
fun CuteCatLoading(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.mipmap.cat_list_item_loading),
            contentDescription = "Loading cute cat!"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Loading cute cat!",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
        )
    }
}

@Composable
fun CatDetails(
    catPic: CatPic,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CatPic(
            imageUrl = catPic.url,
            width = catPic.width,
            height = catPic.height,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CatPic(
    imageUrl: String,
    width: Int,
    height: Int,
    modifier: Modifier = Modifier,
) {
    var imageLoaded by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
            .crossfade(true)
            .placeholder(R.mipmap.cat_list_item_loading)
            .error(R.mipmap.cat_list_item_loading_error)
            .build(),
        onSuccess = {
            imageLoaded = true
        }
    )

    val imageModifier = if (imageLoaded) {
        Modifier
            .zoomable(rememberZoomState())
    } else {
        Modifier
    }

    Surface(
        color = Color.Black,
        modifier = modifier
            .height(IntrinsicSize.Max)
    ) {
        Image(
            painter = painter,
            contentDescription = "Cute Cat Image",
            contentScale = ContentScale.FillWidth,
            modifier = imageModifier
        )
    }
}