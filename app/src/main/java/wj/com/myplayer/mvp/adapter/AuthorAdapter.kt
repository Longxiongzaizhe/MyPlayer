package wj.com.myplayer.mvp.adapter


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.commonlib.network.HttpHandler
import com.example.commonlib.utils.StringUtils
import wj.com.myplayer.R
import wj.com.myplayer.daodb.MediaAuthorEntity
import wj.com.myplayer.daodb.MediaAuthorManager
import wj.com.myplayer.daodb.MediaDaoManager
import wj.com.myplayer.net.NetworkWrapper
import wj.com.myplayer.net.bean.SearchPicBean

class AuthorAdapter(datalist : List<MediaAuthorEntity>) : BaseQuickAdapter<MediaAuthorEntity, BaseViewHolder>(R.layout.item_author,datalist) {

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