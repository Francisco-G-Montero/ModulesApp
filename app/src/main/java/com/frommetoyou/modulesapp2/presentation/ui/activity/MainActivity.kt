package com.frommetoyou.modulesapp2.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frommetoyou.modulesapp2.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class MainActivity @Inject constructor(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}