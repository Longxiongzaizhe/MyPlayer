package com.hjl.module_net.mvp.impl

import com.hjl.commonlib.base.mvp.BaseMvpPresenter
import com.hjl.module_net.mvp.contract.NetSearchContract

/**
 *
 * created by long on 2019/10/22
 */
class SearchPresenterImpl : BaseMvpPresenter<NetSearchContract.INetSearchView>(),NetSearchContract.INetSearchPresenter{

    override fun search(keyWord: String?, pageIndex: String?, pageSize: String?) {

    }

    override fun getAssociativeWord(keyWord: String?) {

    }



    override fun start() {

    }



}