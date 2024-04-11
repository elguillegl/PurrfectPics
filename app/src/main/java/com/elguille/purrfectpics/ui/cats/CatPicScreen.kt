package com.elguille.purrfectpics.ui.cats

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.data.model.Breed
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
                title = { Text(text = stringResource(R.string.catdex_title)) },
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
                is Resource.Empty, is Resource.Loading -> {
                    CuteCatLoading(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is Resource.Success -> {
                    CatDetails(
                        catPic = selectedCatResource.data,
                        modifier = Modifier
                            .fillMaxWidth()
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
            contentDescription = stringResource(R.string.loading_cute_cat)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.loading_cute_cat),
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
        modifier = modifier.
        verticalScroll(rememberScrollState())
    ) {
        CatPic(
            imageUrl = catPic.url,
            width = catPic.width,
            height = catPic.height,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        CatBreedsInfo(
            breeds = catPic.breeds,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
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

@Composable
fun CatBreedsInfo(
    breeds: Array<Breed>,
    modifier: Modifier = Modifier
) {
    var expandedBreed by remember { mutableStateOf(
        if (breeds.size == 1) {
            breeds[0]
        } else {
            null
        }
    ) }

    breeds.forEach {
        BreedCard(
            expanded = expandedBreed == it,
            breed = it,
            modifier = modifier
        ) {
            expandedBreed = if (expandedBreed == it) {
                null
            } else {
                it
            }
        }
    }
}

@Composable
fun BreedCard(
    expanded: Boolean,
    breed: Breed,
    modifier: Modifier = Modifier,
    onExpandToggle: (Breed) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        // Expand toggle row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onExpandToggle(breed)
                }
        ) {
            val icon = if (expanded) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            }

            // Breed name
            Text(
                text = breed.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Expand toggle icon
            IconButton(onClick = {
                onExpandToggle(breed)
            }) {
                Icon(imageVector = icon, contentDescription = "Expand breed")
            }
        }

        // Breed info
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                val descriptionAnnotatedString = buildAnnotatedString {
                    append(breed.description)

                    if (breed.wikipediaUrl != null) {
                        append(" ")
                        pushStringAnnotation(
                            tag = "wikipedia",
                            annotation = breed.wikipediaUrl
                        )
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(stringResource(R.string.wikipedia_hyperlink_text))
                        }
                    }

                    pop()
                }

                // Description
                ClickableText(text = descriptionAnnotatedString) {offset ->
                    descriptionAnnotatedString.getStringAnnotations(tag = "wikipedia", start = offset, end = offset).firstOrNull()?.let {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(it.item))
                        context.startActivity(browserIntent)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // All of these traits could be simplified in an elegant way, for a productionized version of the app
                CatTextTrait(
                    trait = stringResource(R.string.origin_trait_label),
                    value = breed.origin
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatTextTrait(
                    trait = stringResource(R.string.temperament_trait_label),
                    value = breed.temperament
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatTextTrait(
                    trait = stringResource(R.string.life_span_trait_label),
                    value = breed.lifeSpan
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatTextTrait(
                    trait = stringResource(R.string.weight_trait_label),
                    value = breed.weight.metric
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.hypoallergenic_trait_label),
                    value = breed.hypoallergenic
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.shedding_level_trait_label),
                    value = breed.sheddingLevel
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.adaptability_level_trait_label),
                    value = breed.adaptability
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.social_needs_level_trait_label),
                    value = breed.socialNeeds
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.affection_level_trait_label),
                    value = breed.affectionLevel
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.child_friendly_level_trait_level),
                    value = breed.childFriendly
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.dog_friendly_level_trait_label),
                    value = breed.dogFriendly
                )

                Spacer(modifier = Modifier.height(8.dp))

                CatLevelTrait(
                    trait = stringResource(R.string.energy_level_trait_label),
                    value = breed.energyLevel
                )
            }
        }
    }
}

@Composable
fun CatTextTrait(
    trait: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    if (value == null)
        return
    Column(
        modifier = modifier
    ) {
        Text(text = trait, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = value)
    }
}

@Composable
fun CatLevelTrait(
    trait: String,
    value: Int?,
    modifier: Modifier = Modifier
) {
    if (value == null)
        return

    Column(
        modifier = modifier
    ) {
        Text(text = trait, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            (1..5).forEach {
                val iconTint = if (it <= value) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Gray
                }

                Icon(
                    painter = painterResource(id = R.drawable.cat_paw_black_24dp),
                    contentDescription = "$it",
                    tint = iconTint
                )
            }
        }
    }
}