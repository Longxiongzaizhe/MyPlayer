package com.hjl.module_local

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hjl.commonlib.base.BaseMultipleActivity

class LocalMusicActivity : BaseMultipleActivity() {
    override fun initTitle() {
        mTitleCenterTv.setText(resources.getString(R.string.local_app_name))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)
    }
}
