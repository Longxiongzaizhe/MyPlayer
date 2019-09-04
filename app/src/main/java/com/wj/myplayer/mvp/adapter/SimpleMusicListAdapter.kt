package com.wj.myplayer.mvp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wj.myplayer.R
import com.hjl.module_main.daodb.MediaListEntity

class SimpleMusicListAdapter(data: List<MediaListEntity>?) : BaseQuickAdapter<MediaListEntity, BaseViewHolder>(R.layout.item_list_add,data) {


    init {

    }

    override fun convert(helper: BaseViewHolder?, item: MediaListEntity?) {
        helper?.setText(R.id.list_name_tv, item?.name)
    }

}


