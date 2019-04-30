package com.shoniz.saledistributemobility.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.view.base.BaseActivity;

import dagger.android.support.DaggerFragment;


public class BaseFragment extends Fragment {
    protected AppCompatActivity activity;

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected ActionBar getActionBar() {
        return (activity).getSupportActionBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            this.activity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }
}
