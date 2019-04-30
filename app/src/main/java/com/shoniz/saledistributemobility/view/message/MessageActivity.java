package com.shoniz.saledistributemobility.view.message;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.databinding.ActivityMessageBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.message.management.BroadcastMessage;
import com.shoniz.saledistributemobility.message.notification.PersonalNotificationHandler;
import com.shoniz.saledistributemobility.view.base.BaseActivity;

import javax.inject.Inject;


public class MessageActivity extends BaseActivity<MessageViewModel, ActivityMessageBinding>
        implements IMessageNavigator {

    @Inject
    ViewModelProvider.Factory factory;

    BroadcastReceiver newMessageReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();
            mViewModel.load();
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, MessageActivity.class);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public MessageViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(MessageViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public boolean isEnableLocation() {
        return false;
    }

    @Override
    public void onChangeLocation(Location location) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        mViewModel.load();
        setMainToolbar();
        RecyclerHelper.build(
                mViewModel,
                this,
                this,
                mViewDataBinding.messageListRecycler,
                MessageItemBindingBuilder.class)
                .setSelectingModel(
                        RecyclerViewModel.SelectingMode.None
                );

        new PersonalNotificationHandler(this, 0).cancelNotification();
    }

    public void setMainToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarListener);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter fp = new IntentFilter(BroadcastMessage.PERSONAL_BROADCAST_ACTION_NAME);
        fp.setPriority(1000);
        registerReceiver(newMessageReceive, fp);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(newMessageReceive);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message_list_update_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update_message_menu) {
            mViewModel.loadLatestMessage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateRecycler() {

    }


}
