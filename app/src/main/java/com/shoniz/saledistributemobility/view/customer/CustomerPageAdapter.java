package com.shoniz.saledistributemobility.view.customer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexFragment;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicFragment;
import com.shoniz.saledistributemobility.view.customer.info.bought.CustomerBuyFragment;
import com.shoniz.saledistributemobility.view.customer.info.cheque.CustomerChequeFragment;
import com.shoniz.saledistributemobility.view.ordering.sent.SentOrdersFragment;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CustomerPageAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 5;
    private CustomerBasicEntity customerBasicModel;
    private int personId = 0;
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public CustomerPageAdapter(FragmentManager fragmentManager, CustomerBasicEntity customerBasicModel) {
        super(fragmentManager);
        this.customerBasicModel = customerBasicModel;
    }

    public List<Fragment> getLocationListenerFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (Fragment fragment : fragmentHashMap.values()) {
            if(fragment instanceof ILocationChange)
                fragments.add(fragment);
        }
        return fragments;
    }

    public CustomerPageAdapter(FragmentManager fragmentManager, int personId) {
        super(fragmentManager);
        this.personId = personId;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        if (personId > 0)
            return 1;
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (fragmentHashMap.get(position) != null) {
            return fragmentHashMap.get(position);
        }
        switch (position) {
            case 0:
                if (personId > 0)
                    fragmentHashMap.put(position, CardIndexFragment.newInstance(personId));
                else
                    fragmentHashMap.put(position, CardIndexFragment.newInstance(customerBasicModel));
                return fragmentHashMap.get(position);
            case 1:
                fragmentHashMap.put(position, CustomerChequeFragment.newInstance(customerBasicModel));
                return fragmentHashMap.get(position);

            case 2:
                fragmentHashMap.put(position, CustomerBuyFragment.newInstance(customerBasicModel));
                return fragmentHashMap.get(position);

            case 3:
                fragmentHashMap.put(position, CustomerBasicFragment.newInstance(customerBasicModel));
                return fragmentHashMap.get(position);

            case 4:
                fragmentHashMap.put(position, SentOrdersFragment.newInstance(customerBasicModel.PersonID,true));
                return fragmentHashMap.get(position);

            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return "کارتکس";
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return "چک های مشتری";
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return "آمار خرید";
            case 3: // Fragment # 0 - This will show FirstFragment different title
                return StringHelper.GenerateMessage("{0}({1})", customerBasicModel.PersonName, "" + customerBasicModel.CustomerID);
            case 4: // Fragment # 0 - This will show FirstFragment different title
                return "درخواست ها";
            default:
                return "سایر اطلاعات";
        }
    }
}
