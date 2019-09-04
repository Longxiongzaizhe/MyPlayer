package com.wj.myplayer.mvp.ui.fragment.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_local.fragment.AlbumsFragment
import com.hjl.module_local.fragment.AuthorFragment
import com.hjl.module_local.fragment.LocalFragment
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.mvp.fragment.MusicService
import com.wj.myplayer.R
import kotlinx.android.synthetic.main.fragment_main_local.*

public class MainLocalFragment : BaseFragment(){

    val fragments : ArrayList<Fragment> = ArrayList()
    val mTitles : ArrayList<String> = ArrayList()
    var mPageAdapter : LazyFragmentPagerAdapter? = null

    var localFragment : LocalFragment? = null
    var albumsFragment: AlbumsFragment? = null
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
        val binder  = arguments!!.getSerializable(FlagConstant.BINDER) as MusicService.MusicBinder
//        var localFragment :LocalFragment = ARouter.getInstance().build(RApp.LOCAL_FRAGMENT).navigation() as LocalFragment
//        var authorFragment :AuthorFragment = ARouter.getInstance().build(RApp.AUTHOR_FRAGMENT).navigation() as AuthorFragment
//        var albumsFragment :AlbumsFragment = ARouter.getInstance().build(RApp.ALBUMS_FRAGMENT).navigation() as AlbumsFragment
       // localFragment =


        fragments.add(LocalFragment.newInstance(binder))
        fragments.add(AlbumsFragment.newInstance(binder))
        fragments.add(AuthorFragment.newInstance(binder))

        mPageAdapter = LazyFragmentPagerAdapter(fragmentManager, fragments, mTitles)
        local_viewpager.adapter = mPageAdapter
        local_tab_layout.setupWithViewPager(local_viewpager)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_local
    }


}