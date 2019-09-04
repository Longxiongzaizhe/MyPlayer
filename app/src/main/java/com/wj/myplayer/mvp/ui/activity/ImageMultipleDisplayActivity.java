package com.wj.myplayer.mvp.ui.activity;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hjl.commonlib.base.BaseMultipleActivity;

import java.util.ArrayList;
import java.util.List;

import com.hjl.module_main.constant.FlagConstant;
import com.wj.myplayer.R;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter;

public class ImageMultipleDisplayActivity extends BaseMultipleActivity implements View.OnClickListener {

    private static final String TAG = "ImageMultipleDisplayActivity";
    private ViewPager mIvDisplayVp;
    private LazyFragmentPagerAdapter pagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mImgs;
    private List<Uri> mUris;
    private int type; // 1 网络url 2 本地Uri 3 Bitmap
    private int position;
    private ImageView mDownLoadIv;
    private String displayUrl = "";
    private int currentPosition;
    private int isShowDownload; // 0 false 1 true

    public static final int NETWORK_URL = 1;
    public static final int LOCAL_URI = 2;
    private FrameLayout mMultiplyDisplayLl;

    @Override
    public void initTitle() {
        mTitleCenterTv.setText("图片预览");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_multiple_display);
        getKeyData();
        initView();
        initData();
    }

    @Override
    protected void getKeyData() {
        type = getIntent().getIntExtra(FlagConstant.INTENT_KEY01, 0);

        position = getIntent().getIntExtra(FlagConstant.INTENT_KEY03, 0);
        isShowDownload = getIntent().getIntExtra(FlagConstant.INTENT_KEY04, 0);

        switch (type) {
            case NETWORK_URL:
                mImgs = getIntent().getStringArrayListExtra(FlagConstant.INTENT_KEY02);
                displayUrl = mImgs.get(position);
                break;
            case LOCAL_URI:
                mUris = getIntent().getParcelableArrayListExtra(FlagConstant.INTENT_KEY02);
              //  displayUrl = ImagePicker.getPath(ImageMultipleDisplayActivity.this, mUris.get(position));
                break;
        }
    }

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
        switch (v.getId()) {
            default:
                break;
            case R.id.down_load_iv:

                if (StringUtils.isEmpty(displayUrl)){
                    return;
                }

                if (!displayUrl.startsWith("http")) {
                    ToastUtil.showSingleToast("请前往 " + displayUrl + " 查看");
                    return;
                }

                ToastUtil.showSingleToast("正在保存,请稍后...");
//                if (!StringUtils.isEmpty(displayUrl)) {
//                    new Thread(() -> {
//                        final Bitmap bt = FileUtil.getBitmap(displayUrl);
//                      //  final Bitmap bt = BitmapFactory.decodeFile(displayUrl);
//                        MainApplication.runUiThread(() -> {
//                            MobclickAgent.onEvent(this, UmengEventConstant.C_MONITOR_PICTURE_DOWNLOAD);
//                            String fileName = "snapshot-" + System.currentTimeMillis() + ".png";
//                            FileUtils.saveBitmap(bt, fileName, 100, FileUtils.SAVEDIR_IMAGE);
//                            ToastUtil.show(this, "下载成功!请前往" + FileUtils.SAVEDIR_IMAGE + File.separator + fileName + "查看");
//                        });
//
//                    }).start();
//                }
                break;
        }
    }
}
