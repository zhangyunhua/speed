/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.widget.ImageView
 */
package com.meituan.sample.robusttest.other;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.lang.ref.WeakReference;

public class ImageQualityUtil {
    private static final String DEFAULT_SIZE = "/w.h/";
    private static final String INN_POI_LIST_SIZE = "/640.0/";
    private static final String LARGE_SIZE = "/440.267/";
    private static final String LARGE_SIZE_POI_HEADER_IMAGE = "/800.480/";
    private static final String MEDIUM_SIZE = "/0.160/";
    private static final String MIDDLE_SIZE = "/200.120/";
    private static final String MORE_LARGE_SIZE = "/600.160/";
    private static final String MORE_MIDDLE_SIZE = "/290.140/";
    private static final String SMALL_SIZE = "/120.76/";

    /*
     * Enabled aggressive block sorting
     */
    public static String getDefaultSize(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getDefaultSize(java.lang.String) start in ").append(l).toString());
        String string3 = TextUtils.isEmpty((CharSequence)string2) ? "" : ImageQualityUtil.getWebpUrl(string2.replace("/w.h/", "/"));
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getDefaultSize(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getFeatureFoodUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getFeatureFoodUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/200.200/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getFeatureFoodUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getIndexDefaultSize(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getIndexDefaultSize(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getIndexDefaultSize(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getInnListUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getInnListUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/640.0/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getInnListUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getLargeUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getLargeUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/440.267/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getLargeUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getMediumSize(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMediumSize(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/0.160/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMediumSize(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getMiddleUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMiddleUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/200.120/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMiddleUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getMoreLargeUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMoreLargeUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/600.160/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMoreLargeUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getMoreMiddleUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMoreMiddleUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/290.140/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getMoreMiddleUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getPoiAlbumUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getPoiAlbumUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/300.0/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getPoiAlbumUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getPoiHeaderImageLargeUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getPoiHeaderImageLargeUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/800.480/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getPoiHeaderImageLargeUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String getQualityUrl(String string2, String string3) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getQualityUrl(java.lang.String,java.lang.String) start in ").append(l).toString());
        String string4 = TextUtils.isEmpty((CharSequence)string2) ? "" : ImageQualityUtil.getWebpUrl(string2.replace("/w.h/", string3));
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getQualityUrl(java.lang.String,java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String getShareUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getShareUrl(java.lang.String) start in ").append(l).toString());
        String string3 = TextUtils.isEmpty((CharSequence)string2) ? "" : ImageQualityUtil.getWebpUrl(string2.replace("/w.h/", "/"));
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getShareUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    public static String getSmallUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getSmallUrl(java.lang.String) start in ").append(l).toString());
        String string3 = ImageQualityUtil.getQualityUrl(string2, "/120.76/");
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getSmallUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String getWebpUrl(String string2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getWebpUrl(java.lang.String) start in ").append(l).toString());
        if (Build.VERSION.SDK_INT >= 18 && !"Nokia_X".equals(Build.MODEL)) {
            String string3 = string2;
            if (TextUtils.isEmpty((CharSequence)string3)) {
                string2 = "";
            } else if (!string3.toLowerCase().endsWith(".webp")) {
                Uri.parse((String)string3).getHost();
                string2 = string3;
            }
        }
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.getWebpUrl(java.lang.String) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        return string2;
    }

    public static void loadImage(Context context, Picasso picasso, String string2, int n, ImageView imageView) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView) start in ").append(l).toString());
        ImageQualityUtil.loadImage(context, picasso, string2, n, imageView, true);
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void loadImage(Context context, Picasso picasso, String string2, int n, ImageView imageView, int n2, int n3, boolean bl) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,int,int,boolean) start in ").append(l).toString());
        picasso.cancelRequest(imageView);
        imageView.setTag(1, (Object)null);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            if (n != 0) {
                imageView.setImageResource(n);
            }
        } else {
            new ImageViewLoader(imageView, picasso, string2, n, true, false, n2, n3, bl).load();
        }
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,int,int,boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void loadImage(Context context, Picasso picasso, String string2, int n, ImageView imageView, int n2, boolean bl) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,int,boolean) start in ").append(l).toString());
        picasso.cancelRequest(imageView);
        imageView.setTag(1, (Object)null);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            if (n != 0) {
                imageView.setImageResource(n);
            }
        } else {
            new ImageViewLoader(imageView, picasso, string2, n, true, false, n2, bl).load();
        }
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,int,boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    public static void loadImage(Context context, Picasso picasso, String string2, int n, ImageView imageView, boolean bl) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,boolean) start in ").append(l).toString());
        ImageQualityUtil.loadImage(context, picasso, string2, n, imageView, bl, false);
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void loadImage(Context context, Picasso picasso, String string2, int n, ImageView imageView, boolean bl, boolean bl2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,boolean,boolean) start in ").append(l).toString());
        picasso.cancelRequest(imageView);
        imageView.setTag(1, (Object)null);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            if (n != 0) {
                imageView.setImageResource(n);
            }
        } else {
            new ImageViewLoader(imageView, picasso, string2, n, bl, false).load();
        }
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,int,android.widget.ImageView,boolean,boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    public static void loadImage(Context context, Picasso picasso, String string2, Drawable drawable2, ImageView imageView) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView) start in ").append(l).toString());
        ImageQualityUtil.loadImage(context, picasso, string2, drawable2, imageView, true);
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    public static void loadImage(Context context, Picasso picasso, String string2, Drawable drawable2, ImageView imageView, boolean bl) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView,boolean) start in ").append(l).toString());
        ImageQualityUtil.loadImage(context, picasso, string2, drawable2, imageView, bl, false);
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView,boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void loadImage(Context context, Picasso picasso, String string2, Drawable drawable2, ImageView imageView, boolean bl, boolean bl2) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView,boolean,boolean) start in ").append(l).toString());
        picasso.cancelRequest(imageView);
        imageView.setTag(1, (Object)null);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            if (drawable2 != null) {
                imageView.setImageDrawable(drawable2);
            }
        } else {
            new ImageViewLoader(imageView, picasso, string2, drawable2, bl, false).load();
        }
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView,boolean,boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void loadImage(Context context, Picasso picasso, String string2, Drawable drawable2, ImageView imageView, boolean bl, boolean bl2, OnImageLoadFinishedListener onImageLoadFinishedListener) {
        long l = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView,boolean,boolean,com.meituan.sample.robusttest.ImageQualityUtil$OnImageLoadFinishedListener) start in ").append(l).toString());
        picasso.cancelRequest(imageView);
        imageView.setTag(1, (Object)null);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            if (drawable2 != null) {
                imageView.setImageDrawable(drawable2);
            }
        } else {
            new ImageViewLoader(imageView, picasso, string2, drawable2, bl, false).setOnImageLoadFinishedListener(onImageLoadFinishedListener).load();
        }
        long l2 = System.currentTimeMillis();
        Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil.loadImage(android.content.Context,com.squareup.picasso.Picasso,java.lang.String,android.graphics.drawable.Drawable,android.widget.ImageView,boolean,boolean,com.meituan.sample.robusttest.ImageQualityUtil$OnImageLoadFinishedListener) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
    }

    private static class ImageViewLoader
    implements Callback,
    View.OnTouchListener {
        final boolean centerCrop;
        final Drawable drawable;
        int imageHeight;
        final int imageSize;
        final WeakReference<ImageView> imageViewRef;
        int imageWith;
        boolean isForceLoading;
        final boolean localOnly;
        final boolean noFade;
        private OnImageLoadFinishedListener onImageLoadFinishedListener;
        final Picasso picasso;
        final int placeHolder;
        final String url;

        private ImageViewLoader(ImageView imageView, Picasso picasso, String string2, int n, boolean bl, boolean bl2) {
            this(imageView, picasso, string2, n, bl, bl2, -1, false);
        }

        public ImageViewLoader(ImageView imageView, Picasso picasso, String string2, int n, boolean bl, boolean bl2, int n2, int n3, boolean bl3) {
            this.imageViewRef = new WeakReference<ImageView>(imageView);
            this.picasso = picasso;
            this.url = string2;
            this.placeHolder = n;
            this.noFade = bl;
            this.localOnly = bl2;
            this.centerCrop = bl3;
            this.imageHeight = n3;
            this.imageWith = n2;
            this.imageSize = -1;
            this.drawable = null;
        }

        public ImageViewLoader(ImageView imageView, Picasso picasso, String string2, int n, boolean bl, boolean bl2, int n2, boolean bl3) {
            this.imageViewRef = new WeakReference<ImageView>(imageView);
            this.picasso = picasso;
            this.url = string2;
            this.placeHolder = n;
            this.noFade = bl;
            this.localOnly = bl2;
            this.centerCrop = bl3;
            this.imageSize = n2;
            this.drawable = null;
        }

        private ImageViewLoader(ImageView imageView, Picasso picasso, String string2, Drawable drawable2, boolean bl, boolean bl2) {
            this(imageView, picasso, string2, drawable2, bl, bl2, -1, false);
        }

        public ImageViewLoader(ImageView imageView, Picasso picasso, String string2, Drawable drawable2, boolean bl, boolean bl2, int n, boolean bl3) {
            this.imageViewRef = new WeakReference<ImageView>(imageView);
            this.picasso = picasso;
            this.url = string2;
            this.drawable = drawable2;
            this.noFade = bl;
            this.localOnly = bl2;
            this.centerCrop = bl3;
            this.imageSize = n;
            this.placeHolder = 0;
        }

        public void load() {
            long l = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.load() start in ").append(l).toString());
            this.load(false);
            long l2 = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.load() end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        }

        /*
         * Enabled aggressive block sorting
         */
        public void load(boolean bl) {
            long l = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.load(boolean) start in ").append(l).toString());
            ImageView imageView = this.imageViewRef.get();
            if (imageView != null) {
                RequestCreator requestCreator = this.picasso.load(this.url);
                if (this.localOnly && !bl) {
                    requestCreator.networkPolicy(NetworkPolicy.OFFLINE, new NetworkPolicy[0]);
                }
                if (this.centerCrop) {
                    requestCreator.centerCrop();
                }
                if (this.imageSize > 0) {
                    requestCreator.resize(this.imageSize, this.imageSize);
                }
                if (this.imageWith > 0 || this.imageHeight > 0) {
                    requestCreator.resize(this.imageWith, this.imageHeight);
                }
                if (this.noFade) {
                    requestCreator.noFade();
                }
                if (this.placeHolder != 0) {
                    requestCreator.placeholder(this.placeHolder);
                } else if (this.drawable != null) {
                    requestCreator.placeholder(this.drawable);
                }
                requestCreator.into(imageView, this);
            }
            long l2 = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.load(boolean) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        }

        @Override
        public void onError() {
            long l = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.onError() start in ").append(l).toString());
            ImageView imageView = this.imageViewRef.get();
            if (imageView != null) {
                imageView.setOnTouchListener((View.OnTouchListener)this);
            }
            this.isForceLoading = false;
            long l2 = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.onError() end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        }

        @Override
        public void onSuccess() {
            long l = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.onSuccess() start in ").append(l).toString());
            ImageView imageView = this.imageViewRef.get();
            if (imageView != null) {
                imageView.setOnTouchListener(null);
            }
            this.isForceLoading = false;
            if (this.onImageLoadFinishedListener != null) {
                this.onImageLoadFinishedListener.onImageLoadFinished();
            }
            long l2 = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.onSuccess() end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean bl = true;
            long l = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.onTouch(android.view.View,android.view.MotionEvent) start in ").append(l).toString());
            if (motionEvent.getAction() == 0 && !this.isForceLoading) {
                this.isForceLoading = bl;
                this.load(bl);
            } else {
                bl = false;
            }
            long l2 = System.currentTimeMillis();
            Log.d((String)"SpeedPlugin", (String)new StringBuffer().append("com.meituan.sample.robusttest.ImageQualityUtil$ImageViewLoader.onTouch(android.view.View,android.view.MotionEvent) end in ").append(l2).append("  cost: ").append(l2 - l).append("ms").toString());
            return bl;
        }

        public ImageViewLoader setOnImageLoadFinishedListener(OnImageLoadFinishedListener onImageLoadFinishedListener) {
            this.onImageLoadFinishedListener = onImageLoadFinishedListener;
            return this;
        }
    }

    public static interface OnImageLoadFinishedListener {
        public void onImageLoadFinished();
    }

}

