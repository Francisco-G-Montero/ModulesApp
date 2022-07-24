package com.example.feature_lists.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_lists.domain.usecases.GetAppImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultipleListViewModel @Inject constructor(
    val getAppImageUseCase: GetAppImageUseCase
) : ViewModel() {
    private val _appImage = MutableLiveData<Bitmap>()
    val appImage: LiveData<Bitmap>
        get() = _appImage

    fun getAppImage() = viewModelScope.launch {
        getAppImageUseCase()?.let { bitmap ->
            _appImage.value = bitmap
        }
    }
}