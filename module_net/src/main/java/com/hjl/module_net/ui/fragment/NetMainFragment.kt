package com.hjl.module_net.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.mvp.BaseMusicMvpFragment
import com.hjl.module_main.router.RNet
import com.hjl.module_main.ui.activity.MainActivity
import com.hjl.module_net.R
import com.hjl.module_net.mvp.contract.NetMainContract
import com.hjl.module_net.net.vo.BannerVo
import com.hjl.module_net.mvp.presenter.NetMainPresenter
import com.hjl.module_net.net.vo.NetFunctionBean
import com.hjl.module_net.ui.SearchSongActivity
import com.hjl.module_net.ui.adapter.NetFunctionAdapter
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_net_main.*


/**
 * created by long on 2019/10/12
 */

@Route(path = RNet.RNetMain)
class NetMainFragment : BaseMusicMvpFragment<NetMainPresenter>(),NetMainContract.INetMainView {


    lateinit var recommendFragmentList : ArrayList<Fragment>
    lateinit var recommendTileList : ArrayList<String>

    private lateinit var fragmentPagerAdapter : LazyFragmentPagerAdapter

    lateinit var funcAdapter : NetFunctionAdapter
    lateinit var functionList: MutableList<NetFunctionBean>
    lateinit var mainActivity: MainActivity

    override fun createPresenter(): NetMainPresenter {
        return NetMainPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_net_main
    }

    override fun initView(view: View?) {
        net_main_search.setOnClickListener {
            startActivityForResult(Intent(context, SearchSongActivity::class.java),FlagConstant.REQUEST_CODE_ONE)
        }

    }

    override fun initData() {

        mainActivity = mActivity as MainActivity

        mPresenter.getBanner()

        recommendTileList = ArrayList()
        recommendFragmentList = ArrayList()
        functionList = ArrayList()
        funcAdapter = NetFunctionAdapter(functionList)

        recommendTileList.add("新歌")
        recommendTileList.add("热门")

        recommendFragmentList.add(Fragment())
        recommendFragmentList.add(Fragment())

        fragmentPagerAdapter = LazyFragmentPagerAdapter(fragmentManager,recommendFragmentList,recommendTileList)
        main_net_vp.adapter = fragmentPagerAdapter
        main_net_tab.setupWithViewPager(main_net_vp)

        net_func_rv.adapter = funcAdapter
        net_func_rv.layoutManager = GridLayoutManager(context,3)
        functionList.apply {
            add(NetFunctionBean("热门歌手",R.drawable.bg_function_singer))
            add(NetFunctionBean("热门歌单",R.drawable.bg_function_list))
            add(NetFunctionBean("所有分类",R.drawable.bg_function_more))
            funcAdapter.notifyDataSetChanged()
        }

        funcAdapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0 -> {
                    mainActivity.addFragmentInBackStack(AllSingerFragment())
                }


            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()

        net_main_banner?.releaseBanner()
    }

    override fun onGetBannerSuccess(vo: BannerVo?) {
        net_main_banner.setImageLoader(object : ImageLoader(){
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context!!).load(path).into(imageView!!)
            }

        }).isAutoPlay(true).setImages(vo!!.data.slider).setDelayTime(3000).start()
    }

    override fun onGetBannerFail(msg: String?) {
        ToastUtil.showSingleToast(msg)
        val data = ArrayList<String>()
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1831126.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1826655.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1832019.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1831199.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1829466.jpg")

        net_main_banner.setImageLoader(object : ImageLoader(){
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context!!).load(path).into(imageView!!)
            }

        }).isAutoPlay(true).setImages(data).setDelayTime(3000).start()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FlagConstant.REQUEST_CODE_ONE){
            if (resultCode == Activity.RESULT_OK){
                val keyword = data?.getStringExtra(FlagConstant.INTENT_KEY01)
                val resultFrament = NetSearchResultFragment.newInstance(keyword)
//                agentFragment.showFragment(resultFrament,"NetSearchResultFragment.class")
                mainActivity.addFragmentInBackStack(resultFrament)
            }
        }

    }
}