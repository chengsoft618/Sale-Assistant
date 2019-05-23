package com.shoniz.saledistributemobility.view.ordering.order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shoniz.saledistributemobility.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.ILocationChange;
import com.shoniz.saledistributemobility.view.ordering.sent.SentOrdersFragment;
import com.shoniz.saledistributemobility.view.ordering.unsent.UnsentOrdersFragment;
import com.shoniz.saledistributemobility.utility.Enums.RequestEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ferdos.s on 7/29/2017.
 */

public class RequestsListPageAdapter extends FragmentStatePagerAdapter {

    Map<BaseFragment, String> fragmentHashMap = new HashMap<BaseFragment, String> () ;

    private static int PageCount = 2;

    public RequestsListPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return PageCount;
    }

    public List<Fragment> getLocationListenerFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (BaseFragment fragment : fragmentHashMap.keySet()) {
            if(fragment instanceof ILocationChange)
                fragments.add(fragment);
        }
        return fragments;
    }

    // Returns the fragment to display for that page
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (RequestEnum.values()[position]) {
            case RequestsListPage: // Fragment # 0 - This will show FirstFragment
                return SentOrdersFragment.newInstance(0,false);
            case UnsentRequestsPage: // Fragment # 0 - This will show FirstFragment different title
                return UnsentOrdersFragment.newInstance();
//            case UnvisitedCustomerPage: // Fragment # 1 - This will show SecondFragment
//                return UnvisitedCustomerFragment__.newInstance();
//            case 3: // Fragment # 1 - This will show SecondFragment
//                return CustomerBasicPageFragment.newInstance(customerBasicModel);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (RequestEnum.values()[position]) {
            case RequestsListPage:
                return "درخواست های ارسال شده";
            case UnsentRequestsPage:
                return "درخواست های ارسال نشده";
//            case UnvisitedCustomerPage:
//                return "مشتریان ویزیت نشده";
            default: return "سایر اطلاعات";
        }
    }


}
