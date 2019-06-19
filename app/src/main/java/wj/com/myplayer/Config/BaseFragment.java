package wj.com.myplayer.Config;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder mBind;
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(getLayoutId(),container,false);
        mBind = ButterKnife.bind(this,mView);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        getKeyData();
        initView(mView);
        initData();

    }

    protected void getKeyData(){

    }

    protected void initView(View view){

    }

    protected void initData(){

    }


    protected abstract int getLayoutId();


    @Override
    public void onDestroyView() {
        mBind.unbind();
        super.onDestroyView();
    }
}
