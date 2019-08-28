package wj.com.myplayer.mvp.presenter

import android.content.Context
import android.widget.ImageView
import wj.com.myplayer.daodb.MediaEntity

interface IMusicPresenter {

    fun getMuiscUri(context: Context,entity: MediaEntity,imageView: ImageView)

}