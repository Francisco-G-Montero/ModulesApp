package com.frommetoyou.modulesapp2.domain.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageFlowUtil @Inject constructor() {
    private val _imagePathList = MutableLiveData<List<String>>()
    val imagePathList: LiveData<List<String>>
        get() = _imagePathList
    private val leafList = mutableListOf<String>()

    fun appendImgPath(imgPath: String) {
        leafList.add(imgPath)
        _imagePathList.postValue(leafList)
    }

    fun getImgPathList() = imagePathList
}
