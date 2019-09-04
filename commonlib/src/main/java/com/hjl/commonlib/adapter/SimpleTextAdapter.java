package com.hjl.commonlib.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjl.commonlib.R;
import com.hjl.commonlib.bean.SimpleTextBean;

import java.util.List;

public class SimpleTextAdapter extends BaseQuickAdapter<SimpleTextBean, BaseViewHolder> {

    public SimpleTextAdapter( List<SimpleTextBean> data) {
        super(R.layout.common_item_base_tv, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleTextBean item) {
        helper.setText(R.id.item_tv,item.getText());
    }
}
