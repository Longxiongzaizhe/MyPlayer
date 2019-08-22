package com.example.commonlib.BaseConfig;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder mBind;
    protected View mContentView;
    protected View mView;
    protected MultipleStatusView mMultipleStatusView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContentView = inflater.inflate(getLayoutId(),container,false);
        mView = inflater.inflate(R.layout.fragment_base_multiple,container,false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(lp);

        if (mView != null){
            mMultipleStatusView = mView.findViewById(R.id.multiple_state_view);
            mMultipleStatusView.addView(mContentView);
            mMultipleStatusView.setContentView(mContentView);
            mMultipleStatusView.showContent();
            mBind = ButterKnife.bind(this, mView);
            return mView;
        }else {
            mBind = ButterKnife.bind(this, mContentView);
            return mContentView;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getKeyData();
        initView(mContentView);
        initData();
    }

    protected void getKeyData(){

    }




    protected abstract int getLayoutId();
    protected abstract void initView(View view);
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        mBind.unbind();
        super.onDestroyView();
    }
}
