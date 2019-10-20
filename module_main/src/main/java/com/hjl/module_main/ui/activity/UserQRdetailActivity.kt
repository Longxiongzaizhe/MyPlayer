package com.hjl.module_main.ui.activity

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.module_main.constant.SPConstant
import com.hjl.module_main.utils.SPUtils
import kotlinx.android.synthetic.main.activity_user_qrdetail.*
import cn.bertsir.zbar.utils.QRUtils
import android.graphics.Bitmap
import com.alibaba.fastjson.serializer.AwtCodec.support
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R



class UserQRdetailActivity : BaseMultipleActivity() {
    override fun initTitle() {
        mTitleCenterTv.text = "我的名片"
    }

    override fun getLayoutId(): Int {
        return com.hjl.module_main.R.layout.activity_user_qrdetail
    }

    override fun initView() {
        super.initView()
        user_name.text = SPUtils.get(this, SPConstant.USER_NAME, "Sunny")
        val iconBitmap = BitmapFactory.decodeFile(SPConstant.USER_ICON_PATH)
        user_icon.setImageBitmap(iconBitmap)

        val qrCodeBitmap =
                QRUtils.getInstance().
                createQRCodeAddLogo(
                        user_name.text.toString(),
                        iconBitmap)
        user_qr.setImageBitmap(qrCodeBitmap)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
