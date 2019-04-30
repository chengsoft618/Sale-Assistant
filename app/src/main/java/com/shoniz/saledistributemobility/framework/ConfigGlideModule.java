package com.shoniz.saledistributemobility.framework;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class ConfigGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);



        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(3)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        int bitmapPoolSizeBytes = 1024 * 1024 * 30; // 30mb
        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes));
        int cacheSizeMegaBytes = 1024 * 1024 * 30; // 30mb
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSizeMegaBytes));
    }
}
