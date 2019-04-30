package com.shoniz.saledistributemobility.utility;



import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.data.pref.PreferenceManager;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import static com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts.VERSION_DATABASE;

public class Common {
    private static Hashtable<String, Typeface> _typeFaceList;

    public static final Typeface getTypeFace(Context context, String fontPath) {
        if (_typeFaceList == null)
            _typeFaceList = new Hashtable<>();

        if (!_typeFaceList.containsKey(fontPath)) {
            try {
                AssetManager mgr = context.getAssets();
                Typeface tf = Typeface.createFromAsset(mgr, fontPath);
                _typeFaceList.put(fontPath, tf);
            } catch (Exception ex) {
                ex.getMessage();
            }

        }
        return _typeFaceList.get(fontPath);
    }

    public static DBHelper getSaleDataBase(Context context) throws IOException {
        String dbname = "SaleDatabase";
        String path = DBHelper.getDatabasePath(context) + "/";
        DBHelper db = DBHelper.createFromAssets(context, path + dbname + ".db",
                VERSION_DATABASE, "db/" + dbname + ".db");


       /* File backFile = new File(Environment.getExternalStorageDirectory() + "/tempSale");
        if(!backFile.exists())
            backFile.mkdirs();
            FileManager.copyFile(new File(path), new File(Environment.getExternalStorageDirectory() + "/tempSale/CustomerBuy.db"));*/

        return db;
    }

    public static DBHelper getUserDataBase(Context context) throws IOException {
        String dbname = "UserDatabase";
        String path = DBHelper.getDatabasePath(context) + "/";
        DBHelper db = DBHelper.createFromAssets(context, path + dbname + ".db",
                VERSION_DATABASE, "db/" + dbname + ".db");


       /* File backFile = new File(Environment.getExternalStorageDirectory() + "/tempSale");
        if(!backFile.exists())
            backFile.mkdirs();
            FileManager.copyFile(new File(path), new File(Environment.getExternalStorageDirectory() + "/tempSale/CustomerBuy.db"));*/

        return db;
    }

    public static DBHelper openDatabase(Context context,Enums.DBName dbname) {
        String path = DBHelper.getDatabasePath(context) + "/";
        DBHelper db = DBHelper.openDatabase(context, path + dbname.toString() + ".db",
                VERSION_DATABASE);
        return db;
    }

    public static void copyFile(final String src, final String dest) {
        InputStream in = null;
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int len;
        int writed = 0;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
                writed = writed + len;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackgroundDrawable(View view, int drawable){
        final int sdk = Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(
                    ContextCompat.getDrawable(view.getContext(), drawable));
        } else {
            view.setBackground(
                    ContextCompat.getDrawable(view.getContext(), drawable));
        }
    }

    public static void toast(Context context,String str)
    {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
    public static void toast(Context context,int resId)
    {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
    }

    public static PreferenceManager getPref(Context context)
    {
        return new PreferenceManager(context);
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pInfo =  context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public static Drawable getDrawableFromBytes(Context context,byte[] bytes){
        return  new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }

    public static void setTextViewText(View view, int id, String text){
        ((TextView)view.findViewById(id)).setText(text);
    }

    public static String getTextViewText(View view, int id){
        return ((TextView)view.findViewById(id)).getText().toString();
    }

    public static void setRecycleViewLayoutManager(Context context, RecyclerView recyclerView,int foreCol ) {
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else{
            RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(context, foreCol);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            recyclerView.setLayoutManager(layoutManager);

        }
    }

    public static void setRecycleViewLayoutManager(Context context, RecyclerView recyclerView ) {
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else{
            RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(context, context.getResources().getInteger(R.integer.recycler_grid_small_col_number));
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            recyclerView.setLayoutManager(layoutManager);

        }
    }

    public static void storeBase64ImageToFile(String base64,
                                             String filePath) throws IOException {

        OutputStream osf = null;
        try {
            byte[] imgBytes = Base64.decode(base64, Base64.DEFAULT);
            osf = new FileOutputStream(new File(filePath));
            osf.write(imgBytes);
            osf.flush();
        } finally {
            if (osf != null)
                osf.close();
        }
    }



    public static void addFragment(FragmentActivity activity, int fragmentId, Fragment fragment)
    {
        FragmentManager fManager = activity.getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        String fragmentName=fragment.getClass().toString();
        Fragment fragmentOld = fManager.findFragmentByTag(fragmentName);

        if (fragmentOld == null) {
            fTransaction.replace(fragmentId, fragment, fragmentName);
            fTransaction.addToBackStack(null);
        }
        else {
            if(fragmentName.endsWith("PathListFragment")){
                for(int entry = 0; entry < fManager.getBackStackEntryCount(); entry++){
                    fManager.popBackStack();
                }
            }
            fTransaction.replace(fragmentId, fragment, fragmentName);
            fTransaction.addToBackStack(null);
        }
        fTransaction.commit();
    }

    public static String GetNumbersCommaFormat(List<Integer> personIds)    {
        StringBuilder result= new StringBuilder();
        for (int num:personIds) {
            result.append(num).append(",");
        }
        return result.toString().substring(0,result.length()-1);
    }

    public static int getCurrentUserId(){
        return 12345;
    }

    public static String getCurrencyFormat(String price){
        try{
            long p = Long.parseLong(price);
            return getCurrencyFormat(p);
        }catch (Exception e){}
        return price;
    }

    public static String getCurrencyFormat(int price){
        return String.format(Locale.US,"%,d",price);
    }

    public static String getCurrencyFormat(long price){
        return String.format(Locale.US,"%,d",price);
    }

    public static void playSound(Context context,int resId) {
        MediaPlayer mp = MediaPlayer.create(context, resId);
        mp.start();
    }

    public static String getAmountRemainDescription(Context context,int accountRemain) {
        if (accountRemain==0)
            return context.getString(R.string.settlement);
        else if(accountRemain>0)

            return StringHelper.GenerateMessage(context,R.string.debtor, getCurrencyFormat(-1*accountRemain));
        else
            return StringHelper.GenerateMessage(context,R.string.creditor, getCurrencyFormat(accountRemain));
    }
}
