package wj.com.myplayer.mview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.Bean.SimpleTextBean;
import wj.com.myplayer.DaoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.DensityUtil;
import wj.com.myplayer.Utils.ToastUtil;
import wj.com.myplayer.View.adapter.SimpleTextAdapter;

public class MusicEditPopWindow extends BasePopWindow {

    private RecyclerView recyclerView;
    private List<SimpleTextBean> datalist;
    private SimpleTextAdapter adapter;
    private MediaRelManager relManager = MediaRelManager.getInstance();
    private OnClickEditListener listener;

    public MusicEditPopWindow(Context c) {
        super(c, R.layout.layout_music_edit, DensityUtil.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.music_edit_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    protected void initData() {
        datalist = new ArrayList<>();
        datalist.add(new SimpleTextBean("删除"));
        datalist.add(new SimpleTextBean("收藏"));
        datalist.add(new SimpleTextBean("添加到歌单"));
        adapter = new SimpleTextAdapter(datalist);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showSingleToast(datalist.get(position).getText());
                if (listener != null){
                    switch (position){
                        case 0:listener.onClickDeleteListener();break;
                        case 1:listener.onClickFavoriteListener();break;
                        case 2:listener.onClickAddListListener();break;
                    }
                }
                mInstance.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void setListener(OnClickEditListener listener) {
        this.listener = listener;
    }

    public interface OnClickEditListener{
        void onClickDeleteListener();
        void onClickFavoriteListener();
        void onClickAddListListener();
    }
}
