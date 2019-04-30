package com.shoniz.saledistributemobility.view.base;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerAdapter;

import java.util.List;

public class BindingUtils {

    @BindingAdapter("adapter")
    public static <T> void addModels(RecyclerView recycler, List<T> models){
        RecyclerAdapter adapter = (RecyclerAdapter)recycler.getAdapter();
        if(adapter != null){
            adapter.clearItems();
            adapter.setAdapterModel(models);
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    @BindingAdapter({ "loadUrl" })
    public static void loadUrl(WebView webview, String url) {
        webview.loadUrl(url);
        webview.setSaveEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings()
                .setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {


            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }

        });
    }

    @BindingAdapter({ "setImageViewBitmap" })
    public static void setImageViewBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("visibility")
    public static void setVisibility(View view, Boolean value) {
        if (value != null)
            view.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("backgroundColor")
    public static void setBackgroundColor(View view, int value) {
        view.setBackgroundColor(view.getResources().getColor(value));
    }

    @BindingAdapter("android:src")
    public static void setImage(ImageView imageView, int resourceId) {
        imageView.setImageResource(resourceId);
    }

    @BindingAdapter("android:drawableStart")
    public static void setDrawableStart(TextView textView, int resourceId) {
        Drawable drawable = AppCompatResources.getDrawable(textView.getContext(), resourceId);
        Drawable[] drawables = textView.getCompoundDrawables();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelative(drawable,
                    drawables[1], drawables[2], drawables[3]);
        }else {
            textView.setCompoundDrawables(drawable,
                    drawables[1], drawables[2], drawables[3]);
        }
    }

}
