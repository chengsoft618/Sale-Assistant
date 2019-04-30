package com.shoniz.saledistributemobility.view.base.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.shoniz.saledistributemobility.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PagerAdapter extends FragmentStatePagerAdapter
        implements ViewPager.OnPageChangeListener {

    List<BaseFragment> fragments = new ArrayList<>();
    Context context;

    @Inject
    public PagerAdapter(FragmentManager fm, Context context, BaseFragment... fragments) {
        super(fm);
        this.context = context;
        for (BaseFragment fragment : fragments) {
            this.fragments.add(fragment);
        }
//        init();
    }

//    boolean isInit = false;
//
//    void init() {
//        if (!isInit)
//            for (BaseFragment fragment : fragments) {
//                if (fragment.getViewModel() instanceof IPagerDataChange)
//                    ((IPagerDataChange) fragment.getViewModel()).registerDataChangeListener(this);
//                isInit = true;
//            }
//    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(fragments.get(position).getFragmentTitle());

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getActionMode() != null) {
                fragments.get(i).getActionMode().finish();
            }
            fragments.get(i).setIsActive(false);
        }

        fragments.get(position).showActionMode();
        fragments.get(position).setIsActive(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


//    @Override
//    public void onUpdated() {
//        for (BaseFragment fragment : fragments) {
//            if (fragment.getViewModel() instanceof IPagerDataChange) {
//                ((IPagerDataChange) fragment.getViewModel()).onUpdate();
//            }
//        }
//    }
}
