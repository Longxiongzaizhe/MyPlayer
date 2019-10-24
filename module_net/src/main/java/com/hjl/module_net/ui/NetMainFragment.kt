package com.hjl.module_net.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.app.abby.xbanner.ImageLoader
import com.bumptech.glide.Glide
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment
import com.hjl.commonlib.utils.LogUtils
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.router.RNet
import com.hjl.module_net.R
import com.hjl.module_net.mvp.contract.NetMainContract
import com.hjl.module_net.net.vo.BannerVo
import com.hjl.module_net.mvp.presenter.NetMainPresenter
import kotlinx.android.synthetic.main.fragment_net_main.*
import org.greenrobot.eventbus.EventBus
import pl.droidsonroids.gif.GifImageView


/**
 * created by long on 2019/10/12
 */

@Route(path = RNet.RNetMain)
class NetMainFragment : BaseMvpMultipleFragment<NetMainPresenter>(),NetMainContract.INetMainView {

    lateinit var agentFragment : NetAgentFragment

    override fun createPresenter(): NetMainPresenter {
        return NetMainPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_net_main
    }

    override fun initView(view: View?) {
        net_main_search.setOnClickListener {
            startActivityForResult(Intent(context,SearchSongActivity::class.java),FlagConstant.REQUEST_CODE_ONE)
        }

        agentFragment = parentFragment as NetAgentFragment
    }

    override fun initData() {
        mPresenter.getBanner()


    }

    override fun onDestroy() {
        super.onDestroy()

        net_main_banner?.releaseBanner()
    }

    override fun onGetBannerSuccess(vo: BannerVo?) {
        net_main_banner
                .isAutoPlay(true)
                .setDelay(2000)
                .setImageUrls(vo!!.data.slider)
                .setImageLoader(object : ImageLoader{
                    override fun loadImages(context: Context?, url: String?, image: ImageView?) {
                        image?.let {
                            context?.let {
                                it1 -> Glide.with(it1).load(url).into(it)
                            }
                        }
                    }

                    override fun loadGifs(url: String?, gifImageView: GifImageView?, scaleType: ImageView.ScaleType?) {

                    }

                }).start()
    }

    override fun onGetBannerFail(msg: String?) {
        ToastUtil.showSingleToast(msg)
        val data = ArrayList<String>()
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1788145.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1788739.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1788112.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1789452.jpg")
        data.add("http://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1788335.jpg")

        net_main_banner
                .isAutoPlay(true)
                .setDelay(3000)
                .setImageUrls(data)
                .setImageLoader(object : ImageLoader{
                    override fun loadImages(context: Context?, url: String?, image: ImageView?) {
                        image?.let {
                            context?.let {
                                it1 -> Glide.with(it1).load(url).into(it)
                            }
                        }
                    }

                    override fun loadGifs(url: String?, gifImageView: GifImageView?, scaleType: ImageView.ScaleType?) {

                    }

                }).start()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FlagConstant.REQUEST_CODE_ONE){
            if (resultCode == Activity.RESULT_OK){
                val keyword = data?.getStringExtra(FlagConstant.INTENT_KEY01)
                val resultFrament = NetSearchResultFragment.newInstance(keyword)
                agentFragment.showFragment(resultFrament,"NetSearchResultFragment.class")
            }
        }

    }
}