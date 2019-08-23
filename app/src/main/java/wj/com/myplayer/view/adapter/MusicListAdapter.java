package wj.com.myplayer.view.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.utils.StringUtils;

import java.util.List;

import wj.com.myplayer.daoDB.MediaListEntity;
import wj.com.myplayer.daoDB.MediaRelManager;
import wj.com.myplayer.R;

public class MusicListAdapter extends BaseQuickAdapter<MediaListEntity, BaseViewHolder> {

    MediaRelManager relManager = MediaRelManager.getInstance();

    public MusicListAdapter( List<MediaListEntity> data) {
        super(R.layout.item_list_music, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaListEntity item) {
        helper.setText(R.id.list_name_tv,item.name);
        helper.setText(R.id.list_count_tv,relManager.queryMediaList(item.id).size() + "首");
        if (!StringUtils.isEmpty(item.albums)){
            Bitmap bitmap = BitmapFactory.decodeFile(item.albums);
            helper.setImageBitmap(R.id.list_album_iv,bitmap);
        }

    }
}
