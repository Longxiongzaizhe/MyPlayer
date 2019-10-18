package com.hjl.module_net

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.app.abby.xbanner.ImageLoader
import com.bumptech.glide.Glide
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.router.RNet
import com.hjl.module_main.utils.FileUtils
import com.hjl.module_net.contract.NetMainContract
import com.hjl.module_net.net.BannerVo
import com.hjl.module_net.presenter.impl.NetMainPresenter
import kotlinx.android.synthetic.main.fragment_net_main.*
import pl.droidsonroids.gif.GifImageView


/**
 * created by long on 2019/10/12
 */

@Route(path = RNet.RNetMain)
class NetMainFragment : BaseMvpMultipleFragment<NetMainPresenter>(),NetMainContract.INetMainView {


    override fun createPresenter(): NetMainPresenter {
        return NetMainPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_net_main
    }

    override fun initView(view: View?) {

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
    }
}