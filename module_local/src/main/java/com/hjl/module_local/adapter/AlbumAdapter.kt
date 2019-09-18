package com.hjl.module_local.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjl.module_local.R
import com.hjl.module_main.daodb.MediaAlbumsEntity

class AlbumAdapter(data :List<MediaAlbumsEntity>): BaseQuickAdapter<MediaAlbumsEntity, BaseViewHolder>(R.layout.local_item_album,data) {

    override fun convert(helper: BaseViewHolder?, item: MediaAlbumsEntity?) {
        helper!!.setText(R.id.album_tv,item?.author)

        var imageView = helper.getView<ImageView>(R.id.album_iv)
        Glide.with(mContext).load(item!!.coverUrl).error(R.drawable.icon_dog).into(imageView);
    }

}