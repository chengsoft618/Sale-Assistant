package com.shoniz.saledistributemobility.view.settings;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.core.CoreApi;
import com.shoniz.saledistributemobility.data.api.core.ICoreApi;
import com.shoniz.saledistributemobility.data.api.download.FileDownloader;
import com.shoniz.saledistributemobility.data.api.download.IFileDownloader;
import com.shoniz.saledistributemobility.data.model.app.api.AppApi;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.api.SettingRetrofit;
import com.shoniz.saledistributemobility.di.AppComponent;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.install.DownloadCompleteReceiver;
import com.shoniz.saledistributemobility.infrastructure.version.VersionData;
import com.shoniz.saledistributemobility.message.gcm.GcmApi;
import com.shoniz.saledistributemobility.message.gcm.GcmPreferences;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.dialog.AsyncTaskDialog;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.utility.dialog.RunnableModel;

import java.io.File;
import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();


            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
    AppComponent component;
    DownloadCompleteReceiver downloadCompleteReceiver = new DownloadCompleteReceiver();

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || DataSyncPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(downloadCompleteReceiver);
        super.onPause();
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {


        ISettingRepository settingRepository;
        IAppApi appApi;
        Preference isTokenGoogleCloudSave;
        Preference googlePlayServices;
        Preference chromeDownload;
        CommonPackage commonPackage;
        private IFileDownloader fileDownloader;
        ICoreApi coreApi;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            Context context = getActivity();
            commonPackage = new CommonPackage(new Device(getActivity()), new Utility(context), context, new SettingPref(context));
            ApiParameter apiParameter = new ApiParameter(commonPackage);
            settingRepository = new SettingRepository(new SettingRetrofit(apiParameter, commonPackage), new SettingPref(context));
            appApi = new AppApi(apiParameter, commonPackage);
            coreApi = new CoreApi(apiParameter, commonPackage, settingRepository);
            fileDownloader = new FileDownloader(commonPackage, coreApi);

            setHasOptionsMenu(true);
            isTokenGoogleCloudSave = findPreference(getString(R.string.pref_key_is_token_cloud_messaging_save));
            googlePlayServices = findPreference(getString(R.string.pref_key_google_play_services_download));
            chromeDownload = findPreference(getString(R.string.pref_key_chrome_download));
            updateCloudMessagingView();
            updateGooglePlayServicesView();
            isTokenGoogleCloudSave.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    registerGoogleCloud();
                    return false;
                }


            });

            chromeDownload.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    downloadChrome();
                    return false;
                }


            });
            googlePlayServices.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    downloadGooglePlayServices();
                    return false;
                }
            });
            //bindPreferenceSummaryToValue(findPreference("example_text"));
            //bindPreferenceSummaryToValue(findPreference("example_list"));
        }

        private void downloadGooglePlayServices() {
            CommonAsyncTask.RunnableDo<List<Integer>, AsyncResult<VersionData>> runnableDo = (param, onProgress) -> {
                AsyncResult<VersionData> asyncResult = new AsyncResult<VersionData>();
                try {
                    asyncResult.model = appApi.getGooglePlayServicesVersionLink();

                    String base = coreApi.getActiveUrl();
                    Uri downloadUri = Uri.parse(base + "/" + asyncResult.model.Path + asyncResult.model.FileName);
                    asyncResult.model.downloadUri = downloadUri;
                } catch (Exception e) {
                    e.getStackTrace();
                    asyncResult.exception = new ApiNetworkException(commonPackage, e);
                }
                return asyncResult;
            };
            CommonAsyncTask.RunnablePost<AsyncResult<VersionData>> postRunnable = result -> {

                if (!result.hasError() && result.model != null) {
                    try {

                        if (result.model.IsNewVersion) {
                            String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ Environment.DIRECTORY_DOWNLOADS;
                            String filePath = downloadPath + "/" + result.model.FileName;

                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {

                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(result.model.downloadUri);
                                startActivity(i);
                                //finish();
                                return;
                            }

                            File file = new File(filePath);
                            if (file.exists()) {
                                commonPackage.getUtility().installApk(filePath);
                            } else {
                                fileDownloader.download(
                                        result.model.Path + result.model.FileName,
                                        result.model.FileName,
                                        commonPackage.getContext().getString(R.string.common_google_play_services_apk),
                                        commonPackage.getContext().getString(R.string.common_mime_type));
                            }
                        }

                    } catch (InOutError inOutError) {
                        commonPackage.getUtility().toast(inOutError.userMessage);
                        //handleError(inOutError);
                    }
                }
            };
            CommonAsyncTask commonAsyncTask = new CommonAsyncTask(null, runnableDo, postRunnable, null);
            commonAsyncTask.run();
        }

        private void downloadChrome() {
            CommonAsyncTask.RunnableDo<List<Integer>, AsyncResult<String>> runnableDo = (param, onProgress) -> {
                AsyncResult<String> asyncResult = new AsyncResult<String>();
                try {
                    asyncResult.model = appApi.getChromeLink();

                    String base = coreApi.getActiveUrl();
                    asyncResult.model = base + "/" + asyncResult.model;
                    //asyncResult.model.downloadUri = downloadUri;
                } catch (Exception e) {
                    e.getStackTrace();
                    asyncResult.exception = new ApiNetworkException(commonPackage, e);
                }
                return asyncResult;
            };
            CommonAsyncTask.RunnablePost<AsyncResult<String>> postRunnable = result -> {

                if (!result.hasError() && result.model != null) {
                    try {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(result.model));
                            startActivity(i);
                            //finish();
                            return;
                        }

                        String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ Environment.DIRECTORY_DOWNLOADS;

                        String fileName = commonPackage.getUtility().getFileNameFromUrl(result.model);
                        String filePath = downloadPath + "/" + fileName;
                        File file = new File(filePath);
                        if (file.exists()) {
                            commonPackage.getUtility().installApk(filePath);
                        } else {
                            fileDownloader.download(
                                    result.model,
                                    fileName,
                                    commonPackage.getContext().getString(R.string.common_google_chorme_apk),
                                    commonPackage.getContext().getString(R.string.common_mime_type));
                        }


                    } catch (InOutError inOutError) {
                        commonPackage.getUtility().toast(inOutError.userMessage);
                        //handleError(inOutError);
                    }
                }
            };
            CommonAsyncTask commonAsyncTask = new CommonAsyncTask(null, runnableDo, postRunnable, null);
            commonAsyncTask.run();
        }


        private void updateCloudMessagingView() {
            if (settingRepository.isTokenCloudMessagingSave()) {
                isTokenGoogleCloudSave.setSummary(R.string.pref_xml_description_cloud_message_register_recommendations);
            } else {
                isTokenGoogleCloudSave.setSummary(R.string.pref_xml_description_cloud_message_not_register_recommendations);
            }
        }

        private void updateGooglePlayServicesView() {
            String cpu;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                cpu = Build.CPU_ABI;
            } else {
                cpu = Build.SUPPORTED_ABIS[0];
            }
            String str = getString(R.string.pref_xml_description_google_play_services, cpu, Build.VERSION.RELEASE);
            googlePlayServices.setSummary(str);

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void registerGoogleCloud() {
            RunnableMethod<Object, Object> runDo = new RunnableMethod<Object, Object>() {
                @Override
                public Object run(Object object, final OnProgressUpdate onProgressUpdate) {
                    RunnableModel runnableModel = new RunnableModel();
                    try {
//                        InstanceID instanceID = InstanceID.getInstance(getActivity());
//                        String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
//                                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                        String token = FirebaseInstanceId.getInstance().getToken();
                        if (!token.isEmpty()) {
                            new GcmApi().saveGcmToken(getActivity(), token);
                            Common.getPref(getActivity()).set(GcmPreferences.SENT_TOKEN_TO_SERVER, token);
                        }

                    } catch (Exception e) {
                        runnableModel.exception = new HandleException(getActivity(), e);
                        runnableModel.HasError = true;
                    }
                    return runnableModel;
                }
            };

            RunnableMethod<RunnableModel, Object> runPost =
                    new RunnableMethod<RunnableModel, Object>() {
                        @Override
                        public Object run(RunnableModel runnableModel, OnProgressUpdate onProgressUpdate) {
                            if (!runnableModel.HasError) {
                                settingRepository.setTokenCloudMessagingSave(true);

                                Common.toast(getActivity(), R.string.google_cloud_register);
                            } else {
                                Common.toast(getActivity(), R.string.common_cloud_not_register);
                            }
                            updateCloudMessagingView();
                            return null;
                        }
                    };

            AsyncTaskDialog asyncTaskDialog = new AsyncTaskDialog(getActivity(),
                    getString(R.string.wait),
                    getString(R.string.google_cloud_register), null, runDo, runPost);
            asyncTaskDialog.execute();
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
