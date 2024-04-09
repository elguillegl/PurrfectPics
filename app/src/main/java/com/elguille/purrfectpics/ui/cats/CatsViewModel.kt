package com.elguille.purrfectpics.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elguille.purrfectpics.data.model.CatPicItem
import com.elguille.purrfectpics.data.repository.CatPicRepository
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
    private val _catPics = MutableStateFlow<List<CatPicItem>>(emptyList())
    val catPics = _catPics.asStateFlow()

    init {
        viewModelScope.launch {
            _catPics.update {
                repository.getCatPics()
            }
        }
    }
}