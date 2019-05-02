package com.shoniz.saledistributemobility.view.main;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;
import com.shoniz.saledistributemobility.databinding.ActivityMainBinding;
import com.shoniz.saledistributemobility.di.BaseApp;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.message.gcm.GcmApi;
import com.shoniz.saledistributemobility.order.RequestsListActivity;
import com.shoniz.saledistributemobility.programmer.ProgrammerPanelAccessFragment;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Notification;
import com.shoniz.saledistributemobility.utility.PersianCalendar;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.utility.dialog.YesNoDialog;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.branch.BranchActivity;
import com.shoniz.saledistributemobility.view.message.MessageActivity;
import com.shoniz.saledistributemobility.view.path.pathlist.PathListFragment;
import com.shoniz.saledistributemobility.view.settings.SettingsActivity;
import com.shoniz.saledistributemobility.view.tracking.TrackingActivity;
import com.shoniz.saledistributemobility.view.web.WebSiteActivity;

import javax.inject.Inject;


public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding>
        implements IMainNavigator, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    CommonPackage commonPackage;
    @Inject
    ISettingApi settingApi;
    @Inject
    ISettingRepository settingRepository;
    @Inject
    IAppUpdater appUpdater;
    @Inject
    ILogRepository logRepository;

    Notification notificationAll = null;

    public static void createInstance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            mViewModel.setNavigator(this);
            mViewModel.updateData();

            setMainToolbar();
            BaseApp.startAppConfig(this, settingRepository.getSyncIntervalSeconds(), settingRepository.getIsInTrackingMode());

            PathListFragment newFragment = PathListFragment.newInstance();
            Common.addFragment(this, R.id.fragment_container, newFragment);
            mViewModel.checkLastVersion();
        } catch (Exception ex) {
            UncaughtException uncaughtException1=new UncaughtException(commonPackage, ex);
            uncaughtException1.userMessage=ex.getMessage();
            handleError(uncaughtException1);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public boolean isEnableLocation() {
        return true;
    }

    @Override
    public void onChangeLocation(Location location) {
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (!settingRepository.isTokenCloudMessagingSave()) {
                String token = FirebaseInstanceId.getInstance().getToken();
                if (!token.isEmpty()) {
                    try {
                        new GcmApi().saveGcmToken(this, token);
                        settingRepository.setTokenCloudMessagingSave(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_nav_path) {
            PathListFragment newFragment = PathListFragment.newInstance();
            Common.addFragment(this, R.id.fragment_container, newFragment);
            mViewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.menu_request_list) {
            Intent intent = new Intent(commonPackage.getContext(), RequestsListActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_intro_activity) {
            Intent intent = new Intent(commonPackage.getContext(), BranchActivity.class);
            intent.putExtra(BranchActivity.RE_SELECT_BRANCH, true);
            startActivity(intent);
        } else if (id == R.id.menu_report_activity) {
            WebSiteActivity.startReport(commonPackage.getContext());

        } else if (id == R.id.menu_message_activity) {
            Intent intent = new Intent(commonPackage.getContext(), MessageActivity.class);
            startActivity(intent);

        } else if (id == R.id.my_panel) {

            showNoticeDialog();
//        } else if (id == R.id.menu_verification) {//
//            startActivity(OperationActivity.newIntent(this));
        } else if (id == R.id.menu_tracking_show) {
            startActivity(TrackingActivity.newIntent(this));
        } else if (id == R.id.menu_setting) {
            startActivity(SettingsActivity.newIntent(MainActivity.this));
        }


        return true;
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new ProgrammerPanelAccessFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    public void setMainToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarListener);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mViewDataBinding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mViewDataBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mViewDataBinding.navView.setNavigationItemSelectedListener(this);
        mViewDataBinding.navView.setItemIconTintList(null);

        mViewDataBinding.navView.getMenu().findItem(R.id.menu_tracking_show).setVisible(settingRepository.getCurrentRoleId() == 7);

        View header = mViewDataBinding.navView.getHeaderView(0);
        TextView txtApplicationVersion = header.findViewById(R.id.txt_application_version);
        ImageView employeePhoto = header.findViewById(R.id.img_EmployeeName);
        TextView employeeName = header.findViewById(R.id.txt_EmployeeName);
        TextView employeeImei = header.findViewById(R.id.txt_employee_imei);
        TextView txtDate = mViewDataBinding.navView.findViewById(R.id.txtDate);
        txtDate.setText(PersianCalendar.getPersianDate());


        txtApplicationVersion.setText(commonPackage.getContext().getString(R.string.application_version, commonPackage.getUtility().getVersionName()));


        employeeName.setText(settingRepository.getEmployeeInfoEntity().EmployeeName);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            employeeImei.setText(StringHelper.GenerateMessage(getString(R.string.imei), "" + commonPackage.getDevice().getIMEI()));
            employeeImei.setText(commonPackage.getContext().getString(R.string.imei, commonPackage.getDevice().getIMEI()));
        } else {
            Common.toast(commonPackage.getContext(), StringHelper.GenerateMessage(getString(R.string.access_denied_permissions), Manifest.permission.READ_PHONE_STATE));
        }

        if(!settingRepository.getEmployeeInfoEntity().Photo.isEmpty() && !settingRepository.getEmployeeInfoEntity().Photo.equals("0"))
            employeePhoto.setImageDrawable(Common.getDrawableFromBytes(commonPackage.getContext(), settingRepository.getEmployeeInfoEntity().getPhotoBytes()));

    }

    @Override
    public void updateData(boolean isForNewVersion) {
        Runnable runnablePre = () -> {
            onBeginProgress();
            setProgressSize(1);
        };

        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                appUpdater.setAppUpdateListener(message -> onProgress.onUpdate(message));

                if(isForNewVersion) {
                   // appDataUpdate.updateWholeDataForNewVersion();
                    settingRepository.setCurrentVersionName(commonPackage.getUtility().getVersionName());

                }
                appUpdater.updatePrePath();

            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            onEndProgress();
            if (param.hasError()) {
                if (!commonPackage.getUtility().getVersionName().equals(settingRepository.getPreviousVersionName())){
                    YesNoDialog.showDialog(this, "خطا در بروزرسانی نسخه نرم افزار",
                            "مشکل شبکه در آپدیت نرم افزار بوجود آمده است. لطفا اینترنت و یا وای فای دستگاه خود را چک کرده و سپس ادامه دهید. آیا مجددا تلاش می کنید؟",
                            new Runnable() {
                                @Override
                                public void run() {
                                    updateData(true);
                                }
                            }, new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                }
                handleError(param.exception);
            } else {
                settingRepository.setLastDailyUpdateDate();
            }
            mViewModel.checkLastVersion();
        };

        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
            @Override
            public void onUpdate(Object... objects) {
                showSimpleProgress(objects[0].toString());
            }
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
        commonAsyncTask.run();

    }

    @Override
    public void showNewApkNotification() {
        String note="لطفا نسخه جدید برنامه را از قسمت گزارشات نسخه "+settingRepository.getApkLastVersion() +"نصب کنید.";
        notificationAll = new Notification(this, 101);
        notificationAll.set(R.drawable.ic_launcher_round,"توجه",note);
        notificationAll.setProgress(0, 0, false);
        showSnackBar(note);
        notificationAll.push();

    }

    @Override
    public void startChangeLogs() {
        WebSiteActivity.startChangeLog(commonPackage.getContext());
    }


}
