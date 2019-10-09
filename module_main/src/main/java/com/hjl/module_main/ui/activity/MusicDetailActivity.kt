package com.hjl.module_main.ui.activity

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.commonlib.utils.DateUtils
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
import com.hjl.module_main.customview.MusicModePopWindow
import com.hjl.module_main.ui.fragment.MusicInterface
import com.hjl.module_main.ui.fragment.MusicService
import com.hjl.module_main.utils.SPUtils
import kotlinx.android.synthetic.main.activity_music_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.ref.WeakReference
import java.util.*


class MusicDetailActivity : BaseMultipleActivity(), MusicInterface.OnMediaChangeListener, View.OnClickListener {

    private var entity : MediaEntity? = null
    private var mBinder : MusicService.MusicBinder? = null
    private var isPlaying : Boolean = false
    private var popWindow: MusicModePopWindow? = null
    private var timeTask: MyTimeTask? = null //定期器任务
    private var timer: Timer? = null
    private var handler = MusicSeekHander(this)
    lateinit var player : MediaPlayer

    companion object{
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val BTN_PLAY = "BTN_PLAY"
        const val BTN_NEXT = "BTN_NEXT"
        const val TV_NAME = "TV_NAME"
        const val TV_AUTHOR = "TV_AUTHOR"
    }



    override fun getLayoutId(): Int {
        return R.layout.activity_music_detail
    }

    override fun initTitle() {
        mTitleCl.setBackgroundColor(resources.getColor(R.color.transparent))
    }

    override fun getKeyData() {
        super.getKeyData()

        mBinder = intent.getSerializableExtra(FlagConstant.BINDER) as MusicService.MusicBinder
        mBinder?.addOnMediaChangeListener(this)
        if (mBinder != null){
            player = mBinder!!.service.player
            detail_current_tv.text = DateUtils.getMusicTime(player.currentPosition)
            detail_duration_tv.text = DateUtils.getMusicTime(player.duration)

        }

        EventBus.getDefault().register(this)

    }

    override fun initView() {

        initTransition()

        val entityId = intent.getLongExtra(FlagConstant.INTENT_KEY01,-1)
        if (entityId == -1L)  return

        initViewByMedia(entityId)

        popWindow = MusicModePopWindow(this)
        popWindow!!.popupWindow.setOnDismissListener {
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
        mTitleLeftIv.setOnClickListener(this)


    }

    private fun initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           // ViewCompat.setTransitionName(detail_music_view, IMG_TRANSITION)
            ViewCompat.setTransitionName(detail_play_btn, BTN_PLAY)
            ViewCompat.setTransitionName(mTitleCenterTv, TV_NAME)
            ViewCompat.setTransitionName(mTitleCenterSmallTv, TV_AUTHOR)
            ViewCompat.setTransitionName(detail_next_btn, BTN_NEXT)
        }
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
                var bitmap = Glide.with(this).asBitmap().centerCrop().apply(options).load(entity?.coverUrl).submit(500, 500).get()
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
            R.id.title_left_iv -> {
                finishAfterTransition()
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

    override fun onStart() {
        super.onStart()
        if(timeTask == null) run {
            //设定定时器任务
            timeTask = MyTimeTask()
            timer = Timer()
            timer!!.schedule(timeTask, 50, 50) //设定定时器
        }
    }

    inner class MyTimeTask : TimerTask(){
        override fun run() {
            if (player.isPlaying) {
                //获取当前的播放进度
                val duration = player.duration //总长度
                val position = player.currentPosition //当前进度

                //发送消息给主线程
                val msg = Message()
                msg.what = 1
                msg.arg1 = duration
                msg.arg2 = position
                handler.sendMessage(msg)
            }
        }

    }


    class MusicSeekHander(activity: MusicDetailActivity):Handler(){
        var weekActivity :WeakReference<MusicDetailActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

            if (weekActivity.get() == null || weekActivity.get()!!.isFinishing) return

            if (msg?.what == 1) {
                val duration = msg.arg1
                val position = msg.arg2


                weekActivity.get()!!.seek_bar.max = duration
                weekActivity.get()!!.seek_bar.progress = position
                weekActivity.get()!!.detail_current_tv.text = DateUtils.getMusicTime(position)
                weekActivity.get()!!.detail_duration_tv.text = DateUtils.getMusicTime(duration)

                //设定拖动播放
                weekActivity.get()!!.seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        weekActivity.get()!!.player.seekTo(seekBar.progress) //设定播放器到进度条的进度
                        if (!weekActivity.get()!!.player.isPlaying) {
                            weekActivity.get()!!.player.start()
                            weekActivity.get()!!.detail_play_btn.setImageResource(R.drawable.main_icon_pause_transparent)
                        }
                    }
                })

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        timer?.cancel()
        timer?.purge()
        timer = null
        timeTask = null
    }
}
