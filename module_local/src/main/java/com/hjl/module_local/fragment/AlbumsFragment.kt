package com.hjl.module_local.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_local.R
import com.hjl.module_local.adapter.AlbumAdapter
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.constant.MediaConstant
import com.hjl.module_main.customview.WrapContentGridLayoutManager
import com.hjl.module_main.daodb.MediaAlbumsEntity
import com.hjl.module_main.daodb.MediaAlbumsManager
import com.hjl.module_main.router.RApp
import com.hjl.module_main.router.RLocal
import com.hjl.module_main.router.RMain
import com.hjl.module_main.service.MusicService
import com.hjl.module_main.utils.MediaUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.local_fragment_album.*


@Route(path = RLocal.ALBUMS_FRAGMENT)
class AlbumsFragment : BaseFragment(), BaseQuickAdapter.OnItemClickListener {


    var datalist : ArrayList<MediaAlbumsEntity> = ArrayList()
    var adapter = AlbumAdapter(datalist)


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
        with(adapter){
            setEmptyView(R.layout.layout_no_content,album_rv)
            onItemClickListener = this@AlbumsFragment
        }

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

    override fun notifyDataChange() {
        Observable.create(ObservableOnSubscribe<String> { e ->
            MediaUtils.initAlbumCover()
            synchronized(datalist){
                datalist.clear()
                datalist.addAll(MediaAlbumsManager.getInstance().loadAll())
            }
            e.onNext(FlagConstant.RXJAVA_KEY_01)
            e.onComplete()
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                addDisposable(d)
            }

            override fun onNext(value: String) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                mMultipleStatusView?.showContent()
                adapter.notifyDataSetChanged()
            }

        })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        ARouter.getInstance().build(RMain.RMusicList)
                .withInt(FlagConstant.INTENT_KEY01, MediaConstant.LIST_ALBUMS)
                .withLong(FlagConstant.INTENT_KEY02,datalist[position].id)
                .navigation()
    }

    override fun initData() {
        var disposable : Disposable? = null

        Observable.create(ObservableOnSubscribe<String> { e ->
            MediaUtils.initAlbumCover()
            synchronized(datalist){
                datalist.clear()
                datalist.addAll(MediaAlbumsManager.getInstance().loadAll())
            }
            e.onNext(FlagConstant.RXJAVA_KEY_01)
            e.onComplete()
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(value: String) {

            }

            override fun onError(e: Throwable) {

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