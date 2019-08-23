package wj.com.myplayer.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.daoDB.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.utils.MediaUtils;

public class MusicAdapter extends BaseQuickAdapter<MediaEntity, BaseViewHolder> {

    public MusicAdapter(List<MediaEntity> data) {
        super(R.layout.item_music, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaEntity item) {
        helper.setText(R.id.item_music_title,item.getTitle());
        helper.setText(R.id.item_music_author,item.getArtist());
        item.cover = MediaUtils.getArtwork(MainApplication.get().getApplicationContext().getContentResolver(),Integer.valueOf(item.id.toString()),
                (int)item.album_id,true,true);
        if (item.cover != null){
            helper.setImageBitmap(R.id.item_music_albums,item.cover);
        }else {
            helper.setImageResource(R.id.item_music_albums,R.drawable.icon_dog);
        }



        helper.addOnClickListener(R.id.item_music_more);

    }
}
