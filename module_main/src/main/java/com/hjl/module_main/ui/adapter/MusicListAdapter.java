package com.hjl.module_main.ui.adapter;

import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_main.R;
import com.hjl.module_main.daodb.MediaListEntity;
import com.hjl.module_main.daodb.MediaRelManager;

import java.util.List;

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
            helper.setImageBitmap(R.id.list_album_iv, BitmapFactory.decodeFile(item.albums));
        }



    }
}
