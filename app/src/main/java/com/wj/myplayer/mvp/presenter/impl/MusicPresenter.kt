package com.wj.myplayer.mvp.presenter.impl

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hjl.commonlib.base.BaseApplication
import com.hjl.commonlib.network.HttpHandler
import com.hjl.commonlib.utils.LogUtils
import com.hjl.module_main.daodb.MediaDaoManager
import com.hjl.module_main.daodb.MediaEntity
import com.hjl.module_main.net.DoubanNetworkWrapper
import com.hjl.module_main.net.bean.douban.MusicSearchBean
import com.hjl.module_main.utils.MediaUtils
import com.wj.myplayer.mvp.presenter.IMusicPresenter

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
                val bitmap = MediaUtils.getArtwork(BaseApplication.getApplication().applicationContext,
                        entity.getId()!!, entity.getAlbum_id(), true, true)
                Glide.with(context).load(bitmap).into(imageView)
            }
        })
    }


}