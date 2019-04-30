package com.shoniz.saledistributemobility.view.catalog;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.catalog.CategoryAdapter;
import com.shoniz.saledistributemobility.catalog.CategoryModel;
import com.shoniz.saledistributemobility.catalog.ProfileCategoryModel;
import com.shoniz.saledistributemobility.catalog.ResourceBusiness;
import com.shoniz.saledistributemobility.catalog.ResourceData;
import com.shoniz.saledistributemobility.catalog.ShortcutSmallAdapter;
import com.shoniz.saledistributemobility.databinding.FragmentCatalogBinding;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCatalogPagerAdapter;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryModel;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.ChangeSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class CatalogFragment extends BaseFragment<FragmentCatalogBinding, CatalogFragmentViewModel> {
    RecyclerView categoryRecyclerView;
    RecyclerView smallShortcutRecyclerView;
    ViewPager viewPagerSubCategory;
    List<SubCategoryModel> subCategories = new LinkedList<>();
    List<CategoryModel> categoryModelList = new ArrayList<>();
    List<SubCategoryDetailModel> smallShortcutModels = new ArrayList<>();
    SubCatalogPagerAdapter subCategoryViewPagerAdapter;
    CategoryAdapter categoryAdapter;
    ShortcutSmallAdapter smallShortcutAdapter;
    SharedCustomerViewModel sharedCustomerActivityViewModel;
    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManagerCategory;

    @Inject
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory factoryShared;


    @Inject
    @Named("CatalogFragmentViewModel")
    ViewModelProvider.Factory factory;
    ShortcutSmallAdapter.OnItemSelectedListener smallShortcutListener = new ShortcutSmallAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int pos, SubCategoryDetailModel model) {
            try {
                sharedCustomerActivityViewModel.setShortcutSender(String.valueOf(model.Shortcut), ChangeSender.SmallShortcut);
            } catch (IOException e) {
                handleError(new UncaughtException(commonPackage, e));
            }
//            model.isSelected=true;
//            int shortcut = model.Shortcut;
//            RefreshSmallRecycleShortcut(shortcut);
//            Fragment fragment = subCategoryViewPagerAdapter.getCurrentFragment();
//            if (fragment != null) {
//                sharedCustomerActivityViewModel.pos.setValue(pos + 1);
//                sharedCustomerActivityViewModel.shortcutString.setValue(""+shortcut);
//            }
        }
    };
    CategoryAdapter.OnItemSelectedListener categoryListener = new CategoryAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(CategoryModel model) {
            try {
                subCategories = ResourceData.getSubCategory(getBaseActivity(), model.CategoryId);
                sharedCustomerActivityViewModel.setSubcategoryId(subCategories.get(0).SubCategoryId);
                sharedCustomerActivityViewModel.setCategoryId(model.CategoryId);
                fillSmallShortcut();
                InitSubCategoryViewPager();
            } catch (Exception e) {
                HandleException.ShowException(getBaseActivity(), e);
            }
        }
    };

    public CatalogFragment() {
        // Required empty public constructor
    }

    public static CatalogFragment newInstance() {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        try {
            sharedCustomerActivityViewModel = ViewModelProviders.of(getBaseActivity(), factoryShared).get(SharedCustomerViewModel.class);
            sharedCustomerActivityViewModel.changeSender.observe(this, new Observer<ChangeSender>() {
                @Override
                public void onChanged(@Nullable ChangeSender sender) {

                    //RefreshSmallRecycleShortcut(sharedCustomerActivityViewModel.getShortcut());

                    if (sender != ChangeSender.LargeShortcut && sender != ChangeSender.SmallShortcut) {
                        changeCategoryHeader(sharedCustomerActivityViewModel.getShortcut());
                        InitSubCategoryViewPager();
                    }
                    try {
                        fillSmallShortcut();
                    } catch (Exception e) {
                        handleError(new UncaughtException(commonPackage, e));
                    }
                }
            });
            categoryRecyclerView = view.findViewById(R.id.recycler_category);
            smallShortcutRecyclerView = view.findViewById(R.id.recycler_sub_category_detail);
            viewPagerSubCategory = view.findViewById(R.id.viewpager_sub_category);
            viewPagerSubCategory.setOffscreenPageLimit(0);

            TabLayout tabLayout = view.findViewById(R.id.tab_sub_category);
            tabLayout.setupWithViewPager(viewPagerSubCategory);

            smallShortcutAdapter = new ShortcutSmallAdapter(smallShortcutModels, smallShortcutListener);
            layoutManager
                    = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
            smallShortcutRecyclerView.setLayoutManager(layoutManager);
            smallShortcutRecyclerView.setAdapter(smallShortcutAdapter);


            categoryAdapter = new CategoryAdapter(categoryModelList, categoryListener);
            layoutManagerCategory
                    = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false);
            categoryRecyclerView.setLayoutManager(layoutManagerCategory);
            categoryRecyclerView.setAdapter(categoryAdapter);


            subCategories = ResourceData.getSubCategory(getBaseActivity(), 1);
            setCategoryRecycler();
            sharedCustomerActivityViewModel.setCategoryId(1);
            fillSmallShortcut();
            InitSubCategoryViewPager();
            setActionBar();



        } catch (Exception e) {
            HandleException.ShowException(getBaseActivity(), e);
        }

        return view;
    }

    @Override
    public CatalogFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CatalogFragmentViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onChangeLocation(Location location) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_catalog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //gc();
    }

    private void gc() {
        categoryRecyclerView = null;
        smallShortcutRecyclerView = null;
        viewPagerSubCategory = null;
        categoryModelList = null;
        smallShortcutModels = null;
        subCategoryViewPagerAdapter = null;
        categoryAdapter = null;
        smallShortcutAdapter = null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getFragmentTitle() {
        return 0;
    }

    private void setActionBar() {
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        try {
            List<ProfileCategoryModel> profileCategoryModels = ResourceBusiness.GetProfileCategoryAll(getBaseActivity());
            for (ProfileCategoryModel model : profileCategoryModels) {
                menu.add(Menu.NONE, model.ProfileCategoryId, Menu.NONE, model.ProfileName);
            }
        } catch (Exception e) {
            HandleException.ShowException(getBaseActivity(), e);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void InitSubCategoryViewPager() {
        subCategoryViewPagerAdapter = new SubCatalogPagerAdapter(getBaseActivity().getSupportFragmentManager(), subCategories);
        viewPagerSubCategory.setAdapter(subCategoryViewPagerAdapter);
        viewPagerSubCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int xx = 0;
            }

            @Override
            public void onPageSelected(int position) {
                try {
                    sharedCustomerActivityViewModel.setCategoryId(subCategories.get(position).CategoryId);
                    sharedCustomerActivityViewModel.setSubcategoryId(subCategories.get(position).SubCategoryId);
                    if (sharedCustomerActivityViewModel.getSubcategoryId() != sharedCustomerActivityViewModel.getPreSubcategoryId()) {
                        fillSmallShortcut();
                    }
                } catch (Exception e) {
                    HandleException.ShowException(getBaseActivity(), e);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int xx = 0;
            }
        });
    }

    @NonNull
    private List<SubCategoryDetailModel> setSubCategoryDetailModels() throws IOException {
        List<SubCategoryDetailModel> lst = ResourceData.getSubCategoryDetail(getBaseActivity(), sharedCustomerActivityViewModel.getCategoryId());
        SubCategoryDetailModel subCategoryDetailModel = new SubCategoryDetailModel();
        subCategoryDetailModel.Prefix = "r";
        subCategoryDetailModel.Shortcut = subCategories.get(0).ResourceFileId;
        lst.add(0, subCategoryDetailModel);
        return lst;
    }

    private void setCategoryRecycler() throws IOException {
        categoryModelList.clear();
        categoryModelList.addAll(ResourceBusiness.getCategory(getBaseActivity(), 1));

        //fillSmallShortcut(subCategories.get(0).SubCategoryId);
        sharedCustomerActivityViewModel.setSubcategoryId(subCategories.get(0).SubCategoryId);
    }

    private void fillSmallShortcut() throws IOException {
        smallShortcutModels.clear();
        smallShortcutModels.addAll(ResourceData.getSubCategoryDetail(getBaseActivity(), sharedCustomerActivityViewModel.getSubcategoryId()));
        for (int i = 0; i < smallShortcutModels.size(); i++) {
            SubCategoryDetailModel subCategoryDetailModel = smallShortcutModels.get(i);
            if (subCategoryDetailModel.Shortcut == sharedCustomerActivityViewModel.getShortcut()) {
                subCategoryDetailModel.isSelected = true;
                layoutManager.scrollToPosition(i);
                break;
            } else {
                subCategoryDetailModel.isSelected = false;
            }
        }
        smallShortcutAdapter.notifyDataSetChanged();
    }


//    private void RefreshSmallRecycleShortcut(int shortcut) {

//        for (int i = 0; i < smallShortcutModels.size(); i++) {
//            SubCategoryDetailModel subCategoryDetailModel = smallShortcutModels.get(i);
//            if (subCategoryDetailModel.Shortcut == shortcut) {
//                subCategoryDetailModel.isSelected = true;
//            } else {
//                subCategoryDetailModel.isSelected = false;
//            }
//
//        }
    //smallShortcutRecyclerView.smoothScrollToPosition(selectedPos);
    // smallShortcutAdapter.notifyDataSetChanged();
//    }


    private void changeCategoryHeader(int shortcut) {
        try {
            SubCategoryDetailModel subCategoryModel = ResourceData.getShortcutInfo(getBaseActivity(), shortcut);
            subCategories.clear();
            subCategories.addAll(ResourceData.getSubCategory(getBaseActivity(), subCategoryModel.CategoryId));
            RefreshCategory(subCategoryModel.CategoryId);

           // viewPagerSubCategory.setCurrentItem(subCategoryModel.Sort - 1);
            for (SubCategoryModel categoryModel : subCategories) {
                if (categoryModel.SubCategoryId == subCategoryModel.SubCategoryId) {
                    for (SubCategoryModel subCategoryModel1 : subCategories) {
                        if (subCategoryModel1.SubCategoryId == subCategoryModel.SubCategoryId) {
                            subCategoryModel1.CurrentShortcut = shortcut;
                            viewPagerSubCategory.post(new Runnable() {
                                @Override
                                public void run() {
                                    viewPagerSubCategory.setCurrentItem(categoryModel.Sort - 1);
                                }
                            });
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
//            Common.toast(getBaseActivity(), R.string.not_found_shortcut);
            Common.playSound(getBaseActivity(), R.raw.fail);
        }
    }

    private void RefreshCategory(int categoryId) {
        for (int i = 0; i < categoryModelList.size(); i++) {
            CategoryModel categoryModel = categoryModelList.get(i);
            if (categoryModel.CategoryId == categoryId) {
                categoryModel.isSelected = true;
                layoutManagerCategory.scrollToPosition(i);
            } else {
                categoryModel.isSelected = false;
            }
        }
        categoryAdapter.notifyDataSetChanged();
    }
}
