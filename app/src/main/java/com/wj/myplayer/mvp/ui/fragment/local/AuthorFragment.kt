package com.wj.myplayer.mvp.ui.fragment.local

import android.content.res.Resources
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hjl.commonlib.base.BaseFragment
import com.hjl.commonlib.utils.DensityUtil
import com.hjl.commonlib.utils.RecycleViewVerticalDivider
import com.hjl.module_main.constant.FlagConstant
import kotlinx.android.synthetic.main.fragment_author.*
import com.wj.myplayer.R
import com.hjl.module_main.daodb.MediaAuthorEntity
import com.hjl.module_main.daodb.MediaAuthorManager
import com.hjl.module_main.mvp.fragment.MusicService
import com.wj.myplayer.mvp.adapter.AuthorAdapter
import com.wj.myplayer.mvp.ui.fragment.main.MainLocalFragment

class AuthorFragment : BaseFragment() {

    var adapter : AuthorAdapter? = null
    var datalist : List<MediaAuthorEntity> = MediaAuthorManager.get().getAll()


    companion object{
        fun newInstance(bundle: Bundle): AuthorFragment {
            val authorFragment = AuthorFragment()
            authorFragment.arguments = bundle
            return authorFragment
        }

        fun newInstance(mBinder: MusicService.MusicBinder): AuthorFragment {
            val bundle = Bundle()
            bundle.putSerializable(FlagConstant.BINDER,mBinder)
            return newInstance(bundle)
        }
    }

    override fun initView(view: View?) {

        mMultipleStatusView.showLoading()
        adapter = AuthorAdapter(datalist)

    }

    override fun initData() {
        author_rv.layoutManager = LinearLayoutManager(context)
        author_rv.adapter = adapter
        mMultipleStatusView.showContent()
        var itemDivider : RecycleViewVerticalDivider = RecycleViewVerticalDivider(context,2,
                resources.getColor(R.color.common_divider_line_color),DensityUtil.dp2px(68f),0)
        author_rv.addItemDecoration(itemDivider)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_author
    }
}