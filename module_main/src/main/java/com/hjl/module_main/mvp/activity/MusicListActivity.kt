package com.hjl.module_main.mvp.activity

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.commonlib.mview.IOSDialog
import com.hjl.commonlib.utils.PhotoUtils
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.daodb.*
import com.hjl.module_main.mvp.adapter.MusicAdapter
import com.hjl.module_main.mvp.fragment.MusicService
import com.hjl.module_main.utils.FileUtils
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.activity_music_list.*
import java.io.File

class MusicListActivity : BaseMultipleActivity(), BaseQuickAdapter.OnItemClickListener {

    private var datalist : ArrayList<MediaEntity> = ArrayList()
    private val relManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_REL) as MediaRelManager
    val manager =  DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_DAO) as MediaDaoManager
    private val listManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_LIST) as MediaListManager
    val adapter = MusicAdapter(datalist)
    lateinit var mBinder : MusicService.MusicBinder
    private var mDialog: IOSDialog? = null
    private var listId = -1L



    var connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = service as MusicService.MusicBinder
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_music_list
    }


    override fun getKeyData() {
        listId = intent.getLongExtra(FlagConstant.INTENT_KEY01,-1)
        val intent = Intent(this, MusicService::class.java)
        bindService(intent,connection,BIND_AUTO_CREATE)
    }

    override fun initView() {
        music_list_rv.layoutManager = LinearLayoutManager(this)
        music_list_rv.adapter = adapter
        adapter.apply {
            setEmptyView(R.layout.layout_no_content,music_list_rv)
        }
        music_list_rv.setOnItemMoveListener(object :OnItemMoveListener{
            override fun onItemDismiss(p0: RecyclerView.ViewHolder?) {
                relManager.deleteSongRel(datalist.get(p0!!.adapterPosition).id,listId)
                datalist.removeAt(p0.adapterPosition)
                adapter.notifyItemRemoved(p0.adapterPosition)
                if (datalist.size == 0){
                    music_list_rv.isItemViewSwipeEnabled = false
                }
            }

            override fun onItemMove(p0: RecyclerView.ViewHolder?, p1: RecyclerView.ViewHolder?): Boolean {
                return false
            }

        })

        setSupportActionBar(list_detail_toolbar)
        mDialog = IOSDialog(this).addOption("从相册中选择")
                .setTitleVisibility(false)
                .setOnItemClickListener(object :BaseQuickAdapter.OnItemClickListener{
                    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                        when (position) {
                            0 // 相册
                            -> {
                                PhotoUtils.openPic(this@MusicListActivity, FlagConstant.REQUEST_CODE_TWO)
                                mDialog!!.dismiss()
                            }
                        }
                    }

                })
        list_detail_toolbar.setNavigationOnClickListener { finish() }
        toolbar_iv.setOnClickListener { mDialog!!.show() }


    }

    override fun initData() {

        if (listId < 0) return

        val relList = relManager.query(MediaRelEntityDao.Properties.MediaListId.eq(listId)) as ArrayList<MediaRelEntity>
        datalist.clear()
        for (relEntity in relList){
            datalist.add(manager.query(relEntity.songId))
        }
        if (datalist.size >0) music_list_rv.isItemViewSwipeEnabled = true
        adapter.notifyDataSetChanged()
        val entity = listManager.query(listId)
        collapsing_toolbar.title =  entity.getName()
        if (!entity.albums.isNullOrEmpty()){
            BitmapFactory.decodeFile(entity.albums).apply { toolbar_iv.setImageBitmap(this) }
        }
        adapter.setShowEdit(false)
        adapter.onItemClickListener = this
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        mBinder.play(datalist[position])
        mBinder.setPlayList(datalist)
    }

    override fun initTitle() {
        mTitleCl.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FlagConstant.REQUEST_CODE_TWO //相册
            -> if (resultCode == Activity.RESULT_OK) {

                val albumFile = File(FileUtils.SD_CACHE_IMAGE + File.separator + listId + ".png")
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data?.data))
                FileUtils.savaBitmapInFile(bitmap,albumFile)
                toolbar_iv.setImageBitmap(bitmap)
                listManager.update(listManager.query(listId).apply { albums = albumFile.absolutePath })
            }
        }
    }
}
