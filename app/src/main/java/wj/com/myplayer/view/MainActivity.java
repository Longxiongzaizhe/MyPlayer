package wj.com.myplayer.view;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlib.baseConfig.BaseMultipleActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.constant.FlagConstant;
import wj.com.myplayer.constant.SPConstant;
import wj.com.myplayer.daoDB.MediaDaoManager;
import wj.com.myplayer.daoDB.MediaRelEntity;
import wj.com.myplayer.daoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.testPackage.TestFragment;
import wj.com.myplayer.utils.PermissionsUtiles;
import wj.com.myplayer.utils.SPUtils;
import com.example.commonlib.utils.ToastUtil;
import wj.com.myplayer.view.activity.MainMusic.MusicService;
import wj.com.myplayer.view.activity.navSetting.UserSettingActivity;
import wj.com.myplayer.view.fragment.main.FavoriteFragment;
import wj.com.myplayer.view.fragment.main.LocalFragment;
import wj.com.myplayer.view.fragment.main.MainFragment;
import wj.com.myplayer.view.fragment.OneFragment;
import wj.com.myplayer.view.fragment.PlayFragment;
import wj.com.myplayer.view.fragment.main.RecentlyFragment;
import wj.com.myplayer.view.adapter.LazyFragmentPagerAdapter;
import wj.com.myplayer.mview.NoScrollViewPager;

public class MainActivity extends BaseMultipleActivity implements View.OnClickListener {

    private TabLayout mMainTabLayout;
    private NoScrollViewPager mMainViewpager;

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private LazyFragmentPagerAdapter myPagerAdapter;
    private DrawerLayout mMainDrawerLayout;
    private NavigationView mMainNavView;
    private TextView userNameTv;
    private ImageView userIcon;
    private ImageView navBackgrounpIv;
    private View navHeadView;

    private PlayFragment playFragment;
    private MusicService.MusicBinder mBinder;

    private Intent intent;
    private MediaDaoManager manager = MainApplication.get().getMediaManager();
    private static String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private LocalFragment localFragment;
    private MainFragment mainFragment;
    private RecentlyFragment recentlyFragment;
    private FavoriteFragment favoriteFragment;

