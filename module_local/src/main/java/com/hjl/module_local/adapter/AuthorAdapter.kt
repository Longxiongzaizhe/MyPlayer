package com.hjl.module_local.adapter


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjl.commonlib.network.okhttp.HttpHandler
import com.hjl.commonlib.utils.StringUtils
import com.hjl.module_local.R

import com.hjl.module_main.daodb.MediaAuthorEntity
import com.hjl.module_main.daodb.MediaAuthorManager
import com.hjl.module_main.daodb.MediaDaoManager
import com.hjl.module_main.net.NetworkWrapper
import com.hjl.module_main.net.bean.SearchPicBean

class AuthorAdapter(datalist : List<MediaAuthorEntity>) : BaseQuickAdapter<MediaAuthorEntity, BaseViewHolder>(R.layout.local_item_author,datalist) {

    override fun convert(helper: BaseViewHolder?, item: MediaAuthorEntity?) {
        if (item != null) {
            helper!!.setText(R.id.author_tv,item.name)
            helper.setText(R.id.list_count_tv,"${MediaDaoManager.getInstance().getCountByAuthor(item.name)}首")
            val imageView = helper.getView<ImageView>(R.id.list_author_iv)
            if (StringUtils.isEmpty(item.coverUrl)){
                NetworkWrapper.searchPic(item.name,object : HttpHandler<SearchPicBean>() {
                    override fun onSuccess(data: SearchPicBean?) {
                        if (data?.list == null || data.list.size == 0) return
                        var url : String = data!!.list[0]._thumb
                        Glide.with(mContext).load(url).into(imageView)
                        item.coverUrl = url
                        MediaAuthorManager.get().update(item)
                    }

                })
            }else{
                Glide.with(mContext).load(item.coverUrl).into(imageView)
            }

        }
    }

}