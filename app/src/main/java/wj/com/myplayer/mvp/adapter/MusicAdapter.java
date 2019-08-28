package wj.com.myplayer.mvp.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.utils.StringUtils;

import java.util.List;

import wj.com.myplayer.R;
import wj.com.myplayer.daodb.MediaDaoManager;
import wj.com.myplayer.daodb.MediaEntity;
import wj.com.myplayer.utils.MediaUtils;

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
            MediaUtils.setMusicAlbum(mContext,item,imageView);
        }else {
            Glide.with(mContext).load(item.getCoverUrl()).into(imageView);
        }



        helper.addOnClickListener(R.id.item_music_more);

    }
}
