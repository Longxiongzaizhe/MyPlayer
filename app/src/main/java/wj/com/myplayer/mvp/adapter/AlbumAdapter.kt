package wj.com.myplayer.mvp.adapter

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
        Glide.with(mContext).load(item!!.coverurl).error(R.drawable.icon_dog).into(imageView);
    }

}