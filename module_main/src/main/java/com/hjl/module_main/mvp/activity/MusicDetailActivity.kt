package com.hjl.module_main.mvp.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.daodb.MediaDaoManager
import com.hjl.module_main.daodb.MediaEntity
import com.hjl.module_main.mvp.fragment.MusicInterface
import com.hjl.module_main.mvp.fragment.MusicService
import kotlinx.android.synthetic.main.activity_music_detail.*


class MusicDetailActivity : BaseMultipleActivity(), MusicInterface.OnMediaChangeListener, View.OnClickListener {

    private var entity : MediaEntity? = null
    private var mBinder : MusicService.MusicBinder? = null
    private var isPlaying : Boolean = false

    override fun initTitle() {
        mTitleCl.setBackgroundColor(resources.getColor(com.hjl.module_main.R.color.transparent))
    }

    override fun getKeyData() {
        super.getKeyData()

        mBinder = intent.getSerializableExtra(FlagConstant.BINDER) as MusicService.MusicBinder
        mBinder?.addOnMediaChangeListener(this)

    }

    override fun initView() {
        val entityId = intent.getLongExtra(FlagConstant.INTENT_KEY01,-1)
        if (entityId == -1L)  return

        initViewByMedia(entityId)


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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_detail)

        getKeyData()
        initView()
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
        }
    }
}
