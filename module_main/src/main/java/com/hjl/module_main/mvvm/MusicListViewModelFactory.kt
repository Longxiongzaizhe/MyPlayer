package com.hjl.module_main.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 *
 * created by long on 2019/9/27
 */

class MusicListViewModelFactory :  ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MusicListViewModel() as T
    }
}