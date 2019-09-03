package com.wj.myplayer.mvp.ui.fragment.local

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hjl.commonlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_author.*
import com.wj.myplayer.R
import com.wj.myplayer.daodb.MediaAuthorEntity
import com.wj.myplayer.daodb.MediaAuthorManager
import com.wj.myplayer.mvp.adapter.AuthorAdapter

class AuthorFragment : BaseFragment() {

    var adapter : AuthorAdapter? = null
    var datalist : List<MediaAuthorEntity> = MediaAuthorManager.get().getAll()

    override fun initView(view: View?) {

        mMultipleStatusView.showLoading()
        adapter = AuthorAdapter(datalist)

    }

    override fun initData() {
        author_rv.layoutManager = LinearLayoutManager(context)
        author_rv.adapter = adapter
        mMultipleStatusView.showContent()

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_author
    }
}