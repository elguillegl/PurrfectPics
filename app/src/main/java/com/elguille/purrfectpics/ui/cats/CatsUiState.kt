package com.elguille.purrfectpics.ui.cats

import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.domain.DataError
import com.elguille.purrfectpics.domain.Resource

data class CatsUiState(
    val catsResource: Resource<List<CatPic>, DataError> = Resource.Empty(),
    val selectedCatResource: Resource<CatPic, DataError> = Resource.Empty()
)