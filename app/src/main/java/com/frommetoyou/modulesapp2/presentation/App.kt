package com.frommetoyou.modulesapp2.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App @Inject constructor() : Application()