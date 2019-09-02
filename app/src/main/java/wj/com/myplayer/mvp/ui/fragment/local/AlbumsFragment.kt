package wj.com.myplayer.mvp.ui.fragment.local

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.commonlib.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_album.*
import wj.com.myplayer.R
import wj.com.myplayer.constant.FlagConstant
import wj.com.myplayer.daodb.MediaAlbumsEntity
import wj.com.myplayer.daodb.MediaAlbumsManager
import wj.com.myplayer.mview.WrapContentGridLayoutManager
import wj.com.myplayer.mvp.adapter.AlbumAdapter
import wj.com.myplayer.mvp.ui.activity.MainMusic.MusicService
import wj.com.myplayer.utils.MediaUtils

class AlbumsFragment : BaseFragment() {

    var data : ArrayList<MediaAlbumsEntity> = ArrayList()
    var adapter = AlbumAdapter(data)


    companion object{
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
        album_rv.layoutManager = WrapContentGridLayoutManager(context,3)
        album_rv.adapter = adapter
        adapter.setEmptyView(R.layout.layout_no_content,album_rv)

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
        var disposable : Disposable? = null

        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(e: ObservableEmitter<String>?) {
                MediaUtils.initAlbumCover()
                synchronized(data){
                    data.clear()
                    data.addAll(MediaAlbumsManager.getInstance().allAlbums)
                }
                e!!.onNext(FlagConstant.RXJAVA_KEY_01)
                e.onComplete()
            }

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                disposable = d
            }

            override fun onNext(value: String?) {

            }

            override fun onError(e: Throwable?) {

            }

            override fun onComplete() {
                mMultipleStatusView.showContent()
                adapter.notifyDataSetChanged()
                if (!disposable!!.isDisposed){
                    disposable?.dispose()
                }
            }

        })

    }

    override fun onResume() {
        super.onResume()
        Log.d("AlbumsFragment","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("AlbumsFragment","onPause")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("AlbumsFragment","onPause")
    }

}