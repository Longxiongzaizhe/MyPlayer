package com.hjl.module_net.ui

import android.databinding.DataBindingUtil.setContentView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment
import com.hjl.module_net.R
import com.hjl.module_net.mvp.contract.NetSearchContract
import com.hjl.module_net.mvp.impl.SearchPresenterImpl
import com.hjl.module_net.net.SearchVo

class SearchSongActivity : BaseMvpMultipleFragment<SearchPresenterImpl>(),NetSearchContract.INetSearchView {
    override fun createPresenter(): SearchPresenterImpl {
        return SearchPresenterImpl()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_song
    }

    override fun initView(view: View?) {

    }

    override fun initData() {

    }

    override fun onSearchFail(msg: String?) {

    }

    override fun onSearchSuccess(vo: SearchVo?) {

    }

    override fun onGetAssociativeWordSuccess() {

    }

    override fun onGetAssociativeWordFail(msg: String?) {

    }


}
