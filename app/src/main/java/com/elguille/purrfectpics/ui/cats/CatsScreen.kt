package com.elguille.purrfectpics.ui.cats

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.domain.Resource
import com.elguille.purrfectpics.extensions.asTextResource
import com.elguille.purrfectpics.ui.components.CatImage
import com.elguille.purrfectpics.ui.components.CuteCatsLoading
import com.elguille.purrfectpics.ui.components.CuteCatsLoadingError
import com.elguille.purrfectpics.ui.destinations.CatPicScreenDestination
import com.elguille.purrfectpics.ui.navigation.ContentNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@ContentNavGraph(start = true)
@Destination
@Composable
fun CatsScreen(
    vm: CatsViewModel,
    navigator: DestinationsNavigator
) {
    val uiState by vm.uiState.collectAsState()
    val catPicsResource = uiState.catsResource
    val configuration = LocalConfiguration.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { vm.loadCats() }
    )

    Scaffold(
        topBar = {
            TopAppBar( title =  { Text(text = stringResource(R.string.app_name)) })
        }
    ) {
        Box(
            modifier =
            Modifier
                .padding(it)
                .pullRefresh(pullRefreshState)
        ){
            val catPicsResourceValue = uiState.catsResource
            when (catPicsResource) {
                is Resource.Empty -> {

                }
                is Resource.Loading -> {
                    CuteCatsLoading(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                is Resource.Success -> {
                    Cats(
                        catPics = catPicsResource.data,
                        configuration = configuration,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        vm.loadSelectedCatPic(it)
                        navigator.navigate(CatPicScreenDestination())
                    }
                }
                is Resource.Error -> {
                    CuteCatsLoadingError(
                        text = catPicsResource.asTextResource().asString(),
                        scale = ContentScale.Inside,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        vm.loadCats()
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = false,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun Cats(
    catPics: List<CatPic>,
    modifier: Modifier = Modifier,
    configuration: Configuration,
    onCatPicClick: (CatPic) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                CuteCatsHorizontalList(
                    catPicsList = catPics,
                    modifier = Modifier
                        .fillMaxSize(),
                    onCatPicClick = onCatPicClick
                )
            }
            Configuration.ORIENTATION_SQUARE,
            Configuration.ORIENTATION_UNDEFINED,
            Configuration.ORIENTATION_PORTRAIT -> {
                CuteCatsVerticalList(
                    catPicsList = catPics,
                    modifier = Modifier
                        .fillMaxSize(),
                    onCatPicClick = onCatPicClick
                )
            }
        }
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
                scale = ContentScale.Inside,
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
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        CatImage(
            imageUrl = catPic.url,
            width = catPic.width,
            height = catPic.height,
            scale = scale,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}