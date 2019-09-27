package com.hjl.module_main.mvp.presenter.impl

import com.hjl.commonlib.base.mvp.BaseMvpPresenter
import com.hjl.commonlib.utils.RxSchedulers
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.daodb.MediaDaoManager
import com.hjl.module_main.daodb.MediaListManager
import com.hjl.module_main.daodb.MediaRelManager
import com.hjl.module_main.mvp.contract.MainContract
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

/**
 *
 * created by long on 2019/9/25
 */
class MainPresenter : BaseMvpPresenter<MainContract.IMainView>(),MainContract.IMainPresenter {
    override fun start() {

    }


    private val manager = MediaDaoManager.getInstance()
    private val relManager = MediaRelManager.getInstance()
    private val listManager = MediaListManager.getInstance()

    /*
    * val localNum: Int = manager.loadAll().size
            val historyNum: Int = relManager.queryRecentList().size
            val favouriteNum: Int = relManager.queryFavoriteList().size
            val map = HashMap<String, Int>()
            map[FlagConstant.RXJAVA_KEY_01] = localNum
            map[FlagConstant.RXJAVA_KEY_02] = historyNum
            map[FlagConstant.RXJAVA_KEY_03] = favouriteNum



            e.onNext(map)
            e.onComplete()
    *
    * */

    override fun updateData() {
        addDisposable(Observable.create(ObservableOnSubscribe<Map<String,Int>> {
            val localNum: Int = manager.loadAll().size
            val historyNum: Int = relManager.queryRecentList().size
            val favouriteNum: Int = relManager.queryFavoriteList().size
            val map = HashMap<String, Int>()
            map[FlagConstant.RXJAVA_KEY_01] = localNum
            map[FlagConstant.RXJAVA_KEY_02] = historyNum
            map[FlagConstant.RXJAVA_KEY_03] = favouriteNum

            it.onNext(map)

        }).compose(RxSchedulers.io_main()).subscribe{
            view.updateDataFinish(
                    it[FlagConstant.RXJAVA_KEY_01] ?: error(""),
                    it[FlagConstant.RXJAVA_KEY_02] ?: error(""),
                    it[FlagConstant.RXJAVA_KEY_03] ?: error(""))
        })

    }
}