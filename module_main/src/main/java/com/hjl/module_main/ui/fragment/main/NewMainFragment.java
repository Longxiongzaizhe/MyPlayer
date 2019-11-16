package com.hjl.module_main.ui.fragment.main;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjl.commonlib.adapter.LazyFragmentPagerAdapter;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.router.RNet;
import com.hjl.module_main.ui.activity.MainActivity;
import com.hjl.module_main.ui.fragment.my.MainMyMvpFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

/**
 * created by long on 2019/11/13
 * 此Fragment 只存放主界面Title 和 ViewPager
 */
public class NewMainFragment extends BaseFragment implements View.OnClickListener {

    // title
    private TextView mMainMineTv;
    private ImageView mMainLeftIv;
    private ImageView mMainRightIv;
    private List<String> tabTitleList;
    private List<Fragment> fragments;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MainMyMvpFragment mainFragment;
    private LazyFragmentPagerAdapter pagerAdapter;
    private BaseFragment netMainFragment;
    private MainActivity mainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {
        viewPager = view.findViewById(R.id.main_viewpager);
        tabLayout = view.findViewById(R.id.main_tab_layout);
        mMainMineTv = view.findViewById(R.id.main_center_tv);
        mMainLeftIv = view.findViewById(R.id.main_left_iv);
        mMainRightIv = view.findViewById(R.id.main_right_iv);
        mMainRightIv.setImageResource(R.drawable.icon_scan_code);
        mMainRightIv.setOnClickListener(this);

        mMainLeftIv.setImageResource(R.drawable.ic_menu);
        mMainLeftIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tabTitleList = new ArrayList<>();
        fragments = new ArrayList<>();

        mainFragment = new MainMyMvpFragment();
        netMainFragment = (BaseFragment) ARouter.getInstance().build(RNet.RNetMain).navigation();
        fragments.add(mainFragment);
        fragments.add(netMainFragment);
        tabTitleList.add("我");
        tabTitleList.add("听");
        pagerAdapter = new LazyFragmentPagerAdapter(getFragmentManager(),fragments,tabTitleList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        initTabLayout();

        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_left_iv) {
            mainActivity.mMainDrawerLayout.openDrawer(GravityCompat.START);
        }else if (v.getId() == R.id.main_right_iv){
            QrConfig qrConfig = new QrConfig.Builder()
                    .setTitleText("扫描二维码")
                    .setAutoZoom(false) // 自动缩放
                    .setLooperScan(false) // 连续扫描
                    .setFingerZoom(true) // 手动缩放
                    .setShowVibrator(true)
                    .setTitleBackgroudColor(getResources().getColor(R.color.common_base_theme_blue))
                    .setCornerColor(Color.WHITE)//设置扫描框颜色
                    .setLineColor(Color.WHITE) //设置扫描线颜色
                    .setShowLight(true)
                    .create();
            QrManager.getInstance().init(qrConfig).startScan(mainActivity, result -> {
                ToastUtil.showSingleToast(result.getContent());
            });
        }
    }

    private void initTabLayout() {

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    ((TextView) view).setTextSize(22);
                    ((TextView) view).getPaint().setFakeBoldText(true);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white_EAEAEA));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    ((TextView) view).setTextSize(18);
                    ((TextView) view).getPaint().setFakeBoldText(false);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.common_white));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setSelected(true);
    }

    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
        TextView textView =  view.findViewById(R.id.tab_item_tv);
        textView.setText(tabTitleList.get(currentPosition));
        if (currentPosition == 0){
            textView.setTextSize(22);
            textView.getPaint().setFakeBoldText(true);
            textView.setTextColor(getResources().getColor(R.color.white_EAEAEA));
        }
        return view;
    }


}
