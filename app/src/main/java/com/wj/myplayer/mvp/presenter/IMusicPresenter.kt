package com.wj.myplayer.mvp.presenter

import android.content.Context
import android.widget.ImageView
import com.hjl.module_main.daodb.MediaEntity

interface IMusicPresenter {

    fun getMuiscUri(context: Context,entity: MediaEntity,imageView: ImageView)

}