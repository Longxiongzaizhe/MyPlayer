package com.hjl.module_net.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjl.module_net.R;
import com.hjl.module_net.net.vo.SearchVo;

import java.util.List;

/**
 * created by long on 2019/10/24
 */
public class SearchResultAdapter extends BaseQuickAdapter<SearchVo.DataBean.InfoBean, BaseViewHolder> {

    public SearchResultAdapter(List<SearchVo.DataBean.InfoBean> data) {
        super(R.layout.item_search_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchVo.DataBean.InfoBean item) {

        StringBuilder sb = new StringBuilder(item.getSingername());
        sb.append(" - ");
        sb.append(item.getAlbum_name());
        String author = sb.toString();
        author = author.replace("</em>","");
        author = author.replace("<em>","");
        helper.setText(R.id.search_result_name,item.getSongname());
        helper.setText(R.id.search_result_author,author);

    }
}
