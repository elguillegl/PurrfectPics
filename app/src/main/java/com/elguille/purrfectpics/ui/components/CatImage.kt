package com.elguille.purrfectpics.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.domain.DataError
import com.elguille.purrfectpics.domain.Resource
import com.elguille.purrfectpics.extensions.asTextResource
import com.elguille.purrfectpics.extensions.toDataError
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun CatImage(
    imageUrl: String,
    width: Int,
    height: Int,
    modifier: Modifier = Modifier,
    scale: ContentScale = ContentScale.Inside,
) {
    var imageResource: Resource<Drawable, DataError> by remember {
        mutableStateOf(
            Resource.Loading()
        )
    }

    val resourceValue = imageResource

    when (resourceValue) {
        is Resource.Loading, is Resource.Success -> {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                    .crossfade(true)
                    .placeholder(R.mipmap.cat_list_item_loading)
                    .build(),
                onSuccess = {
                    imageResource = Resource.Success(it.result.drawable)
                },
                onError = {
                    imageResource = Resource.Error(it.result.toDataError())
                }
            )

            val imageModifier = if (imageResource is Resource.Success) {
                Modifier
                    .zoomable(rememberZoomState())
            } else {
                Modifier
            }

            val contentScale = if (width > height) {
                ContentScale.FillWidth
            } else {
                ContentScale.FillHeight
            }

            Surface(
                color = Color.Black,
                modifier = modifier
                    .height(IntrinsicSize.Max)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Cute Cat Image",
                    contentScale = contentScale,
                    modifier = imageModifier
                )
            }
        }
        is Resource.Error -> {
            CuteCatsLoadingError(
                text = resourceValue.error.asTextResource().asString(),
                scale = scale,
                modifier = modifier.fillMaxSize()
            ) {
                imageResource = Resource.Loading()
            }
        }
        else -> {}
    }
}