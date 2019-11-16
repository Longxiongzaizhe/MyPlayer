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

    private val fragments : ArrayList<Fragment> = ArrayList()
    private val mTitles : ArrayList<String> = ArrayList()
    private var mPageAdapter : LazyFragmentPagerAdapter? = null
    private lateinit var localFragment : BaseFragment
    private lateinit var authorFragment : BaseFragment
    private lateinit var albumsFragment : BaseFragment


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
        localFragment  = ARouter.getInstance().build(RLocal.LOCAL_FRAGMENT).navigation() as BaseFragment
        authorFragment  = ARouter.getInstance().build(RLocal.AUTHOR_FRAGMENT).navigation() as BaseFragment
        albumsFragment = ARouter.getInstance().build(RLocal.ALBUMS_FRAGMENT).navigation() as BaseFragment

        localFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,mBinder) }
        authorFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,mBinder) }
        albumsFragment.arguments = Bundle().apply { putSerializable(FlagConstant.BINDER,mBinder) }

        fragments.add(localFragment)
        fragments.add(albumsFragment)
        fragments.add(authorFragment)

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
        // setArguments 只能在 new 时传递数据
//        arguments = Bundle().also { it.putSerializable(FlagConstant.BINDER,mBinder) }
    }

    override fun notifyDataChange() {
        super.notifyDataChange()

        // 通知更新数据
        authorFragment.notifyDataChange()
        albumsFragment.notifyDataChange()
        localFragment.notifyDataChange()


    }

    override fun onDestroy() {
        super.onDestroy()
    }



}