package com.hjl.module_main.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_main.R

import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.module.RApp

import kotlinx.android.synthetic.main.fragment_main_local.*

class MainLocalFragment : BaseFragment(){

    private val fragments : ArrayList<Fragment> = ArrayList()
    private val mTitles : ArrayList<String> = ArrayList()
    private var mPageAdapter : LazyFragmentPagerAdapter? = null


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
        val binder  = arguments!!.getSerializable(FlagConstant.BINDER) as MusicService.MusicBinder
        val localFragment  = ARouter.getInstance().build(RApp.LOCAL_FRAGMENT).navigation() as BaseFragment
        val authorFragment  = ARouter.getInstance().build(RApp.AUTHOR_FRAGMENT).navigation() as BaseFragment
        val albumsFragment = ARouter.getInstance().build(RApp.ALBUMS_FRAGMENT).navigation() as BaseFragment

        localFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,binder) }
        authorFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,binder) }
        albumsFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,binder) }


        fragments.add(localFragment)
        fragments.add(albumsFragment)
        fragments.add(authorFragment)


        mPageAdapter = LazyFragmentPagerAdapter(fragmentManager, fragments, mTitles)
        local_viewpager.adapter = mPageAdapter
        local_tab_layout.setupWithViewPager(local_viewpager)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_local
    }


}