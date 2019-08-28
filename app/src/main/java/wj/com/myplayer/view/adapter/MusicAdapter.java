package wj.com.myplayer.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.commonlib.network.HttpHandler;
import com.example.commonlib.utils.StringUtils;
import com.example.commonlib.utils.ToastUtil;

import java.util.List;

import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.constant.FlagConstant;
import wj.com.myplayer.daodb.MediaDaoManager;
import wj.com.myplayer.daodb.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.net.DoubanNetworkWrapper;
import wj.com.myplayer.net.bean.douban.MusicSearchBean;
import wj.com.myplayer.utils.LogUtils;
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
            DoubanNetworkWrapper.searchMusic(item.getTitle(), "", "", "1", new HttpHandler<MusicSearchBean>() {
                @Override
                public void onSuccess(MusicSearchBean data) {
                    if (data.getMusics().size() == 0) return;
                    String url = data.getMusics().get(0).getImage();
                    LogUtils.i("searchMusic","title is "+item.getTitle() +" url is: " + url);
                    Glide.with(mContext).load(data.getMusics().get(0).getImage()).into(imageView);
                    item.setCoverUrl(url);
                    manager.update(item);
                    //imageView.setTag(FlagConstant.TAG_KEY);
                }

                @Override
                public void onFailure(String response) {
                    super.onFailure(response);
                    //ToastUtil.showSingleToast(response,);
                }
            });
        }else {
            Glide.with(mContext).load(item.getCoverUrl()).into(imageView);
        }



        helper.addOnClickListener(R.id.item_music_more);

    }
}
