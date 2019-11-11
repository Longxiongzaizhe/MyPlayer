package com.hjl.module_net.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import com.hjl.commonlib.base.BaseFragment
import com.hjl.module_net.R
import kotlinx.android.synthetic.main.fragment_all_singer.*

import com.hjl.commonlib.utils.ToastUtil
import q.rorbin.verticaltablayout.VerticalTabLayout
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

        for (title : String in mTitles){
            val tab = QTabView(context)
            tab.titleView.text = title
            tab.titleView.setTextColor(resources.getColor(R.color.common_black))
            net_singer_tab.addTab(QTabView(context))
        }

        net_singer_tab.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {

            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                ToastUtil.showSingleToast(mTitles[position])
            }

        })



    }
}