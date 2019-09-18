package com.hjl.module_local

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.view.View
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.commonlib.utils.PermissionsUtiles
import com.hjl.module_local.fragment.AlbumsFragment
import com.hjl.module_local.fragment.AuthorFragment
import com.hjl.module_local.fragment.LocalFragment
import com.hjl.module_main.mvp.fragment.MusicService
import com.hjl.module_main.mvp.fragment.PlayFragment
import kotlinx.android.synthetic.main.activity_local_music.*

class LocalMusicActivity : BaseMultipleActivity() {

    val fragments : ArrayList<Fragment> = ArrayList()
    val mTitles : ArrayList<String> = ArrayList()
    var mPageAdapter : LazyFragmentPagerAdapter? = null

    var mBinder : MusicService.MusicBinder? = null

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CAMERA)

    override fun initTitle() {
        mTitleCenterTv.text = resources.getString(R.string.local_app_name)
        mTitleLeftIv.visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)

        initView()
        initData()

        PermissionsUtiles.requestPermissions(this, permissions) //请求权限
        val startMusicIntent = Intent(this, MusicService::class.java)
        bindService(startMusicIntent, connection,BIND_AUTO_CREATE)
        startService(startMusicIntent)
    }

    override fun initView() {


        mPageAdapter = LazyFragmentPagerAdapter(supportFragmentManager, fragments, mTitles)
        local_viewpager.adapter = mPageAdapter
        local_tab_layout.setupWithViewPager(local_viewpager)

    }

    override fun initData() {
        super.initData()

        mTitles.add("音乐")
        mTitles.add("专辑")
        mTitles.add("歌手")
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBinder = service as MusicService.MusicBinder

            fragments.add(LocalFragment.newInstance(mBinder))
            fragments.add(AlbumsFragment.newInstance(mBinder!!))
            fragments.add(AuthorFragment.newInstance(mBinder!!))
            mPageAdapter!!.notifyDataSetChanged()

            val playFragment = play_fragment as PlayFragment
            playFragment.setBinder(mBinder)


        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}
