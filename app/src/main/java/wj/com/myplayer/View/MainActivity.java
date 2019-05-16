package wj.com.myplayer.View;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.R;
import wj.com.myplayer.Utils.PermissionsUtiles;
import wj.com.myplayer.Utils.PhotoUtils;
import wj.com.myplayer.Utils.SPConstant;
import wj.com.myplayer.Utils.ToastUtil;
import wj.com.myplayer.View.Fragment.MainFragment;
import wj.com.myplayer.View.Fragment.OneFragment;
import wj.com.myplayer.View.Fragment.TestFragment;
import wj.com.myplayer.View.adapter.LazyFragmentPagerAdapter;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout mMainTabLayout;
    private ViewPager mMainViewpager;

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private LazyFragmentPagerAdapter myPagerAdapter;
    private ImageView mTitleLeftIv;
    private TextView mTitleCenterTv;
    private ImageView mTitleRightIv;
    private TextView mTitleRightTv;
    private DrawerLayout mMainDrawerLayout;
    private NavigationView mMainNavView;
    private TextView userNameTv;
    private ImageView userIcon;
    private ImageView navBackgrounpIv;
    private View navHeadView;

    private final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        PermissionsUtiles.requestPermissions(this, permissions); //请求权限

    }

    public void initView() {
        mMainViewpager = (ViewPager) findViewById(R.id.main_viewpager);
        mMainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);

        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        myPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mMainViewpager.setAdapter(myPagerAdapter);
        mMainTabLayout.setupWithViewPager(mMainViewpager);

        mTitleLeftIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleCenterTv = (TextView) findViewById(R.id.title_center_tv);
        mTitleRightIv = (ImageView) findViewById(R.id.title_right_iv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mMainTabLayout.setOnClickListener(this);
        mMainViewpager.setOnClickListener(this);
        mTitleLeftIv.setOnClickListener(this);
        mTitleCenterTv.setText("音乐园");
        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mMainNavView = (NavigationView) findViewById(R.id.main_nav_view);
        mMainNavView.inflateHeaderView(R.layout.navigation_head_layout);
        mMainNavView.inflateMenu(R.menu.nav_menu);
        navHeadView = mMainNavView.getHeaderView(0);
        userNameTv = navHeadView.findViewById(R.id.head_user_name_tv);
        userIcon = navHeadView.findViewById(R.id.nav_head_iv);
        navBackgrounpIv = navHeadView.findViewById(R.id.head_bg_iv);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.openPic(MainActivity.this,1);
            }
        });

        mMainNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_clock:
                        ToastUtil.showSingleToast("nav_clock");
                        break;
                    case R.id.nav_color:
                        ToastUtil.showSingleToast("nav_color");
                        break;
                    case R.id.nav_exit:
                        ToastUtil.showSingleToast("nav_exit");
                        break;
                    case R.id.nav_setting:
                        ToastUtil.showSingleToast("nav_setting");
                        break;
                    case R.id.nav_wifi:
                        ToastUtil.showSingleToast("nav_wifi");
                        break;
                    case R.id.nav_about:
                        ToastUtil.showSingleToast("nav_about");
                        break;
                }
                mMainDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mFragments.add(new MainFragment());
        mFragments.add(new OneFragment());
        mFragments.add(new TestFragment());

        mTitles.add("我的");
        mTitles.add("002");
        mTitles.add("003");

        myPagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_tab_layout:
                break;
            case R.id.main_viewpager:
                break;
            case R.id.title_left_iv:
                mMainDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            File file = new File(SPConstant.USER_ICON_PATH);
            File getFile = new File(data.getData().toString());
        }
    }
}
