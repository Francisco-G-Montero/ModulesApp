package com.example.feature_lists.domain.repository

import android.graphics.Bitmap

interface AppResourcesRepository {
    fun getEkkoImageResource(): Bitmap?
}