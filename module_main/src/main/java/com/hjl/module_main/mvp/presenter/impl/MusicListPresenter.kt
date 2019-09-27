package com.hjl.module_main.mvp.presenter.impl

import com.hjl.commonlib.base.mvp.BaseMvpPresenter
import com.hjl.commonlib.utils.RxSchedulers
import com.hjl.module_main.daodb.*
import com.hjl.module_main.mvp.contract.MusicListContract
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Consumer

/**
 *
 * created by long on 2019/9/27
 */
class MusicListPresenter : MusicListContract.IMusicListPresenter,BaseMvpPresenter<MusicListContract.IMusicListView>() {


    val relManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_REL) as MediaRelManager
    val manager = DaoManagerFactory.getInstance().manager

    override fun getCustomList(id: Long) {
        if (id < 0) return

        addDisposable(Observable.create<ArrayList<MediaEntity>> {
            val list: ArrayList<MediaEntity> = ArrayList()
            val relList = MediaRelManager.getInstance().query(MediaRelEntityDao.Properties.MediaListId.eq(id)) as ArrayList<MediaRelEntity>
            for (relEntity in relList) {
                list.add(manager.query(relEntity.songId))
            }
            it.onNext(list)
        }.compose(RxSchedulers.io_main()).subscribe{
            view.onGetCustomListComplete(it)
        })

    }

    override fun getAuthorList(name: String?) {
        if (name.isNullOrEmpty()) return

        addDisposable(Observable.create<ArrayList<MediaEntity>> {
            val list = manager.queryByAuthor(name) as ArrayList<MediaEntity>
            it.onNext(list)
        }.compose(RxSchedulers.io_main()).subscribe{
            view.onGetAuthorListComplete(it)
        })


    }

    override fun getAlbumsList(id: Long) {
        if (id < 0) return

        addDisposable(Observable.create<ArrayList<MediaEntity>> {
            val list = manager.queryByAlbumId(id)
            it.onNext(list as ArrayList<MediaEntity>)
        }.compose(RxSchedulers.io_main()).subscribe{
            view.onGetAuthorListComplete(it)
        })
    }

    override fun start() {

    }
}