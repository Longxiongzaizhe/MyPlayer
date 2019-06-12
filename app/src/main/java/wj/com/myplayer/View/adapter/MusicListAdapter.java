package wj.com.myplayer.View.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import wj.com.myplayer.Config.MainApplication;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.MediaUtils;

public class MusicListAdapter extends BaseQuickAdapter<MediaEntity, BaseViewHolder> {

    public MusicListAdapter( List<MediaEntity> data) {
        super(R.layout.item_music, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaEntity item) {
        helper.setText(R.id.item_music_title,item.getTitle());
        helper.setText(R.id.item_music_author,item.getArtist());
        helper.setImageBitmap(R.id.item_music_albums, MediaUtils.getArtwork(MainApplication.get().getApplicationContext(),
                item.getId(),item.getAlbum_id(),true,true));
        helper.addOnClickListener(R.id.item_music_more);

    }
}
