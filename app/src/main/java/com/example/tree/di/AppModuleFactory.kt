package com.example.tree.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tree.presentation.viewmodel.TreeViewModel
import javax.inject.Inject
import javax.inject.Provider

class AppModuleFactory @Inject constructor(
    treeViewModel: Provider<TreeViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        TreeViewModel::class.java to treeViewModel,
    )

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return providers[modelClass]?.get() as T
    }
}