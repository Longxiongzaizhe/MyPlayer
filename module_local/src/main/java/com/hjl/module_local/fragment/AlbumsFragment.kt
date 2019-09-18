package com.hjl.module_local.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_local.R
import com.hjl.module_local.adapter.AlbumAdapter
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.daodb.MediaAlbumsEntity
import com.hjl.module_main.daodb.MediaAlbumsManager
import com.hjl.module_main.mview.WrapContentGridLayoutManager
import com.hjl.module_main.mvp.fragment.MusicService
import com.hjl.module_main.utils.MediaUtils
import com.hjl.module_main.module.RApp
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.local_fragment_album.*


@Route(path = RApp.ALBUMS_FRAGMENT)
class AlbumsFragment : BaseFragment() {

    var data : ArrayList<MediaAlbumsEntity> = ArrayList()
    var adapter = AlbumAdapter(data)


    companion object{
        fun newInstance(bundle: Bundle): AlbumsFragment {
            val albumsFragment = AlbumsFragment()
            albumsFragment.arguments = bundle
            return albumsFragment
        }

        fun newInstance(mBinder: MusicService.MusicBinder): AlbumsFragment {
            val bundle = Bundle()
            bundle.putSerializable(FlagConstant.BINDER,mBinder)
            return Companion.newInstance(bundle)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.local_fragment_album
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
                    data.addAll(MediaAlbumsManager.getInstance().loadAll())
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


}