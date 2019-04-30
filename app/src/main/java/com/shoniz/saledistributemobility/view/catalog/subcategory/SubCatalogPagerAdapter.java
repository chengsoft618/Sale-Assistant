package com.shoniz.saledistributemobility.view.catalog.subcategory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class SubCatalogPagerAdapter extends FragmentStatePagerAdapter {
    private List<SubCategoryModel> models;
    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public SubCatalogPagerAdapter(FragmentManager fragmentManager, List<SubCategoryModel> models) {
        super(fragmentManager);
        this.models=models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    // Returns the fragment to display for that page
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return SubCategoryFragment.newInstance(models.get(position) );
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return models.get(position).SubCategoryName;
    }


}
