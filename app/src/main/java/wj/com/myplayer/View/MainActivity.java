package wj.com.myplayer.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.R;
import wj.com.myplayer.View.Fragment.OneFragment;
import wj.com.myplayer.View.Fragment.TestFragment;
import wj.com.myplayer.View.adapter.LazyFragmentPagerAdapter;

public class MainActivity extends BaseActivity {

    private TabLayout mMainTabLayout;
    private ViewPager mMainViewpager;

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private LazyFragmentPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    public void initView() {
        mMainViewpager = (ViewPager) findViewById(R.id.main_viewpager);
        mMainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);

        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        myPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mMainViewpager.setAdapter(myPagerAdapter);
        mMainTabLayout.setupWithViewPager(mMainViewpager);

    }

    @Override
    public void initData() {
        super.initData();
        mFragments.add(new TestFragment());
        mFragments.add(new OneFragment());
        mFragments.add(new TestFragment());

        mTitles.add("001");
        mTitles.add("002");
        mTitles.add("003");

        myPagerAdapter.notifyDataSetChanged();

    }
}
