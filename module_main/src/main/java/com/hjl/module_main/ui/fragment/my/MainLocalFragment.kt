package com.hjl.module_main.ui.fragment.my

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_main.R

import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.mvp.BaseMusicFragment
import com.hjl.module_main.router.RLocal
import com.hjl.module_main.service.MusicService
import com.hjl.module_main.ui.activity.MainActivity

import kotlinx.android.synthetic.main.fragment_main_local.*

class MainLocalFragment : BaseMusicFragment() {

    private var fragments : ArrayList<Fragment>? = null
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
        mTitleCenterTv.text = "本地音乐"
        mTitleLeftIv.setOnClickListener {
            (mActivity as MainActivity).popBackStack()
        }
        showTitleView()
    }

    override fun onConnectedService() {
        val localFragment  = ARouter.getInstance().build(RLocal.LOCAL_FRAGMENT).navigation() as BaseFragment
        val authorFragment  = ARouter.getInstance().build(RLocal.AUTHOR_FRAGMENT).navigation() as BaseFragment
        val albumsFragment = ARouter.getInstance().build(RLocal.ALBUMS_FRAGMENT).navigation() as BaseFragment

        localFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,mBinder) }
        authorFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,mBinder) }
        albumsFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,mBinder) }

        fragments = ArrayList()
        fragments?.add(localFragment)
        fragments?.add(albumsFragment)
        fragments?.add(authorFragment)

        mPageAdapter = LazyFragmentPagerAdapter(fragmentManager, fragments, mTitles)
        local_viewpager.adapter = mPageAdapter
        local_tab_layout.setupWithViewPager(local_viewpager)

    }

    override fun initData() {
        mTitles.add("音乐")
        mTitles.add("专辑")
        mTitles.add("歌手")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_local
    }


    override fun onDestroyView() {
        super.onDestroyView()

        fragments?.clear()


    }

    override fun notifyDataChange() {
        super.notifyDataChange()

        // 通知更新数据

        fragments?.let {
            for (item in it){
                item.let { fragment ->
                    fragment as BaseFragment
                    fragment.notifyDataChange()
                }
            }
        }



    }


    override fun onDestroy() {
        super.onDestroy()
    }



}