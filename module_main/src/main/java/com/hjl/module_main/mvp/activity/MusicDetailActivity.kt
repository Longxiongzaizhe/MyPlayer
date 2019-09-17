package com.hjl.module_main.mvp.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.commonlib.utils.DensityUtil
import com.hjl.commonlib.utils.StringUtils
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.R
import com.hjl.module_main.bean.MusicModeBus
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.constant.MediaConstant
import com.hjl.module_main.constant.SPConstant
import com.hjl.module_main.daodb.MediaDaoManager
import com.hjl.module_main.daodb.MediaEntity
import com.hjl.module_main.mview.MusicModePopWindow
import com.hjl.module_main.mvp.fragment.MusicInterface
import com.hjl.module_main.mvp.fragment.MusicService
import com.hjl.module_main.utils.SPUtils
import kotlinx.android.synthetic.main.activity_music_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MusicDetailActivity : BaseMultipleActivity(), MusicInterface.OnMediaChangeListener, View.OnClickListener {

    private var entity : MediaEntity? = null
    private var mBinder : MusicService.MusicBinder? = null
    private var isPlaying : Boolean = false
    private var popWindow: MusicModePopWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_detail)

        getKeyData()
        initView()
        initData()
    }

    override fun initTitle() {
        mTitleCl.setBackgroundColor(resources.getColor(R.color.transparent))
    }

    override fun getKeyData() {
        super.getKeyData()

        mBinder = intent.getSerializableExtra(FlagConstant.BINDER) as MusicService.MusicBinder
        mBinder?.addOnMediaChangeListener(this)

        EventBus.getDefault().register(this)

    }

    override fun initView() {
        val entityId = intent.getLongExtra(FlagConstant.INTENT_KEY01,-1)
        if (entityId == -1L)  return

        initViewByMedia(entityId)

        popWindow = MusicModePopWindow(this)
        popWindow!!.getPopupWindow().setOnDismissListener {
            popWindow!!.showBackgroundDIM(window, 1.0f)
        }

        if (mBinder != null && mBinder!!.isPlaying){
            isPlaying = mBinder!!.isPlaying
            detail_music_view.start()
            detail_play_btn.setImageResource(R.drawable.main_icon_pause_transparent)
        }

        detail_play_btn.setOnClickListener(this)
        detail_menu_btn.setOnClickListener(this)
        detail_previous_btn.setOnClickListener(this)
        detail_next_btn.setOnClickListener(this)
        detail_menu_btn.setOnClickListener(this)
        detail_mode_btn.setOnClickListener(this)


    }

    private fun initViewByMedia(entityId: Long) {
        entity = MediaDaoManager.getInstance().query(entityId)
        mTitleCenterTv.text = entity?.title
        mTitleCenterSmallTv.text = entity?.artist
        mTitleCenterSmallTv.visibility = View.VISIBLE

        mLlRoot.setBackgroundColor(resources.getColor(R.color.gray))

        if (isDestroyed) return
        Glide.with(this).asDrawable().load(entity?.coverUrl).apply(RequestOptions.bitmapTransform(BlurTransformation(this, 18, 2)))
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        mLlRoot.background = resource
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        ToastUtil.show("图片加载失败")
                    }
                })

        Thread(Runnable {
           // detail_music_view.setmCoverUrl(entity?.coverUrl)
            if (!StringUtils.isEmpty(entity?.coverUrl)){
                val options : RequestOptions = RequestOptions().circleCrop()
                val bitmap = Glide.with(this).asBitmap().centerCrop().apply(options).load(entity?.coverUrl).submit(500, 500).get()
                detail_music_view.setCoverBm(bitmap)
            }

        }).start()



    }



    override fun initData() {
        setMusicMode(SPUtils.get(this, SPConstant.MUSIC_PLAY_MODE, "CIRCLE"))



    }


    override fun onDataChange(entity: MediaEntity?) {
        initViewByMedia(entityId = entity!!.id)
    }

    override fun onPlayEnd() {
        detail_music_view.pause()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.detail_play_btn ->
                if (isPlaying){
                    isPlaying = false
                    mBinder?.pause()
                    detail_music_view.pause()
                    detail_play_btn.setImageResource(R.drawable.icon_play_transparent)
                }else{
                    isPlaying = true
                    mBinder?.play()
                    detail_music_view.start()
                    detail_play_btn.setImageResource(R.drawable.main_icon_pause_transparent)
                }
            R.id.detail_previous_btn ->
                mBinder?.playPrevious()
            R.id.detail_next_btn ->
                mBinder?.playNext()
            R.id.detail_mode_btn ->{

                popWindow?.showBackgroundDIM(window, -1f)
                popWindow?.showAsDropDown(detail_mode_btn,DensityUtil.dp2px(50f),-DensityUtil.dp2px(200f))
            }

        }
    }


    @Subscribe
    fun setMusicMode(mode : MusicModeBus){
        setMusicMode(mode.mode)
    }

    private fun setMusicMode(mode: String) {
        when (mode) {
            MediaConstant.MusicMode.CIRCLE.toString() ->
                detail_mode_btn.setImageResource(R.drawable.main_icon_music_circle_transparent)
            MediaConstant.MusicMode.RANDOM.toString() ->
                detail_mode_btn.setImageResource(R.drawable.main_icon_tran_random_transparent)
            MediaConstant.MusicMode.SEQUENT.toString() ->
                detail_mode_btn.setImageResource(R.drawable.main_icon_music_sequent_transparent)
            MediaConstant.MusicMode.SINGLE.toString() ->
                detail_mode_btn.setImageResource(R.drawable.main_icon_music_single_transparent)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}