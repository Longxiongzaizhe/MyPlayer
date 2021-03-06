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

        String author = item.getSingername() + " - " + item.getAlbum_name();
        author = author.replace("</em>","");
        author = author.replace("<em>","");
        helper.setText(
                R.id.search_result_name,
                item.getSongname()
                        .replace("</em>","")
                        .replace("<em>","")
        );
        helper.setText(R.id.search_result_author,author);

        if (item.getPay_type() != 0 && item.getPay_type() != 3){
            helper.setVisible(R.id.search_result_pay,true);
        }

    }
}
