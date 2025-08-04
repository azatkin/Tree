package com.example.tree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.example.tree.base.compose.AppTheme
import com.example.tree.di.LocalAppComponent
import com.example.tree.di.mainComponent
import com.example.tree.presentation.ui.TreeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalAppComponent provides mainComponent()) {
                AppTheme {
                    TreeScreen()
                }
            }
        }
    }
}

