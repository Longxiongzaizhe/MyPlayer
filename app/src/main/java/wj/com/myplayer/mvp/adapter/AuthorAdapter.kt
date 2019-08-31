package wj.com.myplayer.mvp.adapter


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.commonlib.network.HttpHandler
import com.example.commonlib.utils.StringUtils
import wj.com.myplayer.R
import wj.com.myplayer.daodb.MediaAlbumsEntity
import wj.com.myplayer.daodb.MediaDaoManager
import wj.com.myplayer.net.NetworkWrapper
import wj.com.myplayer.net.bean.SearchPicBean
import wj.com.myplayer.utils.MediaUtils

class AuthorAdapter(datalist : List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_author,datalist) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        if (item != null) {
            helper!!.setText(R.id.author_tv,item)
            helper.setText(R.id.list_count_tv,"${MediaDaoManager.getInstance().getCountByAuthor(item)}首")
            var imageView = helper.getView<ImageView>(R.id.list_author_iv)
            NetworkWrapper.searchPic(item,object : HttpHandler<SearchPicBean>() {
                override fun onSuccess(data: SearchPicBean?) {
                    if (data?.list == null || data.list.size == 0) return
                    Glide.with(mContext).load(data!!.list[0]._thumb).into(imageView)
                }

            })
        }
    }

}