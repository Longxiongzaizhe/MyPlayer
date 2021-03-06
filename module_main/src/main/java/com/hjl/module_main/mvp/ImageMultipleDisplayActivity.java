package com.hjl.module_main.mvp;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hjl.commonlib.base.BaseMultipleActivity;

import java.util.ArrayList;
import java.util.List;

import com.hjl.commonlib.utils.PhotoUtils;
import com.hjl.module_main.R;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter;

public class ImageMultipleDisplayActivity extends BaseMultipleActivity implements View.OnClickListener {

    private static final String TAG = "ImageMultipleDisplayActivity";
    private ViewPager mIvDisplayVp;
    private LazyFragmentPagerAdapter pagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mImgs; // 网络url
    private List<Uri> mUris; // 本地Uri
    private int type; // 1 网络url 2 本地Uri 3 Bitmap
    private int position;
    private ImageView mDownLoadIv;
    private String displayUrl = "";
    private int currentPosition;
    private int isShowDownload; // 0 false 1 true

    public static final int NETWORK_URL = 1;
    public static final int LOCAL_URI = 2;
    private FrameLayout mMultiplyDisplayLl;

    public static final String IMG_TRANSITION = "IMG_TRANSITION";

    @Override
    public void initTitle() {
        mTitleCenterTv.setText("图片预览");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_multiple_display;
    }

    @Override
    protected void getKeyData() {
        type = getIntent().getIntExtra(FlagConstant.INTENT_KEY01, 0);

        position = getIntent().getIntExtra(FlagConstant.INTENT_KEY03, 0);
        isShowDownload = getIntent().getIntExtra(FlagConstant.INTENT_KEY04, 0);

        switch (type) {
            case NETWORK_URL:
                mImgs = getIntent().getStringArrayListExtra(FlagConstant.INTENT_KEY02);
                if (position < 0 || position > mImgs.size() - 1) position = 0;
                displayUrl = mImgs.get(position);
                break;
            case LOCAL_URI:
                mUris = getIntent().getParcelableArrayListExtra(FlagConstant.INTENT_KEY02);
                if (position < 0 || position > mUris.size() - 1) position = 0;
                displayUrl = PhotoUtils.getPath(ImageMultipleDisplayActivity.this, mUris.get(position));
                break;
        }
    }

    @Override
    protected void initView() {
        mIvDisplayVp = (ViewPager) findViewById(R.id.iv_display_vp);
        pagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mIvDisplayVp.setAdapter(pagerAdapter);
        mIvDisplayVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (type == LOCAL_URI) {
                  //  displayUrl = ImagePicker.getPath(ImageMultipleDisplayActivity.this, mUris.get(position));
                } else if (type == NETWORK_URL) {
                    displayUrl = mImgs.get(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mDownLoadIv = (ImageView) findViewById(R.id.down_load_iv);
        mDownLoadIv.setOnClickListener(this);
        if (isShowDownload == 1) {
            mDownLoadIv.setVisibility(View.VISIBLE);
        } else {
            mDownLoadIv.setVisibility(View.GONE);
        }
        mMultiplyDisplayLl = findViewById(R.id.multiply_display_ll);
        mMultiplyDisplayLl.setBackgroundColor(getResources().getColor(R.color.black));
        initTransition();
    }

    public void initTransition(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            //ViewCompat.setTransitionName(mIvDisplayVp, IMG_TRANSITION);
            mIvDisplayVp.setTransitionName(IMG_TRANSITION);
            startPostponedEnterTransition();
        }
    }



    @Override
    protected void initData() {

        switch (type) {
            case NETWORK_URL:
                if (mImgs != null) {
                    for (String url : mImgs) {
                        if (!StringUtils.isEmpty(url)) {
                            mFragments.add(ImageDisplayFragment.getInstance(NETWORK_URL, url, null));
                        }
                    }
                    pagerAdapter.notifyDataSetChanged();
                    if (mFragments.size() >= position) {
                        mIvDisplayVp.setCurrentItem(position);
                    }
                }
                break;
            case LOCAL_URI:
                if (mUris != null) {
                    for (Uri uri : mUris) {
                        if (uri != null) {
                            mFragments.add(ImageDisplayFragment.getInstance(LOCAL_URI, null, uri));
                        }
                    }
                    pagerAdapter.notifyDataSetChanged();
                    if (mFragments.size() >= position) {
                        mIvDisplayVp.setCurrentItem(position);
                    }
                }
                break;
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.down_load_iv) {
            if (StringUtils.isEmpty(displayUrl)) {
                return;
            }
            if (!displayUrl.startsWith("http")) {
                ToastUtil.showSingleToast("请前往 " + displayUrl + " 查看");
                return;
            }
            ToastUtil.showSingleToast("正在保存,请稍后...");
        }
    }
}
