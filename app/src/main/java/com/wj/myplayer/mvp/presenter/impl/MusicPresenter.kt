package com.wj.myplayer.mvp.presenter.impl

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hjl.commonlib.network.HttpHandler
import com.wj.myplayer.config.MainApplication
import com.wj.myplayer.daodb.MediaDaoManager
import com.wj.myplayer.daodb.MediaEntity
import com.wj.myplayer.mvp.presenter.IMusicPresenter
import com.wj.myplayer.net.DoubanNetworkWrapper
import com.wj.myplayer.net.bean.douban.MusicSearchBean
import com.wj.myplayer.utils.LogUtils
import com.wj.myplayer.utils.MediaUtils

class MusicPresenter : IMusicPresenter {

    val manager = MediaDaoManager.getInstance()!!


    override fun getMuiscUri(context: Context, entity: MediaEntity, imageView: ImageView) {
        DoubanNetworkWrapper.searchMusic(entity.getTitle(), "", "", "10", object : HttpHandler<MusicSearchBean>() {
            override fun onSuccess(data: MusicSearchBean) {
                if (data.musics.size == 0) return
                val url = data.musics[0].image
                LogUtils.i("searchMusic", "title is " + entity.getTitle() + " url is: " + url)
                Glide.with(context).load(data.musics[0].image).into(imageView)
                entity.setCoverUrl(url)
                manager.update(entity)
            }

            override fun onFailure(message: String, response: String) {
                val bitmap = MediaUtils.getArtwork(MainApplication.get().applicationContext,
                        entity.getId()!!, entity.getAlbum_id(), true, true)
                Glide.with(context).load(bitmap).into(imageView)
            }
        })
    }


}