package com.hjl.module_main.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjl.commonlib.base.BaseMultipleActivity;
import com.hjl.commonlib.utils.PermissionsUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.bean.MainFragmentBusBean;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaRelEntity;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.service.MusicService;
import com.hjl.module_main.ui.fragment.PlayFragment;
import com.hjl.module_main.ui.fragment.main.NewMainFragment;
import com.hjl.module_main.utils.MediaUtils;
import com.hjl.module_main.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class MainActivity extends BaseMultipleActivity implements View.OnClickListener {

    public DrawerLayout mMainDrawerLayout;
    private NavigationView mMainNavView;
    private TextView userNameTv;
    private ImageView userIcon;
    private ImageView navBackgrounpIv;
    private View navHeadView;

    private PlayFragment playFragment;
    private MusicService.MusicBinder mBinder;
    private long exitTime = 0;
    private final static int SECONDS = 2000;//按下的间隔秒数
    private final static int STATUS = 0;//0 正常结束程序;1 异常关闭程序

    private Intent intent;
    private MediaDaoManager manager = MediaDaoManager.getInstance();
    private static String TAG = MainActivity.class.getSimpleName();

    private FragmentTransaction transaction;
    private FragmentManager mainFragmentManager;
    private FragmentManager netFragmentManager;
    private FragmentManager mFragmentManager;
    private MediaRelManager relManager = MediaRelManager.getInstance();


    private int currentFragment = 0;

    private final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_new;
    }

    @Override
    protected void getKeyData() {
        Message msg = Message.obtain();
        msg.what = FlagConstant.UPDATE_KEY01;
        msg.arg1 = 1;
        mInitUrlHandler.sendMessage(msg);

        PermissionsUtils.requestPermissions(this, permissions); //请求权限
    }

    @Override
    public void initTitle() {
        hideTitleLayout();
        EventBus.getDefault().register(this);

    }

    @Override
    public void initView() {
        mFragmentManager = getSupportFragmentManager();
        playFragment = (PlayFragment) mFragmentManager.findFragmentById(R.id.music_play_lay);
        transaction = mFragmentManager.beginTransaction();

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

        transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment_fl,new NewMainFragment());
        transaction.commit();

        initDrawerLayout();
    }

    private void initDrawerLayout() {
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
                Intent intent = new Intent(this,KugouWebActivity.class);
                intent.putExtra(FlagConstant.INTENT_KEY01,"https://github.com/Longxiongzaizhe/MyPlayer");
                intent.putExtra(FlagConstant.INTENT_KEY02,"MyPlayer");

                startActivity(intent);
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

        Bitmap userIconBitmap = BitmapFactory.decodeFile(SPConstant.USER_ICON_PATH);
        Bitmap userBgBitmap = BitmapFactory.decodeFile(SPConstant.USER_BG_PATH);
        userIcon.setImageBitmap(userIconBitmap);
        navBackgrounpIv.setImageBitmap(userBgBitmap);


        Intent startMusicIntent = new Intent(this,MusicService.class);
        bindService(startMusicIntent,connection,BIND_AUTO_CREATE) ;
        startService(startMusicIntent);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nav_head_iv) {
            startActivity(UserQRdetailActivity.class);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mMainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mMainDrawerLayout.closeDrawers();
        }

    }

    public void setMainFragmentManager(FragmentManager fragmentManager){
        this.mainFragmentManager = fragmentManager;
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

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Subscribe
    public void showTitle(MainFragmentBusBean busBean){

        String name = busBean.getName();

//        switch (name){
//            case FlagConstant.FRAGMENT_LOCAL:
//                mMainMineTv.setText("本地音乐");
//                mMainRightIv.setVisibility(View.GONE);
//                mMainMineTv.setVisibility(View.VISIBLE);
//                tabLayout.setVisibility(View.GONE);
//                mMainLeftIv.setImageResource(R.drawable.ic_back);
//                mMainLeftIv.setOnClickListener((v)-> newPopBackStack());
//                break;
//            case FlagConstant.FRAGMENT_RECENT:
//                mMainMineTv.setText("最近播放");
//                mMainRightIv.setVisibility(View.GONE);
//                mMainMineTv.setVisibility(View.VISIBLE);
//                tabLayout.setVisibility(View.GONE);
//                mMainLeftIv.setImageResource(R.drawable.ic_back);
//                mMainLeftIv.setOnClickListener((v)-> newPopBackStack());
//                break;
//            case FlagConstant.FRAGMENT_FAVORITE:
//                mMainMineTv.setText("我的收藏");
//                mMainRightIv.setVisibility(View.GONE);
//                mMainMineTv.setVisibility(View.VISIBLE);
//                tabLayout.setVisibility(View.GONE);
//                mMainLeftIv.setImageResource(R.drawable.ic_back);
//                mMainLeftIv.setOnClickListener((v)-> newPopBackStack());
//                break;
//        }

    }

    /**
     * 关闭抽屉布局滑动条
     * @param navigationView
     */
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            for (int i = 0; i < navigationView.getChildCount(); i++) {

                View view = navigationView.getChildAt(i);
                if (view instanceof NavigationMenuView) {
                    NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(i);
                    if (navigationMenuView != null) {
                        navigationMenuView.setVerticalScrollBarEnabled(false);
                        navigationMenuView.setOverScrollMode(View.OVER_SCROLL_NEVER);
                    }
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionsUtils.onRequestPermissionsResult(requestCode,permissions,grantResults,this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionsUtils.onActivityResult(requestCode,this,permissions);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        EventBus.getDefault().unregister(this);
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

    public void addFragmentInBackStack(Fragment fragment){

        transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment_fl,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void popBackStack(){
        //通过管理类可以获取到当前的栈内数量
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        //当栈内数量中大于0 的时候才能进行操作不然会造成索引越界 这里进行了判断，为1的时候为主界面不回退
        if (backStackEntryCount> 0){
            //退栈
            mFragmentManager.popBackStackImmediate();
            //获取一下栈内的数量
            backStackEntryCount = mFragmentManager.getBackStackEntryCount();

            if (backStackEntryCount == 0){ // 说明在首页

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

    private void newPopBackStack(){

        FragmentManager fragmentManager = null;

        if (currentFragment == 0){
            fragmentManager = mainFragmentManager;
        }else if (currentFragment == 1){
            fragmentManager = netFragmentManager;
        }


        //通过管理类可以获取到当前的栈内数量
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        //当栈内数量中大于0 的时候才能进行操作不然会造成索引越界 这里进行了判断，为1的时候为主界面不回退
        if (backStackEntryCount> 0){
            //退栈
            fragmentManager.popBackStackImmediate();
            //获取一下栈内的数量
            backStackEntryCount = fragmentManager.getBackStackEntryCount();

            if (backStackEntryCount == 0){ // 说明在首页
//                BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(MainFragment.class.getSimpleName());
//                if (fragment != null) {
//                    // 通知刷新数据
//                    fragment.notifyDataChange();
//                }

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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    public void setNetFragmentManager(FragmentManager netFragmentManager) {
        this.netFragmentManager = netFragmentManager;
    }
}
