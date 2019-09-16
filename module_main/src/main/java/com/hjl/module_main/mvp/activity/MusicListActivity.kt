package com.hjl.module_main.mvp.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.daodb.*
import com.hjl.module_main.mvp.adapter.MusicAdapter
import com.hjl.module_main.mvp.fragment.MusicService
import kotlinx.android.synthetic.main.activity_music_list.*

class MusicListActivity : BaseMultipleActivity(), BaseQuickAdapter.OnItemClickListener {




    var datalist : ArrayList<MediaEntity> = ArrayList()
    val relManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_REL) as MediaRelManager
    val manager =  DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_DAO) as MediaDaoManager
    val listManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_LIST) as MediaListManager
    val adapter = MusicAdapter(datalist)
    public lateinit var mBinder : MusicService.MusicBinder
    private var listId = -1L



    var connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = service as MusicService.MusicBinder
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)

        var intent = Intent(this, MusicService::class.java)
        bindService(intent,connection,BIND_AUTO_CREATE)
        getKeyData()
        initView()
        initData()


    }

    override fun getKeyData() {
        listId = intent.getLongExtra(FlagConstant.INTENT_KEY01,-1)
    }

    override fun initView() {
        music_list_rv.layoutManager = LinearLayoutManager(this)
        music_list_rv.adapter = adapter

        setSupportActionBar(list_detail_toolbar)
        list_detail_toolbar.setNavigationOnClickListener { finish() }

    }

    override fun initData() {

        if (listId < 0) return

        val relList = relManager.query(MediaRelEntityDao.Properties.MediaListId.eq(listId)) as ArrayList<MediaRelEntity>
        datalist.clear()
        for (relEntity in relList){
            datalist.add(manager.query(relEntity.songId))
        }
        adapter.notifyDataSetChanged()
        val entity = listManager.query(listId)
        collapsing_toolbar.title =  entity.getName()
        adapter.setShowEdit(false)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        mBinder.play(datalist[position])
        mBinder.setPlayList(datalist)
    }

    override fun initTitle() {
        mTitleCl.visibility = View.GONE
    }
}
