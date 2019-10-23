package com.hjl.module_net.ui;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_net.R;
import com.hjl.module_net.net.vo.AssociativeWordVo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * created by long on 2019/10/23
 */
public class SearchTipAdapter extends BaseQuickAdapter<AssociativeWordVo.DataBean, BaseViewHolder> {

    private String keyword;
    private SpannableString spannableString;


    public SearchTipAdapter() {
        super(R.layout.item_search_tip);
    }

    @Override
    protected void convert(BaseViewHolder helper, AssociativeWordVo.DataBean item) {

        spannableString = new SpannableString(item.getKeyword());
        if (!StringUtils.isEmpty(keyword)){
            Pattern pattern = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(spannableString);

            while (matcher.find()){
                int start = matcher.start();
                int end = matcher.end();
                spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.common_base_theme_blue)),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        helper.setText(R.id.item_search_tip_tv,spannableString);


    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
