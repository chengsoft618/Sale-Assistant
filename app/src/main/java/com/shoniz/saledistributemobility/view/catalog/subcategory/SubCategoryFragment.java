package com.shoniz.saledistributemobility.view.catalog.subcategory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.catalog.ResourceData;
import com.shoniz.saledistributemobility.catalog.ShortcutLargeAdapter;
import com.shoniz.saledistributemobility.databinding.FragmentSubCategoryBinding;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.ChangeSender;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class SubCategoryFragment extends BaseFragment<FragmentSubCategoryBinding, SubCategoryFragmentViewModel> {


    SharedCustomerViewModel sharedCustomerActivityViewModel;

    @Inject
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory factoryShared;

    @Inject
    @Named("SubCategoryFragmentViewModel")
    ViewModelProvider.Factory factory;

    RecyclerView recyclerView;
    List<SubCategoryDetailModel> lst;
    private int resourceFileId;
    private int subCategoryId;

    public static SubCategoryFragment newInstance(SubCategoryModel subCategoryModel) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(SubCategoryModel.Column.SubCategoryId, subCategoryModel.SubCategoryId);
        args.putInt(SubCategoryModel.Column.ResourceFileId, subCategoryModel.ResourceFileId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public SubCategoryFragmentViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SubCategoryFragmentViewModel.class);
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
        return R.layout.fragment_sub_category;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //gc();
    }

    private void gc() {
        recyclerView = null;
        lst = null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subCategoryId = getArguments().getInt(SubCategoryModel.Column.SubCategoryId);
            resourceFileId = getArguments().getInt(SubCategoryModel.Column.ResourceFileId);
        }
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        try {
            sharedCustomerActivityViewModel = ViewModelProviders.of(getBaseActivity(), factoryShared).get(SharedCustomerViewModel.class);
            sharedCustomerActivityViewModel.changeSender.observe(this, new Observer<ChangeSender>() {
                @Override
                public void onChanged(@Nullable ChangeSender sender) {
                    if (sender != ChangeSender.LargeShortcut) {
                        scrollToPositionLargeShortcut();
                    }
                }
            });
            recyclerView = view.findViewById(R.id.recycler_shortcut);
            lst = setSubCategoryDetailModels(subCategoryId);
            ShortcutLargeAdapter shortcutAdapter = new ShortcutLargeAdapter(lst, recyclerView, new ShortcutLargeAdapter.OnShortcutAdapterListener() {
                @Override
                public void onItemSelected(int pos) {
                    sharedCustomerActivityViewModel.resizeCatalog();
                }
            });
            final LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            final SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(shortcutAdapter);

            scrollToPositionLargeShortcut();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);//1
                    try {
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            int pos = layoutManager.findFirstCompletelyVisibleItemPosition();
                            if (pos > 0)
                                sharedCustomerActivityViewModel.setShortcutSender("" + lst.get(pos).Shortcut, ChangeSender.LargeShortcut);
                        }
                    } catch (Exception e) {
                        // HandleException.ShowException(activity,e);
                    }

                }
            });

        } catch (Exception e) {
            HandleException.ShowException(getBaseActivity(), e);
        }
        return view;
    }

    private void scrollToPositionLargeShortcut() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < lst.size(); i++) {
                    if (lst.get(i).Shortcut == sharedCustomerActivityViewModel.getShortcut()) {
                        recyclerView.getLayoutManager().scrollToPosition(i);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @NonNull
    private List<SubCategoryDetailModel> setSubCategoryDetailModels(int categoryId) throws IOException {
        List<SubCategoryDetailModel> lst = ResourceData.getSubCategoryDetail(getBaseActivity(), categoryId);
        SubCategoryDetailModel subCategoryDetailModel = new SubCategoryDetailModel();
        subCategoryDetailModel.Prefix = "r";
        subCategoryDetailModel.Shortcut = resourceFileId;
        lst.add(0, subCategoryDetailModel);
        return lst;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public int getFragmentTitle() {
        return 0;
    }

}
