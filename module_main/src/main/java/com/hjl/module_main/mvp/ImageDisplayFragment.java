package com.hjl.module_main.mvp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import com.bumptech.glide.Glide;
import com.hjl.commonlib.base.BaseFragment;
import com.github.chrisbanes.photoview.PhotoView;
import java.io.IOException;
import com.hjl.module_main.R;
import com.hjl.module_main.constant.FlagConstant;


public class ImageDisplayFragment extends BaseFragment implements View.OnClickListener {


    private PhotoView mPhotoView;

    private String displayUrl;
    private Bitmap displayBitmap;
    private Uri displayUri;
    private int type; // 1 网络url 2 本地url 3 Bitmap

    public static ImageDisplayFragment getInstance(Bundle bundle){
        ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
        imageDisplayFragment.setArguments(bundle);
        return imageDisplayFragment;
    }

    public static ImageDisplayFragment getInstance(int type,String url,Uri uri){
        Bundle bundle = new Bundle();
        bundle.putString(FlagConstant.INTENT_KEY01,url);
        bundle.putInt(FlagConstant.INTENT_KEY02,type);
        bundle.putParcelable(FlagConstant.INTENT_KEY03,uri);

        return getInstance(bundle);
    }

    public static ImageDisplayFragment getInstance(Bitmap bitmap,int type){
        ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
        Bundle bundle = new Bundle();
        imageDisplayFragment.displayBitmap = bitmap;
        bundle.putInt(FlagConstant.INTENT_KEY02,type);
        imageDisplayFragment.setArguments(bundle);
        return imageDisplayFragment;
    }

    @Override
    protected void getKeyData() {
        Bundle bundle = getArguments();
        displayUrl = bundle.getString(FlagConstant.INTENT_KEY01);
        type = bundle.getInt(FlagConstant.INTENT_KEY02);
        displayUri = bundle.getParcelable(FlagConstant.INTENT_KEY03);

    }

    @Override
    protected void initView(View view) {

        mPhotoView = view.findViewById(R.id.img_iv);
        mPhotoView.setOnClickListener(this);

        switch (type){
            case 1:
                Glide.with(this).load(displayUrl).placeholder(R.drawable.default_img).into(mPhotoView);
                break;
            case 2:
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), displayUri);
                    mPhotoView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_iv_display;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_iv) {
            getActivity().finish();
            getActivity().overridePendingTransition(0, R.anim.activity_out);
        }
    }
}
