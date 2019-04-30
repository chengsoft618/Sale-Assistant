package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.support.annotation.MenuRes;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.shoniz.saledistributemobility.R;

public abstract class RecyclerToolbar implements ActionMode.Callback, IRecyclerToolbarListener {

    int menuId;
    Menu menu;
    public RecyclerViewModel viewModel;

    public RecyclerToolbar(@MenuRes int menuId, RecyclerViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.setRecyclerToolbarListener(this);
        this.menuId = menuId;
    }

    CheckBox checkBox;

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(menuId, menu);
        this.menu = menu;
        checkBox = (CheckBox) menu.findItem(R.id.action_select).getActionView();
        //checkBox.setText("انتخاب همه");
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((checkBox.isChecked())) {

                    selectAll();
                } else {
                    unSelectAll();
                }
            }
        });
        return true;

    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select:
                break;
        }
        return false;
    }

    public void selectAll() {
        viewModel.selectAll();
    }

    public void unSelectAll() {
        viewModel.unSelectAll();
    }

    @Override
    public void onSelectedChange(boolean status) {
        if (!status) checkBox.setChecked(false);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        viewModel.hideSelectivity();
        viewModel.setHasActiveActionMode(false);
    }

}
