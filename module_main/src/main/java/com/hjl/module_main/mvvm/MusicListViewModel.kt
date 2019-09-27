package com.hjl.module_main.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hjl.module_main.daodb.MediaEntity

/**
 *
 * created by long on 2019/9/27
 */
class MusicListViewModel(

) : ViewModel() {

    val datalist : LiveData<ArrayList<MediaEntity>> = MutableLiveData()

    fun getCustomList(listId : Long){

    }

}