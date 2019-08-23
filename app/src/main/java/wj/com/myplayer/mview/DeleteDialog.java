package wj.com.myplayer.mview;

import android.content.Context;
import android.support.annotation.NonNull;

public class DeleteDialog extends BaseDialog {


    public DeleteDialog(@NonNull Context context) {
        super(context, 2);

        setTitle("确定删除");

    }


}
