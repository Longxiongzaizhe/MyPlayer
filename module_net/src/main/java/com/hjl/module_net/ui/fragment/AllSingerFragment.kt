package com.hjl.module_net.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v4.app.Fragment
import android.view.View
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_net.R
import kotlinx.android.synthetic.main.fragment_all_singer.*

import com.hjl.commonlib.utils.ToastUtil
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.QTabView
import q.rorbin.verticaltablayout.widget.TabView


/**
 *
 * created by long on 2019/11/11
 */
class AllSingerFragment : BaseFragment() {

    lateinit var agentFragment : NetAgentFragment

    override fun getLayoutId(): Int {
        return R.layout.fragment_all_singer
    }

    override fun initView(view: View?) {
        agentFragment = parentFragment as NetAgentFragment
    }


    override fun initData() {
        val mTitles = arrayOf("所有歌手","男歌手","女歌手")
        val mFragments = ArrayList<SingerFragment>()

        mFragments.add(SingerFragment.newInstance(SingerFragment.ALL_SINGER))
        mFragments.add(SingerFragment.newInstance(SingerFragment.MALE_SINGER))
        mFragments.add(SingerFragment.newInstance(SingerFragment.FEMALE_SINGER))

        val pagerAdapter = LazyFragmentPagerAdapter(fragmentManager, mFragments as List<Fragment>,mTitles.toList())
        net_singer_vp.adapter = pagerAdapter
        net_singer_tab.setupWithViewPager(net_singer_vp)
        net_singer_tab.setTabMode(VerticalTabLayout.TAB_MODE_SCROLLABLE)
        net_singer_tab.setTabSelected(0)

        net_singer_tab.setTabAdapter(object : SimpleTabAdapter(){
            override fun getCount(): Int {
                return mTitles.size
            }

            override fun getTitle(position: Int): ITabView.TabTitle {
                return ITabView.TabTitle.Builder()
                        .setContent(mTitles[position])
                        .setTextColor(Color.WHITE, Color.BLACK)
                        .build()
            }
        })
    }

}