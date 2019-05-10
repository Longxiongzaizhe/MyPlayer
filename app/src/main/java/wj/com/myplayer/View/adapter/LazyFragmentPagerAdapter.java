package wj.com.myplayer.View.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Project Name Estate
 * <p> Created by xiaowu on 2018/4/19 0019.</p>
 */

public class LazyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private List<String> mTitles;

    public LazyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public LazyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.list = list;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}
