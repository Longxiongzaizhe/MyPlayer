package com.hjl.module_net.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjl.module_net.KugouUtils;
import com.hjl.module_net.R;
import com.hjl.module_net.net.vo.AllSingerVo;

import java.util.List;

/**
 * created by long on 2019/11/12
 */
public class SingerListAdapter extends BaseQuickAdapter<AllSingerVo.DataBean.InfoBean, BaseViewHolder> {

    public SingerListAdapter(List<AllSingerVo.DataBean.InfoBean> data) {
        super(R.layout.item_singer, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllSingerVo.DataBean.InfoBean item) {
        helper.setText(R.id.item_singer_name,item.getSingername());


        Glide.with(mContext).load(item.getImgurl().replace("{size}","480")).into((ImageView) helper.getView(R.id.item_singer_iv));
        StringBuilder sb = new StringBuilder("热度:");
        sb.append(KugouUtils.getCountString(item.getHeat()));
        sb.append("万,粉丝:");
        sb.append(KugouUtils.getCountString(item.getFanscount()));
        sb.append("万");
        helper.setText(R.id.item_singer_info,sb.toString());

        int position = helper.getLayoutPosition() + 1;
        if (position == 1){
            helper.setTextColor(R.id.item_singer_rank,mContext.getResources().getColor(R.color.common_white));
            helper.setBackgroundRes(R.id.item_singer_rank,R.drawable.bg_circle_yellow);
        } else if (position == 2){
            helper.setTextColor(R.id.item_singer_rank,mContext.getResources().getColor(R.color.common_white));
            helper.setBackgroundRes(R.id.item_singer_rank,R.drawable.bg_circle_orange);
        } else if (position == 3){
            helper.setTextColor(R.id.item_singer_rank,mContext.getResources().getColor(R.color.common_white));
            helper.setBackgroundRes(R.id.item_singer_rank,R.drawable.bg_circle_blue);
        } else {
            helper.setTextColor(R.id.item_singer_rank,R.color.black);
            helper.setBackgroundColor(R.id.item_singer_rank,mContext.getResources().getColor(R.color.common_white));
        }

        helper.setText(R.id.item_singer_rank,String.valueOf(position));

    }
}
