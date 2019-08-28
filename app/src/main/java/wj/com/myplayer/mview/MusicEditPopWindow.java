package wj.com.myplayer.mview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.bean.SimpleTextBean;
import wj.com.myplayer.daodb.MediaRelManager;
import wj.com.myplayer.R;
import com.example.commonlib.utils.DensityUtil;
import wj.com.myplayer.mvp.adapter.SimpleTextAdapter;

public class MusicEditPopWindow extends BasePopWindow {

    private RecyclerView recyclerView;
    private List<SimpleTextBean> datalist;
    private SimpleTextAdapter adapter;
    private MediaRelManager relManager = MediaRelManager.getInstance();
    private OnClickEditListener listener;
    private EditMusicMode editMode;

    public enum EditMusicMode{
        LOCAL,FAVORITE,NORMAL
    }



    public MusicEditPopWindow(Context c,EditMusicMode mode) {
        super(c, R.layout.layout_music_edit, DensityUtil.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT);

        editMode = mode;
        initData();
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
        if (editMode == EditMusicMode.LOCAL){
            datalist.add(new SimpleTextBean("收藏"));
            datalist.add(new SimpleTextBean("添加到歌单"));
        }else if (editMode == EditMusicMode.FAVORITE){
            datalist.add(new SimpleTextBean("移除收藏"));
            datalist.add(new SimpleTextBean("添加到歌单"));
        }

        adapter = new SimpleTextAdapter(datalist);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {

            if (listener != null){
                listener.onClickListener(datalist.get(position).getText());
            }
            mInstance.dismiss();
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void addMenu(String name){
        datalist.add(new SimpleTextBean(name));
        adapter.notifyDataSetChanged();
    }

    public void setOnClickEditListener(OnClickEditListener listener) {
        this.listener = listener;
    }

    public interface OnClickEditListener{
        void onClickListener(String name);
    }
}
