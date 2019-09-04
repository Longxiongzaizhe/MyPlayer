package com.wj.myplayer.mvp.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjl.commonlib.utils.StringUtils;

import java.util.List;

import com.wj.myplayer.R;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.utils.MediaUtils;

public class MusicAdapter extends BaseQuickAdapter<MediaEntity, BaseViewHolder> {

    private MediaDaoManager manager = MediaDaoManager.getInstance();

    public MusicAdapter(List<MediaEntity> data) {
        super(R.layout.item_music, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaEntity item) {
        helper.setText(R.id.item_music_title,item.getTitle());
        helper.setText(R.id.item_music_author,item.getArtist());
        ImageView imageView = helper.getView(R.id.item_music_albums);

        if (StringUtils.isEmpty(item.getCoverUrl())){
            MediaUtils.setMusicCover(mContext,item,imageView);
        }else {
            Glide.with(mContext).load(item.getCoverUrl()).into(imageView);
        }

        helper.addOnClickListener(R.id.item_music_more);

    }
}
