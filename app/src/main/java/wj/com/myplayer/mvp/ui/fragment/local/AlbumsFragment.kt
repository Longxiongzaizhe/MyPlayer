package wj.com.myplayer.mvp.ui.fragment.local

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.example.commonlib.baseConfig.BaseFragment
import kotlinx.android.synthetic.main.fragment_album.*
import wj.com.myplayer.R
import wj.com.myplayer.constant.FlagConstant
import wj.com.myplayer.daodb.MediaAuthorEntity
import wj.com.myplayer.daodb.MediaAuthorManager
import wj.com.myplayer.mvp.ui.activity.MainMusic.MusicService
import wj.com.myplayer.mvp.adapter.AlbumAdapter
import wj.com.myplayer.utils.MediaUtils

class AlbumsFragment : BaseFragment() {

    var data : List<MediaAuthorEntity> = MediaAuthorManager.getInstance().all
    var adapter = AlbumAdapter(data)


    companion object instance{
        fun getInstance(bundle: Bundle):AlbumsFragment{
            val albumsFragment = AlbumsFragment()
            albumsFragment.arguments = bundle
            return albumsFragment
        }

        fun getInstance(mBinder:MusicService.MusicBinder):AlbumsFragment{
            val bundle = Bundle()
            bundle.putSerializable(FlagConstant.BINDER,mBinder)
            return getInstance(bundle)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_album
    }

    override fun initView(view: View?) {
        mMultipleStatusView.showLoading()
        album_rv.layoutManager = GridLayoutManager(context,3)
        album_rv.adapter = adapter
        adapter.setEmptyView(R.layout.layout_no_content,album_rv)
        MediaUtils.initAlbumCover()

        album_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(context!!).resumeRequests()
                }else{
                    Glide.with(context!!).pauseAllRequests()
                }

            }
        })
    }

    override fun initData() {


        mMultipleStatusView.showContent()
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
    }
}