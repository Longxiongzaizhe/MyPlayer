package com.hjl.module_net.ui

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hjl.commonlib.base.mvp.BaseMvpMultipleActivity
import com.hjl.commonlib.utils.RxSchedulers
import com.jakewharton.rxbinding3.widget.*
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.ui.activity.KugouWebActivity
import com.hjl.module_net.R
import com.hjl.module_net.mvp.contract.NetSearchContract
import com.hjl.module_net.mvp.presenter.SearchPresenterImpl
import com.hjl.module_net.net.vo.AssociativeWordVo
import com.hjl.module_net.net.vo.HotSearchVo
import com.hjl.module_net.net.vo.SearchVo
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search_song.*
import java.util.concurrent.TimeUnit

class SearchSongActivity : BaseMvpMultipleActivity<SearchPresenterImpl>(),NetSearchContract.INetSearchView {

    private val tipAdapter = SearchTipAdapter()
    private var isGetHotSuccess = false

    override fun initTitle() {
        mTitleCenterTv.text = "搜索歌曲"
    }


    override fun createPresenter(): SearchPresenterImpl {
        return SearchPresenterImpl()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_song
    }

    override fun initView() {
        search_cancel_tv.setOnClickListener { finish() }
        search_tip_rv.adapter = tipAdapter
        search_tip_rv.layoutManager = LinearLayoutManager(this)
        tipAdapter.setOnItemClickListener { adapter, view, position ->

            val intent = Intent()
            val data = tipAdapter.data[position].keyword
            intent.putExtra(FlagConstant.INTENT_KEY01,data)
            setResult(Activity.RESULT_OK,intent)
            finish()

        }

        addDisposable(net_search_et.textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .map{ it.toString() }
                .compose(RxSchedulers.io_main())
                .subscribe {
                    if (it.isNullOrEmpty() && isGetHotSuccess){
                        search_flowlayout.visibility = View.VISIBLE
                        tipAdapter.setNewData(emptyList())
                    }else{
                        search_flowlayout.visibility = View.GONE
                    }
                    tipAdapter.setKeyword(it)
                    mPresenter.getAssociativeWord(it) })

    }

    override fun initData() {
        mPresenter.getHotSearch()
    }

    override fun onSearchFail(msg: String?) {

    }

    override fun onSearchSuccess(vo: SearchVo?) {

    }

    override fun onGetAssociativeWordSuccess(vo: AssociativeWordVo?) {
        tipAdapter.setNewData(vo?.data)
    }

    override fun onGetAssociativeWordFail(msg: String?) {

    }
    override fun getHotSearchSuccess(hotSearchVo: HotSearchVo?) {
        val tagAdapter = object :TagAdapter<HotSearchVo.DataBean.InfoBean>(hotSearchVo?.data?.info){
            override fun getView(parent: FlowLayout?, position: Int, item: HotSearchVo.DataBean.InfoBean?): View {
                val inflater = LayoutInflater.from(this@SearchSongActivity)
                val tv = inflater.inflate(R.layout.item_flow_layout,parent,false) as TextView
                tv.text = item?.keyword ?: ""
                return tv
            }
        }

        search_flowlayout.adapter = tagAdapter
        search_flowlayout.setOnTagClickListener { view, position, parent ->

            val intent = Intent(this,KugouWebActivity::class.java)
            intent.putExtra(FlagConstant.INTENT_KEY01, hotSearchVo!!.data.info[position].jumpurl)
            intent.putExtra(FlagConstant.INTENT_KEY02, hotSearchVo.data.info[position].keyword)
            startActivity(intent)
            return@setOnTagClickListener true
        }
        isGetHotSuccess = true
        search_flowlayout.visibility = View.VISIBLE

    }

    override fun getHotSearchFail(msg: String?) {
        ToastUtil.showSingleToast(msg)
        Log.e("search",msg)
    }



}
