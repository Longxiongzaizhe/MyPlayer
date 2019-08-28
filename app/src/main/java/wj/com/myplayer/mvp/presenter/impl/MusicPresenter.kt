package wj.com.myplayer.mvp.presenter.impl

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.commonlib.network.HttpHandler
import wj.com.myplayer.config.MainApplication
import wj.com.myplayer.daodb.MediaDaoManager
import wj.com.myplayer.daodb.MediaEntity
import wj.com.myplayer.mvp.presenter.IMusicPresenter
import wj.com.myplayer.net.DoubanNetworkWrapper
import wj.com.myplayer.net.bean.douban.MusicSearchBean
import wj.com.myplayer.utils.LogUtils
import wj.com.myplayer.utils.MediaUtils

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