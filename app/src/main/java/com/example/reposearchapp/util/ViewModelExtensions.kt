package com.example.reposearchapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <VM : ViewModel> createFactory(crossinline createViewModel: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(clazz: Class<T>): T = createViewModel() as T
    }