package com.elguille.purrfectpics.ui.cats

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.domain.Resource
import com.elguille.purrfectpics.ui.cats.destinations.CatPicScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun CatsScreen(
    vm: CatsViewModel,
    navigator: DestinationsNavigator
) {
    val configuration = LocalConfiguration.current
    val uiState by vm.uiState.collectAsState()

    when (val catPicsResource = uiState.catsResource) {
        is Resource.Empty -> {

        }
        is Resource.Loading -> {
            CuteCatsLoading(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        is Resource.Success -> {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    CuteCatsHorizontalList(
                        catPicsList = catPicsResource.data,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        vm.selectCatPic(it)
                        navigator.navigate(CatPicScreenDestination())
                    }
                }
                Configuration.ORIENTATION_SQUARE,
                Configuration.ORIENTATION_UNDEFINED,
                Configuration.ORIENTATION_PORTRAIT -> {
                    CuteCatsVerticalList(
                        catPicsList = catPicsResource.data,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        vm.selectCatPic(it)
                        navigator.navigate(CatPicScreenDestination())
                    }
                }
            }
        }
        is Resource.Error -> {

        }
    }
}

@Composable
fun CuteCatsLoading(modifier: Modifier = Modifier)  {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.mipmap.cat_list_loading),
            contentDescription = stringResource(R.string.loading_cute_cats)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.loading_cute_cats),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
        )
    }
}

@Composable
fun CuteCatsVerticalList(
    modifier: Modifier = Modifier,
    catPicsList: List<CatPic>,
    onCatPicClick: (CatPic) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(catPicsList, key = { item ->  item.id }){ item ->
            CatPicItem(
                catPic = item,
                modifier = Modifier
                    .height(286.dp),
            ){
                onCatPicClick(item)
            }
        }
    }
}

@Composable
fun CuteCatsHorizontalList(
    modifier: Modifier = Modifier,
    catPicsList: List<CatPic>,
    onCatPicClick: (CatPic) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(catPicsList, key = { item ->  item.id }){ item ->
            CatPicItem(
                catPic = item,
                modifier = Modifier
                    .width(286.dp),
            ){
                onCatPicClick(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatPicItem(
    catPic: CatPic,
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
                    .data(catPic.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Cat pic ${catPic.id}",
                contentScale = scale,
                placeholder = painterResource(R.mipmap.cat_list_item_loading),
                error = painterResource(R.mipmap.cat_list_item_loading_error),
                modifier = Modifier
                    .fillMaxSize())
        }
    }
}