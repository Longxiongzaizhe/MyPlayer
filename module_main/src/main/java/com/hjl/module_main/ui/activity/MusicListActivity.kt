package com.hjl.module_main.ui.activity

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hjl.commonlib.base.mvp.BaseMvpMultipleActivity
import com.hjl.commonlib.mview.IOSDialog
import com.hjl.commonlib.utils.PhotoUtils
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.constant.MediaConstant
import com.hjl.module_main.daodb.*
import com.hjl.module_main.mvp.contract.MusicListContract
import com.hjl.module_main.mvp.presenter.impl.MusicListPresenter
import com.hjl.module_main.router.RMain
import com.hjl.module_main.ui.adapter.MusicAdapter
import com.hjl.module_main.service.MusicService
import com.hjl.module_main.utils.FileUtils
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.activity_music_list.*
import java.io.File

@Route(path = RMain.RMusicList)
class MusicListActivity : BaseMvpMultipleActivity<MusicListPresenter>(), BaseQuickAdapter.OnItemClickListener ,MusicListContract.IMusicListView {


    override fun createPresenter(): MusicListPresenter {
        return MusicListPresenter()
    }

    private var datalist : ArrayList<MediaEntity> = ArrayList()
    private val relManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_REL) as MediaRelManager
    private val manager =  DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_DAO) as MediaDaoManager
    private val listManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_LIST) as MediaListManager
    private val albumsManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_ALBUMS) as MediaAlbumsManager
    private val authorManager = DaoManagerFactory.getInstance().getManager(DaoManager.MEDIA_AUTHOR) as MediaAuthorManager
    val adapter = MusicAdapter(datalist)
    lateinit var mBinder : MusicService.MusicBinder
    private var mDialog: IOSDialog? = null
    private var listId = -1L
    private var authorName = ""
    private var type = -1 // 1 自定义歌单列表 2 专辑列表 3 歌手列表

//    // mvvm
//    private lateinit var mBind : ActivityMusicListBinding
//    private lateinit var listViewModel: MusicListViewModel




    private var connection = object : ServiceConnection{
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

        ARouter.getInstance().inject(this)

        type = intent.getIntExtra(FlagConstant.INTENT_KEY01,-1).apply {
            when(this){
                MediaConstant.LIST_CUSTOM -> {
                    listId = intent.getLongExtra(FlagConstant.INTENT_KEY02,-1)
                }
                MediaConstant.LIST_AUTHOR -> {
                    authorName = intent.getStringExtra(FlagConstant.INTENT_KEY02)
                }
                MediaConstant.LIST_ALBUMS -> {
                    listId = intent.getLongExtra(FlagConstant.INTENT_KEY02,-1)
                }
            }
        }


        val intent = Intent(this, MusicService::class.java)
        bindService(intent,connection,BIND_AUTO_CREATE)
    }

    override fun initView() {
        setSupportActionBar(list_detail_toolbar)
        music_list_rv.layoutManager = LinearLayoutManager(this)
        music_list_rv.adapter = adapter
        adapter.apply {
            setEmptyView(R.layout.layout_no_content,music_list_rv)
        }
        when (type){
            MediaConstant.LIST_CUSTOM -> {
                val entity = listManager.query(listId)
                collapsing_toolbar.title = entity.getName()
                if (!entity.albums.isNullOrEmpty()) {
                    File(entity.albums).apply {
                        if (this.exists()) {
                            BitmapFactory.decodeFile(entity.albums).apply { toolbar_iv.setImageBitmap(this) }
                        } else {
                            ToastUtil.show("文件不存在")
                        }
                    }
                }
                music_list_rv.setOnItemMoveListener(object :OnItemMoveListener{
                    override fun onItemDismiss(p0: RecyclerView.ViewHolder?) {
                        relManager.deleteSongRel(datalist[p0!!.adapterPosition].id,listId)
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
                mDialog = IOSDialog(this).addOption("从相册中选择")
                        .setTitleVisibility(false)
                        .setOnItemClickListener { adapter, view, position ->
                            when (position) {
                                0 // 相册
                                -> {
                                    PhotoUtils.openPic(this@MusicListActivity, FlagConstant.REQUEST_CODE_TWO)
                                    mDialog!!.dismiss()
                                }
                            }
                        }

            }
            MediaConstant.LIST_ALBUMS -> {
                albumsManager.query(listId).let {
                    Glide.with(this).load(it.coverUrl).into(toolbar_iv)
                    mTitleCenterTv.text = it.author
                }
            }
            MediaConstant.LIST_AUTHOR -> {
                authorManager.query(authorName).let {
                    Glide.with(this).load(it.coverUrl).into(toolbar_iv)
                }

            }
        }

        toolbar_iv.setOnClickListener { mDialog?.show() }
        list_detail_toolbar.setNavigationOnClickListener { finish() }



    }

    override fun initData() {

        when(type){
            MediaConstant.LIST_CUSTOM -> {
                mPresenter.getCustomList(listId)
            }
            MediaConstant.LIST_ALBUMS -> {
                mPresenter.getAlbumsList(listId)
            }
            MediaConstant.LIST_AUTHOR -> {
                mPresenter.getAuthorList(authorName)
            }
        }

        adapter.setShowEdit(false)
        adapter.onItemClickListener = this
    }

    override fun onGetCustomListComplete(list: MutableList<MediaEntity>) {
        datalist.clear()
        datalist.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onGetAuthorListComplete(list: MutableList<MediaEntity>) {
        datalist.clear()
        datalist.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onGetAlbumsListComplete(list: MutableList<MediaEntity>) {
        datalist.clear()
        datalist.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
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

                val albumFile = File(FileUtils.SD_FILES_IMAGE + File.separator + listId + ".png")
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data?.data))
                FileUtils.savaBitmapInFile(bitmap,albumFile)
                toolbar_iv.setImageBitmap(bitmap)
                listManager.update(listManager.query(listId).apply { albums = albumFile.absolutePath })
            }
        }
    }
}
