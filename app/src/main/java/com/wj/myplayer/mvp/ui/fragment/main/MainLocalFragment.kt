package com.wj.myplayer.mvp.ui.fragment.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.mvp.fragment.MusicService
import com.wj.myplayer.R
import com.wj.myplayer.mvp.adapter.LazyFragmentPagerAdapter
import com.wj.myplayer.mvp.ui.fragment.local.AlbumsFragment
import com.wj.myplayer.mvp.ui.fragment.local.AuthorFragment
import com.wj.myplayer.mvp.ui.fragment.local.LocalFragment
import kotlinx.android.synthetic.main.fragment_main_local.*

public class MainLocalFragment : BaseFragment(){

    val fragments : ArrayList<Fragment> = ArrayList()
    val mTitles : ArrayList<String> = ArrayList()
    var mPageAdapter : LazyFragmentPagerAdapter? = null
    var localFragment : LocalFragment? = null
    var albumsFragment : AlbumsFragment? = null
    var authorFragment : AuthorFragment? = null

    companion object{
        fun newInstance(bundle: Bundle): MainLocalFragment {
            val mainLocalFragment = MainLocalFragment()
            mainLocalFragment.arguments = bundle
            return mainLocalFragment
        }

        fun newInstance(mBinder: MusicService.MusicBinder): MainLocalFragment {
            val bundle = Bundle()
            bundle.putSerializable(FlagConstant.BINDER,mBinder)
            return newInstance(bundle)
        }
    }

    override fun initView(view: View?) {
        mTitles.add("音乐")
        mTitles.add("专辑")
        mTitles.add("歌手")
    }

    override fun initData() {
        val binder = arguments!!.getSerializable(FlagConstant.BINDER)
        if (localFragment == null) localFragment = LocalFragment.newInstance(binder as MusicService.MusicBinder)
        if (albumsFragment == null) albumsFragment = AlbumsFragment.newInstance(binder as MusicService.MusicBinder)
        if (authorFragment == null) authorFragment = AuthorFragment.newInstance(binder as MusicService.MusicBinder)
        localFragment?.let { fragments.add(it) }
        albumsFragment?.let { fragments.add(it) }
        authorFragment?.let { fragments.add(it) }

        mPageAdapter = LazyFragmentPagerAdapter(fragmentManager,fragments,mTitles)
        local_viewpager.adapter = mPageAdapter
        local_tab_layout.setupWithViewPager(local_viewpager)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_local
    }


}