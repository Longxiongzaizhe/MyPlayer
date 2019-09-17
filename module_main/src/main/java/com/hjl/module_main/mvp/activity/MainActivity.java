package com.hjl.module_main.mvp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.base.BaseMultipleActivity;
import com.hjl.commonlib.utils.PermissionsUtiles;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaRelEntity;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.mvp.fragment.FavoriteFragment;
import com.hjl.module_main.mvp.fragment.MainFragment;
import com.hjl.module_main.mvp.fragment.MainLocalFragment;
import com.hjl.module_main.mvp.fragment.MusicService;
import com.hjl.module_main.mvp.fragment.PlayFragment;
import com.hjl.module_main.mvp.fragment.RecentlyFragment;
import com.hjl.module_main.utils.MediaUtils;
import com.hjl.module_main.utils.SPUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends BaseMultipleActivity implements View.OnClickListener {

    private DrawerLayout mMainDrawerLayout;
    private NavigationView mMainNavView;
    private TextView userNameTv;
    private ImageView userIcon;
    private ImageView navBackgrounpIv;
    private View navHeadView;

    private PlayFragment playFragment;
    private MusicService.MusicBinder mBinder;
    private FrameLayout mFragmentFrame;
    private Fragment lastFragment;
    private long exitTime = 0;
    private final static int SECONDS = 2000;//按下的间隔秒数
    private final static int STATUS = 0;//0 正常结束程序;1 异常关闭程序

    private Intent intent;
    private MediaDaoManager manager = MediaDaoManager.getInstance();
    private static String TAG = MainActivity.class.getSimpleName();

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MediaRelManager relManager = MediaRelManager.getInstance();

    // title
    private TextView mMainCenterTv;
    private ImageView mMainLeftIv;
    private ImageView mMainRightIv;

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
        Message msg = Message.obtain();
        msg.what = FlagConstant.UPDATE_KEY01;
        msg.arg1 = 1;
        mInitUrlHandler.sendMessage(msg);

        PermissionsUtiles.requestPermissions(this, permissions); //请求权限
//        List<MediaEntity> list = MediaUtils.getAllMediaList(this,"");
//        for (MediaEntity entity : list){
//            Log.e(TAG,entity.toString());
//        }
//        relManager.deleteAll();
//        relManager.insert(list);
    }

    @Override
    public void initTitle() {
        mTitleCl.setVisibility(View.GONE);

    }

    public void initView() {
        mFragmentFrame = findViewById(R.id.fragment_frame);
        FragmentManager fragmentManager = getSupportFragmentManager();
        playFragment = (PlayFragment) fragmentManager.findFragmentById(R.id.music_play_lay);
        transaction = getSupportFragmentManager().beginTransaction();
        MainFragment mainFragment = MainFragment.newInstance();
        transaction.add(R.id.fragment_frame,mainFragment,MainFragment.class.getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();


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

        mMainCenterTv = findViewById(R.id.main_center_tv);
        mMainLeftIv = findViewById(R.id.main_left_iv);
        mMainRightIv = findViewById(R.id.main_right_iv);

        mMainLeftIv.setImageResource(R.drawable.ic_menu);
        mMainLeftIv.setOnClickListener(this);
        mMainCenterTv.setText("音乐园");

        mMainNavView.setNavigationItemSelectedListener(menuItem -> {

            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_clock) {
                ToastUtil.showSingleToast("暂未开放定时功能哟~");
            } else if (itemId == R.id.nav_color) {
                ToastUtil.showSingleToast("暂未开放主题功能哟~");
                // mMultipleStateView.showLoading();
            } else if (itemId == R.id.nav_exit) {
                finish();
                //   mMultipleStateView.showEmpty();
            } else if (itemId == R.id.nav_setting) {
                intent.setClass(MainActivity.this, UserSettingActivity.class);
            } else if (itemId == R.id.nav_wifi) {
                ToastUtil.showSingleToast("暂未开放网络功能哟~");
            } else if (itemId == R.id.nav_about) {
                ToastUtil.showSingleToast("Author is WuJun From SZU");
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
    }

    @Override
    public void initData() {
        super.initData();
        intent = new Intent();


        Intent startMusicIntent = new Intent(this,MusicService.class);
        bindService(startMusicIntent,connection,BIND_AUTO_CREATE) ;
        startService(startMusicIntent);

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

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_left_iv) {//mMultipleStateView.showContent();
            mMainDrawerLayout.openDrawer(GravityCompat.START);
        } else if (id == R.id.nav_head_iv) {//startActivity(TestActivity.class);
        } else if (id == R.id.main_right_iv) {
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
            MainLocalFragment mainLocalFragment = MainLocalFragment.Companion.newInstance(mBinder);
            FavoriteFragment favoriteFragment = FavoriteFragment.newInstance(mBinder);
            RecentlyFragment recentlyFragment = RecentlyFragment.newInstance(mBinder);
           // MusicListFragment listFragment = MusicListFragment.Companion.newInstance(mBinder);

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_frame,mainLocalFragment,MainLocalFragment.class.getSimpleName());
            transaction.add(R.id.fragment_frame,favoriteFragment,FavoriteFragment.class.getSimpleName());
            transaction.add(R.id.fragment_frame,recentlyFragment,RecentlyFragment.class.getSimpleName());
           // transaction.add(R.id.fragment_frame,listFragment,MusicListFragment.class.getSimpleName());
            transaction.hide(mainLocalFragment);
            transaction.hide(favoriteFragment);
            transaction.hide(recentlyFragment);
          //  transaction.hide(listFragment);
            transaction.commit();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void showFragment(String name){

        BaseFragment fragment =(BaseFragment) getSupportFragmentManager().findFragmentByTag(name);
        if (fragment != null){
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.show(fragment);
            if (lastFragment != null){
                transaction.hide(lastFragment);
            }
            lastFragment = fragment;
            fragment.notifyDataChange();
            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            ToastUtil.showSingleToast(name + "is no found");
        }
        switch (name){
            case FlagConstant.FRAGMENT_LOCAL:
                mMainCenterTv.setText("本地音乐");
                mMainLeftIv.setImageResource(R.drawable.ic_back);
                mMainLeftIv.setOnClickListener((v)-> popBackStack());
                break;
            case FlagConstant.FRAGMENT_RECENT:
                mMainCenterTv.setText("最近播放");
                mMainLeftIv.setImageResource(R.drawable.ic_back);
                mMainLeftIv.setOnClickListener((v)-> popBackStack());
                break;
            case FlagConstant.FRAGMENT_FAVORITE:
                mMainCenterTv.setText("我的收藏");
                mMainLeftIv.setImageResource(R.drawable.ic_back);
                mMainLeftIv.setOnClickListener((v)-> popBackStack());
                break;
        }

    }

    public void showFragment(String name,Object... object){

        if (object.length == 0){
            showFragment(name);
            return;
        }

//        if (name.equals(FlagConstant.FRAGMENT_LIST)){
//            MusicListFragment fragment =(MusicListFragment) getSupportFragmentManager().findFragmentByTag(name);
//            if (fragment != null){
//                fragment.notifyDataChange((long)object[0]);
//                transaction = getSupportFragmentManager().beginTransaction();
//                transaction.show(fragment);
//                if (lastFragment != null){
//                    transaction.hide(lastFragment);
//                }
//                lastFragment = fragment;
//                fragment.notifyDataChange();
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }else {
//                ToastUtil.showSingleToast(name + "is no found");
//            }
//        }

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

        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        popBackStack();
    }

    private void popBackStack() {
        //通过管理类可以获取到当前的栈内数量
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        //当栈内数量中大于0 的时候才能进行操作不然会造成索引越界
        if (backStackEntryCount>1){
            //退栈
            fragmentManager.popBackStackImmediate();
            //获取一下栈内的数量
            backStackEntryCount = fragmentManager.getBackStackEntryCount();
            //二次判断
            if (backStackEntryCount>0){
                //退栈完 元素 -1 就是当前的栈顶Fragemn
                FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(backStackEntryCount - 1);
                //重新过去name
                String name = backStackEntryAt.getName();
                Log.d("BACK",name + "");
                //重新获取 Fragment
                BaseFragment fragmentByTag = (BaseFragment) fragmentManager.findFragmentByTag(name);
                //重新赋值
                lastFragment =fragmentByTag;

                if (backStackEntryCount == 1){
                    BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(MainFragment.class.getSimpleName());
                    if (fragment != null) {
                        fragment.notifyDataChange();
                    }
                    mMainCenterTv.setText("音乐园");
                    mMainLeftIv.setImageResource(R.drawable.ic_menu);
                    mMainLeftIv.setOnClickListener((v)-> mMainDrawerLayout.openDrawer(GravityCompat.START));
                }

            }

        } else {
            if ((System.currentTimeMillis() - exitTime) > SECONDS) {
                ToastUtil.show(this,"再按一下退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public static Handler mInitUrlHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FlagConstant.UPDATE_KEY01){
                int index = msg.arg1;
                MediaUtils.initMusicCover(5,index++);
                if (index*5 < MediaDaoManager.getInstance().loadAll().size()){
                    Message sendMsg = Message.obtain();
                    sendMsg.what = FlagConstant.UPDATE_KEY01;
                    sendMsg.arg1 = index;
                    Log.i("initMusicUrl","index is:" + index);
                    mInitUrlHandler.sendMessageDelayed(sendMsg,10000);
                }else {
                    MediaUtils.initAlbumCover();
                }
            }
        }
    };
}
