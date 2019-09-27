package com.hjl.module_main.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hjl.module_main.daodb.*

/**
 *
 * created by long on 2019/9/27
 */
class MusicListViewModel(

) : ViewModel() {

    val datalist : MutableLiveData<ArrayList<MediaEntity>> = MutableLiveData()
    val relManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_REL) as MediaRelManager
    val manager = DaoManagerFactory.getInstance().manager

    fun getCustomList(listId : Long){
        if (listId < 0) return

        val list : ArrayList<MediaEntity> = ArrayList()
        val relList = MediaRelManager.getInstance().query(MediaRelEntityDao.Properties.MediaListId.eq(listId)) as ArrayList<MediaRelEntity>
        for (relEntity in relList) {
            list.add(manager.query(relEntity.songId))
        }
        datalist.postValue(list)
    }

}