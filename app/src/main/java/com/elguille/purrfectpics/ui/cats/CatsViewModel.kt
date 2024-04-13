package com.elguille.purrfectpics.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elguille.purrfectpics.data.model.CatPic
import com.elguille.purrfectpics.data.repository.CatPicRepository
import com.elguille.purrfectpics.domain.DataError
import com.elguille.purrfectpics.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val repository: CatPicRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CatsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCats()
    }

    fun loadCats() {
        _uiState.update { it.copy(catsResource = Resource.Loading()) }

        viewModelScope.launch {
            _uiState.update {
                it.copy(catsResource = repository.getCatPics())
            }
        }
    }

    fun loadSelectedCatPic(catPic: CatPic) {
        _uiState.update { it.copy(selectedCatPic = catPic, selectedCatResource = Resource.Loading()) }
        viewModelScope.launch {
            _uiState.update {
                it.copy(selectedCatResource = repository.getCatPic(catPic.id))
            }
        }
    }
}