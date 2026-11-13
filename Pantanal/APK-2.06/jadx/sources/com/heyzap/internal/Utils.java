package com.heyzap.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.common.concurrency.SettableFuture;
import com.heyzap.sdk.ads.HeyzapAds;
import com.tapjoy.TapjoyConstants;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    private static final String CACHE_DIR = "com.heyzap.sdk";
    public static final String HEYZAP_PACKAGE = "com.heyzap.android";
    private static final String IMAGE_CACHE_DIR = "com.heyzap.sdk.images";
    private static Future<Boolean> advertisingIdAvailable;
    private static HashMap<String, String> cachedParams;
    ExecutorService executorPool = ExecutorPool.getInstance();
    private static float density = -1.0f;
    private static Boolean debug = null;
    private static String deviceId = "unknown";
    public static String packageName = "unknown";
    public static String gameName = "unknown";
    private static Object paramLock = new Object();
    private static String advertisingId = null;
    private static Boolean limitAdTrackingEnabled = false;
    private static Object advertiserIdLock = new Object();

    interface ResponseHandler {
        void onFailure(Throwable th);

        void onSuccess(String str);
    }

    public static void setDebug(boolean newDebug) {
        debug = Boolean.valueOf(newDebug);
    }

    public static Boolean isDebug(Context context) {
        if (debug == null) {
            debug = Boolean.valueOf(packageIsInstalled(Constants.SNAKE_PACKAGE, context));
        }
        return debug;
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase(Locale.US) + s.substring(1);
    }

    public static String truncate(String s, int limit) {
        if (s.length() > limit) {
            return s.substring(0, limit) + "...";
        }
        return s;
    }

    public static String getAppLabel(Context context) {
        CharSequence label = null;
        try {
            label = context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0));
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (label == null) {
            return null;
        }
        return label.toString();
    }

    public static int daysBetween(Date d1, Date d2) {
        return Math.abs((int) ((d1.getTime() - d2.getTime()) / 86400000));
    }

    public static boolean marketInstalled(Context context) {
        try {
            Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.heyzap.android"));
            List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(marketIntent, 65536);
            return !resolveInfo.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public ExecutorService getExecutorPool() {
        return this.executorPool;
    }

    public static void load(Context context) {
        createCacheDir(context);
        loadAdvertisingId(context);
    }

    public static String getPackageName(Context context) {
        if (packageName.equals("unknown") && context != null) {
            String pn = context.getPackageName();
            if (pn.endsWith(".debug")) {
                pn = pn.substring(0, pn.length() - 6);
            }
            packageName = pn;
        }
        return packageName;
    }

    public static String getAppName(Context context) {
        if (gameName.equals("unknown") && context != null) {
            gameName = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
        }
        return gameName;
    }

    public static String getDeviceId(Context context) {
        if (deviceId.equals("unknown") && context != null) {
            String product = Build.PRODUCT;
            String androidId = Settings.Secure.getString(context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
            if (product != null && androidId != null) {
                deviceId = product + "_" + androidId;
            }
        }
        return deviceId;
    }

    public static boolean androidVersionSupported() {
        try {
            return Integer.parseInt(Build.VERSION.SDK) >= 7;
        } catch (Exception e) {
            return false;
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                try {
                    String line = reader.readLine();
                    if (line != null) {
                        sb.append(line + "\n");
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            Logger.trace((Throwable) e);
                        }
                    }
                } catch (Throwable th) {
                    try {
                        is.close();
                    } catch (IOException e2) {
                        Logger.trace((Throwable) e2);
                    }
                    throw th;
                }
            } catch (IOException e3) {
                Logger.trace((Throwable) e3);
                try {
                    is.close();
                } catch (IOException e4) {
                    Logger.trace((Throwable) e4);
                }
            }
        }
        is.close();
        return sb.toString();
    }

    public static String getAdvertisingId(Context context) {
        try {
            if (advertisingIdAvailable == null) {
                loadAdvertisingId(context);
            }
            if (advertisingIdAvailable != null) {
                advertisingIdAvailable.get();
            }
            return advertisingId;
        } catch (InterruptedException e) {
            Logger.trace((Throwable) e);
            return advertisingId;
        } catch (ExecutionException e2) {
            Logger.trace((Throwable) e2);
            return advertisingId;
        }
    }

    private static void loadAdvertisingId(Context context) {
        synchronized (advertiserIdLock) {
            if (advertisingIdAvailable == null || advertisingIdAvailable.isDone()) {
                AdvertisingIdCallable callable = new AdvertisingIdCallable(context.getApplicationContext());
                advertisingIdAvailable = ExecutorPool.getInstance().submit(callable);
            }
        }
    }

    public static Boolean getLimitAdTrackingEnabled(Context context) {
        try {
            if (advertisingIdAvailable == null) {
                loadAdvertisingId(context);
            }
            advertisingIdAvailable.get();
            return limitAdTrackingEnabled;
        } catch (InterruptedException e) {
            Logger.trace((Throwable) e);
            return limitAdTrackingEnabled;
        } catch (ExecutionException e2) {
            Logger.trace((Throwable) e2);
            return limitAdTrackingEnabled;
        }
    }

    public static void setAdvertisingId(String adId) {
        advertisingId = adId;
    }

    public static void setLimitAdTracking(Boolean enabled) {
        limitAdTrackingEnabled = enabled;
    }

    public static HashMap<String, String> extraParams(Context context) {
        String formFactor;
        HashMap<String, String> params = new HashMap<>();
        synchronized (paramLock) {
            if (cachedParams == null) {
                cachedParams = new HashMap<>();
                int version = 0;
                try {
                    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    version = Integer.valueOf(pInfo.versionCode);
                } catch (Exception e) {
                    Logger.trace((Throwable) e);
                }
                cachedParams.put(TapjoyConstants.TJC_APP_VERSION_NAME, String.valueOf(version));
                context.getResources().getDisplayMetrics();
                cachedParams.put("sdk_version", "10.2.0");
                cachedParams.put("android_version", Build.VERSION.SDK);
                cachedParams.put("external_package", getPackageName(context));
                cachedParams.put("game_package", getPackageName(context));
                cachedParams.put("app_name", getAppName(context));
                if (isTablet(context)) {
                    formFactor = "tablet";
                } else {
                    formFactor = "phone";
                }
                cachedParams.put("device_form_factor", formFactor);
                cachedParams.put("device_model", Build.MODEL);
                cachedParams.put(TapjoyConstants.TJC_DEVICE_TYPE_NAME, Build.DEVICE);
                String publisherId = HeyzapAds.config.publisherId;
                if (publisherId != null) {
                    cachedParams.put("publisher_sdk_key", publisherId);
                }
                if (isAmazon()) {
                    cachedParams.put(TapjoyConstants.TJC_PLATFORM, "amazon");
                    cachedParams.put("sdk_platform", "amazon");
                } else {
                    cachedParams.put(TapjoyConstants.TJC_PLATFORM, TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
                    cachedParams.put("sdk_platform", TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
                }
                params.putAll(cachedParams);
            } else {
                params.putAll(cachedParams);
            }
            throw th;
        }
        if (getAdvertisingId(context) != null && !isAmazon()) {
            params.put("device_id", getAdvertisingId(context));
            params.put(TapjoyConstants.TJC_ADVERTISING_ID, getAdvertisingId(context));
            params.put("tracking_enabled", !getLimitAdTrackingEnabled(context).booleanValue() ? "1" : "0");
        } else {
            params.put("device_id", getDeviceId(context));
        }
        if (HeyzapAds.mediator != null) {
            params.put("sdk_mediator", HeyzapAds.mediator);
        }
        if (HeyzapAds.framework != null) {
            params.put("sdk_framework", HeyzapAds.framework);
        }
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long bytesAvailable = ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks());
            params.put("device_free_bytes", Long.toString(bytesAvailable));
        } catch (Exception e2) {
            params.put("device_free_bytes", "0");
        }
        Locale locale = context.getResources().getConfiguration().locale;
        if (locale != null) {
            params.put("locale_country", locale.getCountry().toLowerCase(Locale.US));
            params.put("locale_lang", locale.getLanguage().toLowerCase(Locale.US));
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        params.put("device_dpi", Float.toString(dm.density));
        if (!params.containsKey("device_width")) {
            params.put("device_width", String.valueOf(dm.widthPixels));
        }
        if (!params.containsKey("device_height")) {
            params.put("device_height", String.valueOf(dm.heightPixels));
        }
        return params;
    }

    public static boolean isAmazon() {
        return Build.MANUFACTURER.equals("Amazon") || HeyzapAds.config.store.equals("amazon");
    }

    public static int dpToPx(Context context, int dp) {
        density = density > BitmapDescriptorFactory.HUE_RED ? density : context.getResources().getDisplayMetrics().density;
        return (int) ((dp * density) + 0.5f);
    }

    public static String getSignatureHash(Context context) {
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            byte[] sig = signatures[0].toByteArray();
            String hash = Base64.encodeToString(MessageDigest.getInstance("SHA-1").digest(sig), 2);
            return hash;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.trace((Throwable) e);
            return "";
        } catch (NoSuchAlgorithmException e2) {
            Logger.trace((Throwable) e2);
            return "";
        }
    }

    public static String getAbsolutePath(Context context, String fileName) {
        return String.format("%s/files/%s", context.getCacheDir().getAbsolutePath(), fileName);
    }

    public static String saveBitmapToLocalFile(Context context, String fileName, Bitmap image) {
        try {
            if (fileName == null) {
                throw new Exception("No filename.");
            }
            if (image == null) {
                throw new Exception("No image.");
            }
            String absolutePath = getAbsolutePath(context, fileName);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, bs);
            FileOutputStream outputStream = context.openFileOutput(fileName, 3);
            if (outputStream != null) {
                outputStream.write(bs.toByteArray());
                return absolutePath;
            }
            throw new Exception("Unable to open output file stream.");
        } catch (Exception e) {
            Logger.trace((Throwable) e);
            return null;
        }
    }

    public static int getScaledSize(Context context, int baseSize) {
        return getScaledSize(context, baseSize);
    }

    public static int getScaledSize(Context context, float baseSize) {
        if (density <= BitmapDescriptorFactory.HUE_RED) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return (int) (density * baseSize);
    }

    public static int getInverseScaledSize(Context context, int pixels) {
        return getInverseScaledSize(context, pixels);
    }

    public static int getInverseScaledSize(Context context, float pixels) {
        if (density <= BitmapDescriptorFactory.HUE_RED) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return (int) (pixels / density);
    }

    public static int getScaledSizeWithRelativeFlags(Context context, int dp) {
        if (dp <= 0) {
            return dp;
        }
        if (density <= BitmapDescriptorFactory.HUE_RED) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return (int) (density * dp);
    }

    public static void clickWrap(final Context context, final View wrapper, final View inner, final int extraPaddingDpTop, final int extraPaddingDpRight, final int extraPaddingDpBottom, final int extraPaddingDpLeft) {
        if (wrapper != null) {
            wrapper.post(new Runnable() { // from class: com.heyzap.internal.Utils.1
                @Override // java.lang.Runnable
                public void run() {
                    Rect r = new Rect();
                    wrapper.getHitRect(r);
                    r.top -= Utils.getScaledSize(context, extraPaddingDpTop);
                    r.right += Utils.getScaledSize(context, extraPaddingDpRight);
                    r.bottom += Utils.getScaledSize(context, extraPaddingDpBottom);
                    r.left -= Utils.getScaledSize(context, extraPaddingDpLeft);
                    wrapper.setTouchDelegate(new TouchDelegate(r, inner));
                }
            });
        }
    }

    public static void clickWrap(Context context, View wrapper, View inner) {
        clickWrap(context, wrapper, inner, 0);
    }

    public static void clickWrap(Context context, View wrapper, View inner, int extraPaddingDp) {
        clickWrap(context, wrapper, inner, extraPaddingDp, extraPaddingDp, extraPaddingDp, extraPaddingDp);
    }

    public static boolean heyzapIsInstalled(Context context) {
        return packageIsInstalled(HEYZAP_PACKAGE, context);
    }

    public static boolean packageIsInstalled(String packageName2, Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            Intent pi = pm.getLaunchIntentForPackage(packageName2);
            if (pi == null) {
                return false;
            }
            List<ResolveInfo> list = pm.queryIntentActivities(pi, 65536);
            if (list.size() <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        return (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnectedOrConnecting() || isAirplaneModeOn(context)) ? false : true;
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static boolean isApplicationOnTop(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        String applicationPackageName = context.getApplicationContext().getPackageName();
        try {
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
            for (int i = 0; i < processes.size(); i++) {
                ActivityManager.RunningAppProcessInfo process = processes.get(i);
                if (process.processName.equals(applicationPackageName) && process.importance == 100) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.trace((Throwable) e);
        }
        return false;
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
        if (resourceId <= 0) {
            return 0;
        }
        int result = context.getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    public static boolean isFroyo() {
        return getSdkVersion() >= 8;
    }

    public static boolean isHoneycomb() {
        return getSdkVersion() >= 11;
    }

    public static boolean isGingerbread() {
        return getSdkVersion() >= 9;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isHoneycombTablet(Context context) {
        return isHoneycomb() && isTablet(context);
    }

    public static Boolean externalStorageIsAvailableAndWritable() {
        boolean mExternalStorageWriteable;
        boolean mExternalStorageAvailable;
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            mExternalStorageWriteable = true;
            mExternalStorageAvailable = true;
        } else if ("mounted_ro".equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageWriteable = false;
            mExternalStorageAvailable = false;
        }
        return Boolean.valueOf(mExternalStorageAvailable && mExternalStorageWriteable);
    }

    public static void deleteDirectory(File root) throws IOException {
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                deleteDirectory(file);
            }
        }
        root.delete();
    }

    public static String getCachePath(Context context, String withFileName) {
        if (context == null || withFileName == null) {
            return null;
        }
        return String.format("%s/%s", getCacheDirAbsolutePath(context), withFileName);
    }

    public static String getCacheDirAbsolutePath(Context context) {
        if (context != null) {
            return String.format("%s/%s", context.getCacheDir(), "com.heyzap.sdk");
        }
        return null;
    }

    public static String getImageCacheDirAbsolutePath(Context context) {
        if (context != null) {
            return String.format("%s/%s", context.getCacheDir(), IMAGE_CACHE_DIR);
        }
        return null;
    }

    public static void createCacheDir(Context context) {
        File dir = new File(getCacheDirAbsolutePath(context));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static Boolean isOnMainThread() {
        return Boolean.valueOf(Looper.getMainLooper() == Looper.myLooper());
    }

    public static Integer getPackageVersion(Context context) {
        int version = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Integer version2 = Integer.valueOf(pInfo.versionCode);
            return version2;
        } catch (Exception e) {
            Logger.trace((Throwable) e);
            return version;
        }
    }

    public static String hexString() {
        Random generator = new Random();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            output.append("ABCDEF1234567890".charAt(generator.nextInt("ABCDEF1234567890".length())));
        }
        return output.toString();
    }

    public static Boolean classExists(String klassName) {
        try {
            Class.forName(klassName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Boolean isExpired(Long startTime, Integer ttlMillis) {
        return ((long) ttlMillis.intValue()) < System.currentTimeMillis() - startTime.longValue();
    }

    public static boolean activityExistsInPackage(Activity activity, Class activityKlass) {
        Intent intent = new Intent(activity, (Class<?>) activityKlass);
        return intent.resolveActivityInfo(activity.getPackageManager(), 0) != null;
    }

    public static boolean activityExistsInPackage(Activity activity, String classString) {
        try {
            return activityExistsInPackage(activity, Class.forName(classString));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean packageHasPermissions(Activity activity, ArrayList<String> neededPermissions) {
        for (String permission : neededPermissions) {
            if (!packageHasPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean packageHasPermission(Activity activity, String permission) {
        PackageManager pm = activity.getPackageManager();
        return pm.checkPermission(permission, activity.getPackageName()) == 0;
    }

    public static Boolean packageHasReceiver(Activity activity, String receiverName) {
        List<PackageInfo> packs = activity.getPackageManager().getInstalledPackages(2);
        for (PackageInfo p : packs) {
            if (p.packageName.equals(getPackageName(activity))) {
                ActivityInfo[] receivers = p.receivers;
                if (receivers != null) {
                    for (ActivityInfo ai : receivers) {
                        if (ai.name.equals(receiverName)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static Boolean probablyHasGooglePlayServices(Activity activity) {
        Bundle metaData;
        List<PackageInfo> packs = activity.getPackageManager().getInstalledPackages(128);
        for (PackageInfo p : packs) {
            if (p.packageName.equals(getPackageName(activity))) {
                ApplicationInfo applicationInfo = p.applicationInfo;
                if (applicationInfo != null && (metaData = applicationInfo.metaData) != null && metaData.containsKey("com.google.android.gms.version")) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static void runOnMainThreadAndBlock(Activity activity, Integer timeoutMillis, final Runnable runnable) throws ExecutionException, InterruptedException, TimeoutException {
        if (Looper.myLooper() != null && Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        }
        final SettableFuture<Boolean> settableFuture = SettableFuture.create();
        Runnable r = new Runnable() { // from class: com.heyzap.internal.Utils.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    settableFuture.setException(e);
                } finally {
                    settableFuture.set(true);
                }
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(r);
        settableFuture.get(timeoutMillis.intValue(), TimeUnit.MILLISECONDS);
    }
}
