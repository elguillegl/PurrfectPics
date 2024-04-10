package com.elguille.purrfectpics.ui.cats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.ui.cats.destinations.CatPicScreenDestination
import com.elguille.purrfectpics.ui.components.CatPic
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
    val catPicsList by vm.catPics.collectAsState()
    
    if (catPicsList.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
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

        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(catPicsList, key = { item ->  item.id }){ item ->
            CatPic(
                catPic = item,
                modifier = Modifier
                    .height(286.dp),
            ){
                vm.selectCatPic(item)
                navigator.navigate(CatPicScreenDestination())
            }
        }
    }
}

