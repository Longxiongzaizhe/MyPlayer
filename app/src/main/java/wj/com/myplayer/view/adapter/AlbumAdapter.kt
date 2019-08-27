package wj.com.myplayer.view.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wj.com.myplayer.R
import wj.com.myplayer.config.MainApplication
import wj.com.myplayer.constant.FlagConstant
import wj.com.myplayer.daodb.MediaAuthorEntity
import wj.com.myplayer.daodb.MediaDaoManager
import wj.com.myplayer.utils.MediaUtils

class AlbumAdapter(data :List<MediaAuthorEntity>): BaseQuickAdapter<MediaAuthorEntity, BaseViewHolder>(R.layout.item_album,data) {

    override fun convert(helper: BaseViewHolder?, item: MediaAuthorEntity?) {
        helper!!.setText(R.id.album_tv,item?.author)

        var imageView = helper.getView<ImageView>(R.id.album_iv)
//
//        if(imageView.tag != FlagConstant.TAG_KEY){
//            Glide.with(mContext).load(item!!.cover).into(helper.getView(R.id.album_iv) as ImageView)
//            imageView.tag = FlagConstant.TAG_KEY
//        }
        val album = MediaDaoManager.getInstance().getAlbumByAlbumId(item!!.id)
        if(imageView.tag != FlagConstant.TAG_KEY){
            var bitmap = MediaUtils.getArtwork(MainApplication.get().applicationContext.contentResolver, album.toInt(),
                    item?.id!!.toInt(), true, false)
            if (bitmap != null){
                Glide.with(mContext).load(bitmap).into(helper.getView(R.id.album_iv) as ImageView)
                imageView.tag = FlagConstant.TAG_KEY
            }
        }

//        if(imageView.tag != FlagConstant.TAG_KEY){
//            var bitmap = MediaUtils.getArtwork(MainApplication.get().applicationContext.contentResolver, Integer.valueOf(item?.id.toString()),
//                    item?.id!!.toInt(), true, false)
//            if (bitmap != null){
//                Glide.with(mContext).load(bitmap).into(helper.getView(R.id.album_iv) as ImageView)
//                imageView.tag = FlagConstant.TAG_KEY
//            }
//        }

//        var bitmap = MediaUtils.getArtwork(MainApplication.get().applicationContext.contentResolver, Integer.valueOf(item?.id.toString()),
//                item?.id!!.toInt(), true, false)
//        if (bitmap != null){
//            Glide.with(mContext).load(bitmap).into(helper.getView(R.id.album_iv) as ImageView)
//        }



    }

}