    private MediaRelManager relManager = MediaRelManager.getInstance();

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
//        List<MediaEntity> list = MediaUtils.getAllMediaList(this,"");
//        for (MediaEntity entity : list){
//            Log.e(TAG,entity.toString());
//        }
//        manager.deleteAll();
//        manager.insert(list);
    }

    @Override
    public void initTitle() {
        mTitleLeftIv.setImageResource(R.drawable.ic_menu);
        mTitleLeftIv.setOnClickListener(this);
        mTitleCenterTv.setText("音乐园");
    }

    public void initView() {
        mMainViewpager =  findViewById(R.id.main_viewpager);
        mMainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mMainViewpager.setNoScroll(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        playFragment = (PlayFragment) fragmentManager.findFragmentById(R.id.music_play_lay);

        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        myPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mMainViewpager.setAdapter(myPagerAdapter);
        mMainTabLayout.setupWithViewPager(mMainViewpager);
        mMainTabLayout.setOnClickListener(this);
        mMainViewpager.setOnClickListener(this);

        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mMainNavView = (NavigationView) findViewById(R.id.main_nav_view);
        mMainNavView.inflateHeaderView(R.layout.navigation_head_layout);
        mMainNavView.inflateMenu(R.menu.nav_menu);
        disableNavigationViewScrollbars(mMainNavView);
        navHeadView = mMainNavView.getHeaderView(0);
        userNameTv = navHeadView.findViewById(R.id.head_user_name_tv);
        userIcon = navHeadView.findViewById(R.id.nav_head_iv);
        userIcon.setOnClickListener(this);
        navBackgrounpIv = navHeadView.findViewById(R.id.head_bg_iv);
        userNameTv.setText(SPUtils.get(this,SPConstant.USER_NAME,"Sunny"));

        mMainNavView.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.nav_clock:
                    ToastUtil.showSingleToast("暂未开放定时功能哟~");
                    break;
                case R.id.nav_color:
                    ToastUtil.showSingleToast("暂未开放主题功能哟~");
                   // mMultipleStateView.showLoading();
                    break;
                case R.id.nav_exit:
                    finish();
                 //   mMultipleStateView.showEmpty();
                    break;
                case R.id.nav_setting:
                    intent.setClass(MainActivity.this, UserSettingActivity.class);
                    break;
                case R.id.nav_wifi:
                    ToastUtil.showSingleToast("暂未开放网络功能哟~");
                    break;
                case R.id.nav_about:
                    ToastUtil.showSingleToast("Author is WuJun From SZU");
                    break;
            }
            mMainDrawerLayout.closeDrawers();
            return true;

        });
        mMainDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                if (intent != null && intent.getComponent() != null){
                    startActivity(intent);
                    intent.setComponent(null);
                }

            }
            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        mMainViewpager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public void initData() {
        super.initData();
        mFragments.add(MainFragment.newInstance());
        mFragments.add(new OneFragment());
        mFragments.add(new TestFragment());
        intent = new Intent();

        initMainTitle();



        Intent startMusicIntent = new Intent(this,MusicService.class);
        bindService(startMusicIntent,connection,BIND_AUTO_CREATE) ;
        startService(startMusicIntent);

        mainFragment = MainFragment.newInstance();

    }

    private void initMainTitle() {
        mTitles.clear();
        mTitles.add("我的");
        mTitles.add("热门");
        mTitles.add("FM");
        myPagerAdapter.notifyDataSetChanged();
    }

    private void initLocalTitle(){
        mTitles.clear();
        mTitles.add("音乐");
        mTitles.add("专辑");
        mTitles.add("歌手");

        myPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bitmap iconBitmap = BitmapFactory.decodeFile(SPConstant.USER_ICON_PATH);
        Bitmap bgBitmap = BitmapFactory.decodeFile(SPConstant.USER_BG_PATH);
        userIcon.setImageBitmap(iconBitmap);
        navBackgrounpIv.setImageBitmap(bgBitmap);
        if (mBinder != null && playFragment != null){
            playFragment.setBinder(mBinder);
        }
        List<String> list = MediaDaoManager.getInstance().getAllAuthor();
        for (String da : list){
            Log.e("author",da);
        }
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
                mMultipleStateView.showContent();
                mMainDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_head_iv:
                //startActivity(TestActivity.class);
                break;
            case R.id.title_right_iv:
                if (mFragments.get(0).getClass()== RecentlyFragment.class){
                    ((RecentlyFragment)mFragments.get(0)).deleteRecentList();
                }
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

    @Override
    protected void onStop() {
        super.onStop();
        if (mMainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mMainDrawerLayout.closeDrawers();
        }

    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mTitles.get(0).equals("我的")){
                mMainViewpager.setCurrentItem(0);
                if (position != 0) {
                    ToastUtil.showSingleToast("暂未开放，敬请期待");
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;
            List<MediaRelEntity> relList = relManager.queryRecentList();
            if (relList.size() != 0){
                MediaRelEntity entity = relList.get(relList.size()-1);
                mBinder.setCurrentEntity(manager.query(entity.songId));
                mBinder.setData(manager.query(entity.songId));
            }

            playFragment.setBinder(mBinder);

            //    mBinder.play();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void setFragment(int flag){
        switch (flag){
            case FlagConstant.FRAGMENT_LOCAL:
                if (localFragment == null) localFragment = LocalFragment.newInstance(mBinder);
                mFragments.set(0, localFragment);
                myPagerAdapter.notifyDataSetChanged();
                setTitle(FlagConstant.FRAGMENT_LOCAL);
                break;
            case FlagConstant.FRAGMENT_MAIN:
                if (mainFragment == null) mainFragment = MainFragment.newInstance();
                mFragments.set(0,mainFragment);
                myPagerAdapter.notifyDataSetChanged();
                setTitle(FlagConstant.FRAGMENT_MAIN);
                break;
            case FlagConstant.FRAGMENT_RECENT:
                if (recentlyFragment == null) recentlyFragment = RecentlyFragment.newInstance(mBinder);
                mFragments.set(0,recentlyFragment);
                myPagerAdapter.notifyDataSetChanged();
                setTitle(FlagConstant.FRAGMENT_RECENT);
                break;
            case FlagConstant.FRAGMENT_FAVORITE:
                if (favoriteFragment == null) favoriteFragment = FavoriteFragment.newInstance(mBinder);
                mFragments.set(0,favoriteFragment);
                myPagerAdapter.notifyDataSetChanged();
                setTitle(FlagConstant.FRAGMENT_FAVORITE);
                break;
        }
    }

    public void setTitle(int flag){

        mTitleRightIv.setVisibility(View.GONE);
        switch (flag){
            case FlagConstant.FRAGMENT_LOCAL:
                mMainViewpager.setNoScroll(false);
                mTitleLeftIv.setImageResource(R.drawable.ic_back);
                mTitleCenterTv.setText("本地音乐");
                mTitleLeftIv.setOnClickListener(v-> setFragment(FlagConstant.FRAGMENT_MAIN));
                initLocalTitle();

                break;
            case FlagConstant.FRAGMENT_RECENT:
                mTitleLeftIv.setImageResource(R.drawable.ic_back);
                mTitleRightIv.setVisibility(View.VISIBLE);
                mTitleRightIv.setImageResource(R.drawable.icon_garbage);
                mTitleRightIv.setOnClickListener(this);
                mTitleLeftIv.setOnClickListener(v-> setFragment(FlagConstant.FRAGMENT_MAIN));
                mMainTabLayout.setVisibility(View.GONE);
                mTitleCenterTv.setText("最近播放");
                break;
            case FlagConstant.FRAGMENT_MAIN:
                mMainViewpager.setNoScroll(true);
                mTitleLeftIv.setImageResource(R.drawable.ic_menu);
                mTitleCenterTv.setText("音乐园");
                mTitleLeftIv.setOnClickListener(v->{
                    mMultipleStateView.showContent();
                    mMainDrawerLayout.openDrawer(GravityCompat.START);
                });
                mMainTabLayout.setVisibility(View.VISIBLE);
                initMainTitle();
                break;
            case FlagConstant.FRAGMENT_FAVORITE:
                mTitleLeftIv.setImageResource(R.drawable.ic_back);
                mTitleCenterTv.setText("我的收藏");
                mTitleLeftIv.setOnClickListener(v-> setFragment(FlagConstant.FRAGMENT_MAIN));
                mMainTabLayout.setVisibility(View.GONE);
                break;
        }
    }


    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            for (int i = 0; i < navigationView.getChildCount(); i++) {

                View view = navigationView.getChildAt(i);
                if (view instanceof NavigationMenuView) {
                    NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(i);
                    if (navigationMenuView != null) {
                        navigationMenuView.setVerticalScrollBarEnabled(false);
                        navigationMenuView.setOverScrollMode(navigationMenuView.OVER_SCROLL_NEVER);
                    }
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mFragments.get(0).getClass()!= MainFragment.class){
                setFragment(FlagConstant.FRAGMENT_MAIN);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
