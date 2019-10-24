package com.hjl.module_net.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.router.RNet;
import com.hjl.module_main.ui.activity.MainActivity;
import com.hjl.module_main.ui.fragment.local.MainFragment;
import com.hjl.module_net.R;

import org.jetbrains.annotations.NotNull;

/**
 * created by long on 2019/10/23
 */
@Route(path = RNet.RNetAgent)
public class NetAgentFragment extends BaseFragment {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    private Fragment lastFragment;

    public static NetAgentFragment newInstance(){
        NetAgentFragment fragment = new NetAgentFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_agent_net;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        NetMainFragment netMainFragment = new NetMainFragment();
        transaction.add(R.id.fragment_net_agent,netMainFragment, NetMainFragment.class.getSimpleName());
        transaction.commit();
        MainActivity activity = (MainActivity) getActivity();
        activity.setNetFragmentManager(fragmentManager);

    }

    /**
     * 添加的时候，并且加入返回栈的，fragment退栈时会销毁
     */
    public void showFragment(BaseFragment fragment,@NotNull String name){
        if (!StringUtils.isEmpty(name)){
            BaseFragment fragmentByTag =(BaseFragment) fragmentManager.findFragmentByTag(name);
            if (fragmentByTag == null){
                transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragment_net_agent,fragment,name);
                fragment.notifyDataChange();
                transaction.addToBackStack(null);
                transaction.commit();
            }else {
                showFragment(name);
            }
        }else {
            transaction = fragmentManager.beginTransaction();
            transaction.show(fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    /**
     * 先添加，再show Fragment，fragment一直存在
     * @param name
     */
    public void showFragment(String name){

        BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(name);
        if (fragment != null){
            transaction = fragmentManager.beginTransaction();
            transaction.show(fragment);
            lastFragment = fragment;
            fragment.notifyDataChange();
            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            ToastUtil.showSingleToast(name + "is no found");
        }

    }
}
