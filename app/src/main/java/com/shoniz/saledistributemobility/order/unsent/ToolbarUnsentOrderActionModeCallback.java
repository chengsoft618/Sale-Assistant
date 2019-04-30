package com.shoniz.saledistributemobility.order.unsent;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.shoniz.saledistributemobility.R;

/**
 * Created by aghazadeh.a on 8/3/2017.
 */

public class ToolbarUnsentOrderActionModeCallback implements ActionMode.Callback{
    private UnsentOrderAdapter adapter;
    private Menu menu;
    public ToolbarUnsentOrderActionModeCallback(UnsentOrderAdapter adapter )
    {
        this.adapter = adapter;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.unsent_order_list_action_mode, menu);//Inflate the menu over action mode
        this.menu=menu;
        final CheckBox checkBox = (CheckBox) menu.findItem(R.id.action_select).getActionView();
        checkBox.setText("انتخاب همه");
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelected();
                if((checkBox.isChecked())){
                    adapter.setAllItemSelected(true);
                    adapter.setSelectableMode( true);
                }else{
                    adapter.setAllItemSelected(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                adapter.performUnsentOrderUpdateClick();

                break;

            case R.id.action_delete:
                adapter.performUnsentOrderDeleteClick();

                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        adapter.setSelectableMode(false);
        adapter.setAllSelectedMode(false);
        adapter.setAllItemSelected(false);
         adapter.notifyDataSetChanged();
    }

    public void setCheckAll(Boolean flag)
    {
        CheckBox checkBox = (CheckBox) menu.findItem(R.id.action_select).getActionView();
        checkBox.setChecked(flag);
    }
}
