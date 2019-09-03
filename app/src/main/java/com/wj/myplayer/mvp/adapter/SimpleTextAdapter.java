package com.wj.myplayer.mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.wj.myplayer.bean.SimpleTextBean;
import com.wj.myplayer.R;

public class SimpleTextAdapter extends BaseQuickAdapter<SimpleTextBean, BaseViewHolder> {

    public SimpleTextAdapter( List<SimpleTextBean> data) {
        super(R.layout.item_base_tv, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleTextBean item) {
        helper.setText(R.id.item_tv,item.getText());
    }
}
