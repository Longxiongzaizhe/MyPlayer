package com.wj.myplayer.mview;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hjl.commonlib.mview.BaseDialog;

public class DeleteDialog extends BaseDialog {


    public DeleteDialog(@NonNull Context context) {
        super(context, 2);

        setTitle("确定删除");

    }


}
