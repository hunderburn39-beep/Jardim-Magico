package com.tapjoy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.moat.analytics.mobile.tjy.MoatAdEvent;
import com.moat.analytics.mobile.tjy.MoatAdEventType;
import com.moat.analytics.mobile.tjy.MoatFactory;
import com.moat.analytics.mobile.tjy.ReactiveVideoTracker;
import com.moat.analytics.mobile.tjy.ReactiveVideoTrackerPlugin;
import com.tapjoy.internal.cr;
import com.tapjoy.mraid.view.MraidView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"SetJavaScriptEnabled"})
public class TJAdUnitJSBridge {
    private TJWebViewJSInterface a;
    public boolean allowRedirect;
    private TJAdUnitJSBridge b;
    private Context c;
    public boolean closeRequested;
    public boolean customClose;
    private TJAdUnit d;
    public boolean didLaunchOtherActivity;
    private WebView e;
    private ProgressDialog f;
    private LocationManager g;
    private LocationListener h;
    private View i;
    private boolean j;
    private ReactiveVideoTracker k;
    private HashMap l;
    private Handler m;
    public String otherActivityCallbackID;

    public interface AdUnitAsyncTaskListner {
        void onComplete(boolean z);
    }

    public TJAdUnitJSBridge(Context c, TJAdUnit tjAdUnit) {
        this(c, tjAdUnit.getWebView());
        this.d = tjAdUnit;
    }

    public TJAdUnitJSBridge(Context c, WebView w) {
        this.i = null;
        this.didLaunchOtherActivity = false;
        this.allowRedirect = true;
        this.otherActivityCallbackID = null;
        this.customClose = false;
        this.closeRequested = false;
        TapjoyLog.i("TJAdUnitJSBridge", "creating AdUnit/JS Bridge");
        this.c = c;
        this.e = w;
        this.b = this;
        if (this.e == null) {
            TapjoyLog.e("TJAdUnitJSBridge", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Cannot create AdUnitJSBridge -- webview is NULL"));
            return;
        }
        this.a = new TJWebViewJSInterface(this.e, new TJWebViewJSInterfaceListener() { // from class: com.tapjoy.TJAdUnitJSBridge.1
            @Override // com.tapjoy.TJWebViewJSInterfaceListener
            public final void onDispatchMethod(String methodName, JSONObject json) throws JSONException {
                String string;
                if (TJAdUnitJSBridge.this.j) {
                    try {
                        string = json.getString(TJAdUnitConstants.String.CALLBACK_ID);
                    } catch (Exception e) {
                        string = null;
                    }
                    try {
                        JSONObject jSONObject = json.getJSONObject("data");
                        Method method = TJAdUnitJSBridge.class.getMethod(methodName, JSONObject.class, String.class);
                        TapjoyLog.d("TJAdUnitJSBridge", "Dispatching method with method name=" + method + ";data=" + jSONObject + ";callbackID=" + string);
                        method.invoke(TJAdUnitJSBridge.this.b, jSONObject, string);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        TJAdUnitJSBridge.this.invokeJSCallback(string, Boolean.FALSE);
                    }
                }
            }
        });
        this.e.addJavascriptInterface(this.a, TJAdUnitConstants.JAVASCRIPT_INTERFACE_ID);
        this.j = true;
    }

    public void alert(JSONObject json, final String callbackID) throws JSONException {
        String string;
        Exception e;
        String str;
        JSONArray jSONArray;
        int i;
        TapjoyLog.d("TJAdUnitJSBridge", "alert_method: " + json);
        String string2 = "";
        try {
            string2 = json.getString("title");
            string = json.getString(TJAdUnitConstants.String.MESSAGE);
            try {
                str = string;
                jSONArray = json.getJSONArray(TJAdUnitConstants.String.BUTTONS);
            } catch (Exception e2) {
                e = e2;
                invokeJSCallback(callbackID, Boolean.FALSE);
                e.printStackTrace();
                str = string;
                jSONArray = null;
            }
        } catch (Exception e3) {
            string = "";
            e = e3;
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this.c).setTitle(string2).setMessage(str).create();
        if (jSONArray == null || jSONArray.length() == 0) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            switch (i2) {
                case 0:
                    i = -2;
                    break;
                case 1:
                    i = -3;
                    break;
                default:
                    i = -1;
                    break;
            }
            try {
                arrayList.add(jSONArray.getString(i2));
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            alertDialogCreate.setButton(i, (CharSequence) arrayList.get(i2), new DialogInterface.OnClickListener() { // from class: com.tapjoy.TJAdUnitJSBridge.5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialog, int which) {
                    int i3 = 0;
                    switch (which) {
                        case -3:
                            i3 = 1;
                            break;
                        case -1:
                            i3 = 2;
                            break;
                    }
                    try {
                        TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Integer.valueOf(i3));
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            });
        }
        alertDialogCreate.setCancelable(false);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.show();
    }

    public void checkAppInstalled(JSONObject json, String callbackID) throws JSONException {
        String string = "";
        try {
            string = json.getString(TJAdUnitConstants.String.BUNDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (string != null && string.length() > 0) {
            Iterator<ApplicationInfo> it = this.c.getPackageManager().getInstalledApplications(0).iterator();
            while (it.hasNext()) {
                if (it.next().packageName.equals(string)) {
                    invokeJSCallback(callbackID, Boolean.TRUE);
                    return;
                }
            }
        }
        invokeJSCallback(callbackID, Boolean.FALSE);
    }

    public void getInstalledAppData(JSONObject json, String callbackID) {
        PackageManager packageManager = this.c.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
        JSONArray jSONArray = new JSONArray();
        for (ApplicationInfo applicationInfo : installedApplications) {
            if ((applicationInfo.flags & 1) != 1) {
                HashMap map = new HashMap();
                String str = applicationInfo.packageName;
                map.put("packageName", str);
                map.put("targetSdk", Integer.valueOf(applicationInfo.targetSdkVersion));
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(str, 4096);
                    map.put("installTime", new Date(packageInfo.firstInstallTime));
                    map.put("updateTime", new Date(packageInfo.lastUpdateTime));
                    map.put("versionName", packageInfo.versionName);
                    map.put("versionCode", Integer.valueOf(packageInfo.versionCode));
                    jSONArray.put(new JSONObject(map));
                } catch (Exception e) {
                }
            }
        }
        invokeJSCallback(callbackID, jSONArray);
    }

    public void closeRequested(Boolean shouldForceClose) {
        this.closeRequested = true;
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.FORCE_CLOSE, shouldForceClose);
        invokeJSAdunitMethod(TJAdUnitConstants.String.CLOSE_REQUESTED, map);
    }

    public void getVolume(JSONObject json, String callbackID) {
        HashMap volumeArgs = getVolumeArgs();
        if (volumeArgs != null) {
            invokeJSCallback(callbackID, volumeArgs);
        } else {
            invokeJSCallback(callbackID, false);
        }
    }

    public void onVolumeChanged() {
        invokeJSAdunitMethod(TJAdUnitConstants.String.VOLUME_CHANGED, getVolumeArgs());
    }

    public HashMap getVolumeArgs() {
        if (this.d == null) {
            TapjoyLog.d("TJAdUnitJSBridge", "No ad unit provided");
            return null;
        }
        String volume = this.d.getVolume();
        boolean zIsMuted = this.d.isMuted();
        TapjoyLog.d("TJAdUnitJSBridge", "getVolumeArgs: volume=" + volume + "; isMuted=" + zIsMuted);
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.CURRENT_VOLUME, volume);
        map.put(TJAdUnitConstants.String.IS_MUTED, Boolean.valueOf(zIsMuted));
        return map;
    }

    public void destroy() {
        if (this.h != null && this.g != null) {
            this.g.removeUpdates(this.h);
            this.g = null;
            this.h = null;
        }
    }

    public void dismiss(JSONObject json, String callbackID) {
        if (this.c instanceof TJAdUnitActivity) {
            invokeJSCallback(callbackID, true);
            ((Activity) this.c).finish();
        } else {
            invokeJSCallback(callbackID, false);
        }
    }

    public void display() {
        invokeJSAdunitMethod(TJAdUnitConstants.String.DISPLAY, new Object[0]);
    }

    public void displayRichMedia(final JSONObject json, String callbackID) throws JSONException {
        boolean z;
        String string;
        try {
            z = json.getBoolean(TJAdUnitConstants.String.INLINE);
        } catch (Exception e) {
            z = false;
        }
        try {
            string = json.getString(TJAdUnitConstants.String.HTML);
        } catch (Exception e2) {
            e2.printStackTrace();
            string = null;
        }
        if (string == null) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            return;
        }
        if (z) {
            ((Activity) this.c).runOnUiThread(new Runnable() { // from class: com.tapjoy.TJAdUnitJSBridge.6
                @Override // java.lang.Runnable
                public final void run() {
                    String string2;
                    try {
                        string2 = json.getString(TJAdUnitConstants.String.HTML);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        string2 = null;
                    }
                    if (TJAdUnitJSBridge.this.i != null && TJAdUnitJSBridge.this.i.getParent() != null) {
                        ((ViewGroup) TJAdUnitJSBridge.this.i.getParent()).removeView(TJAdUnitJSBridge.this.i);
                    }
                    MraidView mraidView = new MraidView(TJAdUnitJSBridge.this.c);
                    TJAdUnitJSBridge.this.e.getSettings().setJavaScriptEnabled(true);
                    mraidView.setPlacementType(MraidView.PLACEMENT_TYPE.INLINE);
                    mraidView.setLayoutParams(new ViewGroup.LayoutParams(640, 100));
                    mraidView.setInitialScale(100);
                    mraidView.setBackgroundColor(0);
                    mraidView.loadDataWithBaseURL(null, string2, "text/html", "utf-8", null);
                    int width = ((WindowManager) ((Activity) TJAdUnitJSBridge.this.c).getSystemService("window")).getDefaultDisplay().getWidth();
                    TJAdUnitJSBridge.this.i = TapjoyUtil.scaleDisplayAd(mraidView, width);
                    ((Activity) TJAdUnitJSBridge.this.c).addContentView(TJAdUnitJSBridge.this.i, new ViewGroup.LayoutParams(width, (int) (100.0d * (((double) width) / 640.0d))));
                }
            });
            return;
        }
        TJPlacementData tJPlacementData = new TJPlacementData(TapjoyConnectCore.getHostURL(), string, callbackID);
        Intent intent = new Intent(this.c, (Class<?>) TJAdUnitActivity.class);
        intent.putExtra(TJAdUnitConstants.EXTRA_TJ_PLACEMENT_DATA, tJPlacementData);
        ((Activity) this.c).startActivityForResult(intent, TJAdUnitConstants.MRAID_REQUEST_CODE);
    }

    public void displayStoreURL(JSONObject json, String callbackID) {
        displayURL(json, callbackID);
    }

    public void displayURL(JSONObject json, String callbackID) {
        try {
            String string = json.getString("url");
            this.didLaunchOtherActivity = true;
            this.otherActivityCallbackID = callbackID;
            ((Activity) this.c).startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string)));
        } catch (Exception e) {
            invokeJSCallback(callbackID, Boolean.TRUE);
            e.printStackTrace();
        }
    }

    public void clearCache(JSONObject json, String callbackID) {
        if (TapjoyCache.getInstance() != null) {
            TapjoyCache.getInstance().clearTapjoyCache();
            invokeJSCallback(callbackID, Boolean.TRUE);
        } else {
            invokeJSCallback(callbackID, Boolean.FALSE);
        }
    }

    public void setPrerenderLimit(JSONObject json, String callbackID) {
        try {
            TJPlacementManager.setPreRenderedPlacementLimit(json.getInt(TJAdUnitConstants.String.TJC_PLACEMENT_PRE_RENDERED_LIMIT));
            invokeJSCallback(callbackID, Boolean.TRUE);
        } catch (Exception e) {
            TapjoyLog.w("TJAdUnitJSBridge", "Unable to set Tapjoy placement pre-render limit. Invalid parameters.");
            invokeJSCallback(callbackID, Boolean.FALSE);
        }
    }

    public void setEventPreloadLimit(JSONObject json, String callbackID) {
        if (TapjoyCache.getInstance() != null) {
            try {
                TJPlacementManager.setCachedPlacementLimit(json.getInt(TJAdUnitConstants.String.TJC_PLACEMENT_CACHE_LIMIT));
                invokeJSCallback(callbackID, Boolean.TRUE);
                return;
            } catch (Exception e) {
                TapjoyLog.w("TJAdUnitJSBridge", "Unable to set Tapjoy cache's event preload limit. Invalid parameters.");
                invokeJSCallback(callbackID, Boolean.FALSE);
                return;
            }
        }
        invokeJSCallback(callbackID, Boolean.FALSE);
    }

    public void removeAssetFromCache(JSONObject json, String callbackID) {
        try {
            String assetURL = json.getString("url");
            if (TapjoyCache.getInstance() != null) {
                invokeJSCallback(callbackID, Boolean.valueOf(TapjoyCache.getInstance().removeAssetFromCache(assetURL)));
            } else {
                invokeJSCallback(callbackID, Boolean.FALSE);
            }
        } catch (Exception e) {
            TapjoyLog.w("TJAdUnitJSBridge", "Unable to cache video. Invalid parameters.");
            invokeJSCallback(callbackID, Boolean.FALSE);
        }
    }

    public void cacheAsset(JSONObject json, String callbackID) {
        String string = "";
        Long lValueOf = 0L;
        try {
            String assetURL = json.getString("url");
            try {
                string = json.getString(TapjoyConstants.TJC_PLACEMENT_OFFER_ID);
            } catch (Exception e) {
            }
            try {
                lValueOf = Long.valueOf(json.getLong(TapjoyConstants.TJC_TIME_TO_LIVE));
            } catch (Exception e2) {
            }
            if (TapjoyCache.getInstance() != null) {
                invokeJSCallback(callbackID, TapjoyCache.getInstance().cacheAssetFromURL(assetURL, string, lValueOf.longValue()));
            } else {
                invokeJSCallback(callbackID, Boolean.FALSE);
            }
        } catch (Exception e3) {
            TapjoyLog.w("TJAdUnitJSBridge", "Unable to cache video. Invalid parameters.");
            invokeJSCallback(callbackID, Boolean.FALSE);
        }
    }

    public void cachePathForURL(JSONObject json, String callbackID) {
        try {
            String url = json.getString("url");
            if (TapjoyCache.getInstance() != null) {
                invokeJSCallback(callbackID, TapjoyCache.getInstance().getPathOfCachedURL(url));
            } else {
                invokeJSCallback(callbackID, "");
            }
        } catch (Exception e) {
            invokeJSCallback(callbackID, "");
        }
    }

    public void getCachedAssets(JSONObject json, String callbackID) {
        if (TapjoyCache.getInstance() != null) {
            invokeJSCallback(callbackID, TapjoyCache.getInstance().cachedAssetsToJSON());
        } else {
            invokeJSCallback(callbackID, "");
        }
    }

    public void contentReady(JSONObject json, String callbackID) {
        if (this.d != null) {
            this.d.fireContentReady();
            invokeJSCallback(callbackID, true);
        } else {
            invokeJSCallback(callbackID, false);
        }
    }

    public void setOrientation(JSONObject json, String callbackID) {
        int i;
        if (this.d == null) {
            TapjoyLog.d("TJAdUnitJSBridge", "No ad unit provided");
            invokeJSCallback(callbackID, false);
            return;
        }
        try {
            String string = json.getString("orientation");
            if (string.equals(TJAdUnitConstants.String.LANDSCAPE) || string.equals(TJAdUnitConstants.String.LANDSCAPE_LEFT)) {
                i = 0;
            } else {
                i = string.equals(TJAdUnitConstants.String.LANDSCAPE_RIGHT) ? 8 : 1;
            }
            this.d.setOrientation(i);
            invokeJSCallback(callbackID, true);
        } catch (Exception e) {
            invokeJSCallback(callbackID, false);
        }
    }

    public void setBackgroundColor(JSONObject json, final String callbackID) {
        try {
            String backgroundHexColor = json.getString(TJAdUnitConstants.String.BACKGROUND_COLOR);
            if (this.d != null) {
                this.d.setBackgroundColor(backgroundHexColor, new AdUnitAsyncTaskListner() { // from class: com.tapjoy.TJAdUnitJSBridge.7
                    @Override // com.tapjoy.TJAdUnitJSBridge.AdUnitAsyncTaskListner
                    public final void onComplete(boolean result) {
                        TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.valueOf(result));
                    }
                });
            } else {
                invokeJSCallback(callbackID, false);
            }
        } catch (Exception e) {
            TapjoyLog.w("TJAdUnitJSBridge", "Unable to set background color. Invalid parameters.");
            invokeJSCallback(callbackID, false);
        }
    }

    public void setBackgroundWebViewContent(JSONObject json, final String callbackID) {
        TapjoyLog.d("TJAdUnitJSBridge", "setBackgroundWebViewContent");
        try {
            String string = json.getString(TJAdUnitConstants.String.BACKGROUND_CONTENT);
            if (this.d != null) {
                this.d.setBackgroundContent(string, new AdUnitAsyncTaskListner() { // from class: com.tapjoy.TJAdUnitJSBridge.8
                    @Override // com.tapjoy.TJAdUnitJSBridge.AdUnitAsyncTaskListner
                    public final void onComplete(boolean result) {
                        TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.valueOf(result));
                    }
                });
            } else {
                invokeJSCallback(callbackID, false);
            }
        } catch (Exception e) {
            TapjoyLog.w("TJAdUnitJSBridge", "Unable to set background content. Invalid parameters.");
            invokeJSCallback(callbackID, false);
        }
    }

    public void displayVideo(JSONObject json, final String callbackID) {
        try {
            String url = json.getString("url");
            if (url.length() > 0 && url != "") {
                this.d.loadVideoUrl(url, new AdUnitAsyncTaskListner() { // from class: com.tapjoy.TJAdUnitJSBridge.9
                    @Override // com.tapjoy.TJAdUnitJSBridge.AdUnitAsyncTaskListner
                    public final void onComplete(boolean result) {
                        TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.valueOf(result));
                    }
                });
            } else {
                invokeJSCallback(callbackID, Boolean.FALSE);
            }
        } catch (Exception e) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    public void playVideo(JSONObject json, String callbackID) {
        if (this.d != null) {
            invokeJSCallback(callbackID, Boolean.valueOf(this.d.playVideo()));
        }
    }

    public void pauseVideo(JSONObject json, String callbackID) {
        if (this.d != null) {
            invokeJSCallback(callbackID, Boolean.valueOf(this.d.pauseVideo()));
        }
    }

    public void clearVideo(JSONObject json, final String callbackID) {
        if (this.d != null) {
            this.d.clearVideo(new AdUnitAsyncTaskListner() { // from class: com.tapjoy.TJAdUnitJSBridge.10
                @Override // com.tapjoy.TJAdUnitJSBridge.AdUnitAsyncTaskListner
                public final void onComplete(boolean result) {
                    TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.valueOf(result));
                }
            });
        }
    }

    public void getLocation(JSONObject json, String callbackID) {
        boolean zBooleanValue;
        float fFloatValue = 100.0f;
        try {
            fFloatValue = Float.valueOf(json.getString(TJAdUnitConstants.String.GPS_ACCURACY)).floatValue();
        } catch (Exception e) {
        }
        try {
            zBooleanValue = Boolean.valueOf(json.getString(TJAdUnitConstants.String.ENABLED)).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            zBooleanValue = false;
        }
        this.g = (LocationManager) this.c.getSystemService("location");
        String bestProvider = this.g.getBestProvider(new Criteria(), false);
        if (this.h == null) {
            this.h = new LocationListener() { // from class: com.tapjoy.TJAdUnitJSBridge.11
                @Override // android.location.LocationListener
                public final void onLocationChanged(Location location) {
                    if (TJAdUnitJSBridge.this.c == null || TJAdUnitJSBridge.this.e == null) {
                        if (TJAdUnitJSBridge.this.g != null && TJAdUnitJSBridge.this.h != null) {
                            TapjoyLog.d("TJAdUnitJSBridge", "stopping updates");
                            TJAdUnitJSBridge.this.g.removeUpdates(TJAdUnitJSBridge.this.h);
                            return;
                        }
                        return;
                    }
                    if (location != null) {
                        HashMap map = new HashMap();
                        map.put(TJAdUnitConstants.String.LAT, Double.valueOf(location.getLatitude()));
                        map.put(TJAdUnitConstants.String.LONG, Double.valueOf(location.getLongitude()));
                        map.put(TJAdUnitConstants.String.ALTITUDE, Double.valueOf(location.getAltitude()));
                        map.put("timestamp", Long.valueOf(location.getTime()));
                        map.put(TJAdUnitConstants.String.SPEED, Float.valueOf(location.getSpeed()));
                        map.put(TJAdUnitConstants.String.COURSE, Float.valueOf(location.getBearing()));
                        TJAdUnitJSBridge.this.invokeJSAdunitMethod(TJAdUnitConstants.String.LOCATION_UPDATED, map);
                    }
                }

                @Override // android.location.LocationListener
                public final void onProviderEnabled(String p) {
                }

                @Override // android.location.LocationListener
                public final void onProviderDisabled(String p) {
                }

                @Override // android.location.LocationListener
                public final void onStatusChanged(String p, int status, Bundle extras) {
                }
            };
        }
        if (zBooleanValue) {
            if (bestProvider != null) {
                this.g.requestLocationUpdates(bestProvider, 0L, fFloatValue, this.h);
            } else {
                invokeJSCallback(callbackID, Boolean.FALSE);
                return;
            }
        } else if (this.g != null && this.h != null) {
            this.g.removeUpdates(this.h);
        }
        invokeJSCallback(callbackID, Boolean.TRUE);
    }

    public void log(JSONObject json, String callbackID) {
        try {
            TapjoyLog.d("TJAdUnitJSBridge", "Logging message=" + json.getString(TJAdUnitConstants.String.MESSAGE));
            invokeJSCallback(callbackID, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    public void openApp(JSONObject json, String callbackID) {
        try {
            this.c.startActivity(this.c.getPackageManager().getLaunchIntentForPackage(json.getString(TJAdUnitConstants.String.BUNDLE)));
            invokeJSCallback(callbackID, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void nativeEval(final JSONObject json, final String callbackID) {
        ((Activity) this.c).runOnUiThread(new Runnable() { // from class: com.tapjoy.TJAdUnitJSBridge.12
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (Build.VERSION.SDK_INT >= 19) {
                        TJAdUnitJSBridge.this.e.evaluateJavascript(json.getString(TJAdUnitConstants.String.COMMAND), null);
                    } else {
                        TJAdUnitJSBridge.this.e.loadUrl("javascript:" + json.getString(TJAdUnitConstants.String.COMMAND));
                    }
                    TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.TRUE);
                } catch (Exception e) {
                    TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.FALSE);
                }
            }
        });
    }

    public void present(JSONObject json, String callbackID) {
        try {
            Boolean.valueOf(false);
            boolean zValueOf = false;
            Boolean boolValueOf = Boolean.valueOf(json.getString("visible"));
            try {
                zValueOf = Boolean.valueOf(json.getString(TJAdUnitConstants.String.TRANSPARENT));
            } catch (Exception e) {
            }
            try {
                this.customClose = Boolean.valueOf(json.getString(TJAdUnitConstants.String.CUSTOM_CLOSE)).booleanValue();
            } catch (Exception e2) {
            }
            new a(this.e).execute(boolValueOf, zValueOf);
            invokeJSCallback(callbackID, Boolean.TRUE);
        } catch (Exception e3) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            e3.printStackTrace();
        }
    }

    public void triggerEvent(JSONObject json, String callbackID) {
        if (this.d != null) {
            try {
                String string = json.getString("eventName");
                if (string.equals(TJAdUnitConstants.String.VIDEO_START)) {
                    this.d.fireOnVideoStart();
                } else if (string.equals(TJAdUnitConstants.String.VIDEO_COMPLETE)) {
                    this.d.fireOnVideoComplete();
                } else if (string.equals(TJAdUnitConstants.String.VIDEO_ERROR)) {
                    this.d.fireOnVideoError("Error while trying to play video.");
                }
            } catch (Exception e) {
                TapjoyLog.w("TJAdUnitJSBridge", "Unable to triggerEvent. No event name.");
            }
        }
    }

    public void invokeJSAdunitMethod(String methodName, Object... args) {
        this.a.callback(new ArrayList(Arrays.asList(args)), methodName, (String) null);
    }

    public void invokeJSAdunitMethod(String methodName, Map map) {
        this.a.callback(map, methodName, (String) null);
    }

    public void invokeJSCallback(String callbackID, Object... argArray) {
        if (cr.c(callbackID)) {
            TapjoyLog.d("TJAdUnitJSBridge", "invokeJSCallback -- no callbackID provided");
        } else {
            this.a.callback(new ArrayList(Arrays.asList(argArray)), "", callbackID);
        }
    }

    public void invokeJSCallback(String callbackID, Map map) {
        this.a.callback(map, "", callbackID);
    }

    public void flushMessageQueue() {
        this.a.flushMessageQueue();
    }

    public void setAllowRedirect(JSONObject json, String callbackID) {
        boolean z;
        try {
            z = json.getBoolean(TJAdUnitConstants.String.ENABLED);
        } catch (Exception e) {
            z = true;
        }
        this.allowRedirect = z;
        invokeJSCallback(callbackID, Boolean.TRUE);
    }

    public void setContext(Context c) {
        this.c = c;
    }

    public void setSpinnerVisible(JSONObject json, String callbackID) throws JSONException {
        String str;
        String string;
        String string2 = TJAdUnitConstants.SPINNER_TITLE;
        try {
            boolean visible = json.getBoolean("visible");
            try {
                string2 = json.getString("title");
                str = string2;
                string = json.getString(TJAdUnitConstants.String.MESSAGE);
            } catch (Exception e) {
                str = string2;
                string = "";
            }
            if (visible) {
                this.f = ProgressDialog.show(this.c, str, string);
            } else if (this.f != null) {
                this.f.dismiss();
            }
            invokeJSCallback(callbackID, Boolean.TRUE);
        } catch (Exception e2) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            e2.printStackTrace();
        }
    }

    public void setCloseButtonVisible(JSONObject json, String callbackID) {
        if (!(this.c instanceof TJAdUnitActivity)) {
            TapjoyLog.d("TJAdUnitJSBridge", "Not a TJAdUnitActivity");
            invokeJSCallback(callbackID, false);
            return;
        }
        try {
            final boolean z = json.getBoolean("visible");
            ((Activity) this.c).runOnUiThread(new Runnable() { // from class: com.tapjoy.TJAdUnitJSBridge.2
                @Override // java.lang.Runnable
                public final void run() {
                    ((TJAdUnitActivity) TJAdUnitJSBridge.this.c).setCloseButtonVisibility(z);
                }
            });
            invokeJSCallback(callbackID, true);
        } catch (Exception e) {
            invokeJSCallback(callbackID, false);
            e.printStackTrace();
        }
    }

    public void shouldClose(JSONObject json, String callbackID) {
        try {
            Boolean.valueOf(false);
            if (Boolean.valueOf(json.getString(TJAdUnitConstants.String.CLOSE)).booleanValue() && (this.c instanceof Activity)) {
                ((Activity) this.c).finish();
            }
            invokeJSCallback(callbackID, Boolean.TRUE);
        } catch (Exception e) {
            invokeJSCallback(callbackID, Boolean.FALSE);
            ((Activity) this.c).finish();
            e.printStackTrace();
        }
        this.closeRequested = false;
    }

    public void setLoggingLevel(JSONObject json, String callbackID) {
        try {
            TapjoyAppSettings.getInstance().saveLoggingLevel(String.valueOf(json.getString(TJAdUnitConstants.String.LOGGING_LEVEL)));
        } catch (Exception e) {
            TapjoyLog.d("TJAdUnitJSBridge", "setLoggingLevel exception " + e.getLocalizedMessage());
            invokeJSCallback(callbackID, false);
            e.printStackTrace();
        }
    }

    public void clearLoggingLevel(JSONObject json, String callbackID) {
        TapjoyAppSettings.getInstance().clearLoggingLevel();
    }

    public void attachVolumeListener(JSONObject json, String callbackID) {
        try {
            boolean z = json.getBoolean(TJAdUnitConstants.String.ATTACH);
            int iOptInt = json.optInt(TJAdUnitConstants.String.INTERVAL, TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL);
            if (iOptInt > 0) {
                this.d.attachVolumeListener(z, iOptInt);
                invokeJSCallback(callbackID, true);
            } else {
                TapjoyLog.d("TJAdUnitJSBridge", "Invalid `interval` value passed to attachVolumeListener(): interval=" + iOptInt);
                invokeJSCallback(callbackID, false);
            }
        } catch (Exception e) {
            TapjoyLog.d("TJAdUnitJSBridge", "attachVolumeListener exception " + e.toString());
            invokeJSCallback(callbackID, false);
            e.printStackTrace();
        }
    }

    public void initMoatVideoTracker(JSONObject json, String callbackID) {
        if (!(this.c instanceof TJAdUnitActivity)) {
            TapjoyLog.d("TJAdUnitJSBridge", "Error from initMoatVideoTracker -- not a TJAdUnitActivity");
            invokeJSCallback(callbackID, false);
            return;
        }
        try {
            this.k = (ReactiveVideoTracker) MoatFactory.create((TJAdUnitActivity) this.c).createCustomTracker(new ReactiveVideoTrackerPlugin(json.getString(TJAdUnitConstants.String.PARTNER_CODE)));
            if (this.l == null) {
                TapjoyLog.d("TJAdUnitJSBridge", "Initializing MOAT tracking events map");
                this.l = new HashMap();
                this.l.put(TJAdUnitConstants.String.VIDEO_FIRST_QUARTILE, MoatAdEventType.AD_EVT_FIRST_QUARTILE);
                this.l.put(TJAdUnitConstants.String.VIDEO_MIDPOINT, MoatAdEventType.AD_EVT_MID_POINT);
                this.l.put(TJAdUnitConstants.String.VIDEO_THIRD_QUARTILE, MoatAdEventType.AD_EVT_THIRD_QUARTILE);
                this.l.put(TJAdUnitConstants.String.VIDEO_COMPLETE, MoatAdEventType.AD_EVT_COMPLETE);
                this.l.put(TJAdUnitConstants.String.VIDEO_PAUSED, MoatAdEventType.AD_EVT_PAUSED);
                this.l.put(TJAdUnitConstants.String.VIDEO_PLAYING, MoatAdEventType.AD_EVT_PLAYING);
                this.l.put(TJAdUnitConstants.String.VIDEO_START, MoatAdEventType.AD_EVT_START);
                this.l.put(TJAdUnitConstants.String.VIDEO_STOPPED, MoatAdEventType.AD_EVT_STOPPED);
                this.l.put(TJAdUnitConstants.String.VIDEO_SKIPPED, MoatAdEventType.AD_EVT_SKIPPED);
                this.l.put(TJAdUnitConstants.String.VOLUME_CHANGED, MoatAdEventType.AD_EVT_VOLUME_CHANGE);
                this.l.put(TJAdUnitConstants.String.ENTER_FULL_SCREEN, MoatAdEventType.AD_EVT_ENTER_FULLSCREEN);
                this.l.put(TJAdUnitConstants.String.EXIT_FULL_SCREEN, MoatAdEventType.AD_EVT_EXIT_FULLSCREEN);
            }
            this.m = new Handler(Looper.getMainLooper());
            invokeJSCallback(callbackID, true);
        } catch (Exception e) {
            TapjoyLog.d("TJAdUnitJSBridge", "initMoatVideoTracker exception " + e.toString());
            invokeJSCallback(callbackID, false);
        }
    }

    public void startMoatVideoTracker(JSONObject json, final String callbackID) {
        try {
            final Integer numValueOf = Integer.valueOf(json.getInt(TJAdUnitConstants.String.VIDEO_LENGTH));
            final HashMap map = new HashMap();
            JSONObject jSONObject = json.getJSONObject(TJAdUnitConstants.String.AD_IDS);
            if (jSONObject != null) {
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.getString(next));
                }
            }
            this.m.post(new Runnable() { // from class: com.tapjoy.TJAdUnitJSBridge.3
                @Override // java.lang.Runnable
                public final void run() {
                    TJAdUnitJSBridge.this.invokeJSCallback(callbackID, Boolean.valueOf(TJAdUnitJSBridge.this.k != null ? TJAdUnitJSBridge.this.k.trackVideoAd(map, numValueOf, TJAdUnitJSBridge.this.e) : false));
                }
            });
        } catch (Exception e) {
            TapjoyLog.d("TJAdUnitJSBridge", "startMoatVideoTracker exception " + e.toString());
            invokeJSCallback(callbackID, false);
        }
    }

    public void triggerMoatVideoEvent(JSONObject json, String callbackID) {
        try {
            Integer numValueOf = Integer.valueOf(json.getInt(TJAdUnitConstants.String.CURRENT_VIDEO_TIME));
            String string = json.getString("eventName");
            MoatAdEventType moatAdEventType = null;
            if (this.l != null) {
                moatAdEventType = (MoatAdEventType) this.l.get(string);
            }
            if (moatAdEventType == null) {
                TapjoyLog.d("TJAdUnitJSBridge", "eventName:" + string + " has no matching MOAT event");
                invokeJSCallback(callbackID, false);
            } else {
                TapjoyLog.d("TJAdUnitJSBridge", "Sending MOAT event: " + moatAdEventType);
                final MoatAdEvent moatAdEvent = new MoatAdEvent(moatAdEventType, numValueOf);
                this.m.post(new Runnable() { // from class: com.tapjoy.TJAdUnitJSBridge.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (TJAdUnitJSBridge.this.k != null) {
                            TJAdUnitJSBridge.this.k.dispatchEvent(moatAdEvent);
                        }
                    }
                });
                invokeJSCallback(callbackID, true);
            }
        } catch (Exception e) {
            TapjoyLog.d("TJAdUnitJSBridge", "triggerMoatVideoEvent exception " + e.toString());
            invokeJSCallback(callbackID, false);
        }
    }

    public void disable() {
        this.j = false;
    }

    @TargetApi(11)
    class a extends AsyncTask {
        WebView a;

        @Override // android.os.AsyncTask
        protected final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return (Boolean[]) objArr;
        }

        @Override // android.os.AsyncTask
        protected final /* synthetic */ void onPostExecute(Object obj) {
            Boolean[] boolArr = (Boolean[]) obj;
            final boolean zBooleanValue = boolArr[0].booleanValue();
            final boolean zBooleanValue2 = boolArr[1].booleanValue();
            if (TJAdUnitJSBridge.this.c instanceof Activity) {
                ((Activity) TJAdUnitJSBridge.this.c).runOnUiThread(new Runnable() { // from class: com.tapjoy.TJAdUnitJSBridge.a.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (zBooleanValue) {
                            a.this.a.setVisibility(0);
                            if (zBooleanValue2) {
                                if (a.this.a.getParent() instanceof RelativeLayout) {
                                    ((RelativeLayout) a.this.a.getParent()).getBackground().setAlpha(0);
                                    ((RelativeLayout) a.this.a.getParent()).setBackgroundColor(0);
                                }
                                if (Build.VERSION.SDK_INT >= 11) {
                                    a.this.a.setLayerType(1, null);
                                    return;
                                }
                                return;
                            }
                            if (a.this.a.getParent() instanceof RelativeLayout) {
                                ((RelativeLayout) a.this.a.getParent()).getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
                                ((RelativeLayout) a.this.a.getParent()).setBackgroundColor(-1);
                            }
                            if (Build.VERSION.SDK_INT >= 11) {
                                a.this.a.setLayerType(0, null);
                                return;
                            }
                            return;
                        }
                        a.this.a.setVisibility(4);
                        if (a.this.a.getParent() instanceof RelativeLayout) {
                            ((RelativeLayout) a.this.a.getParent()).getBackground().setAlpha(0);
                            ((RelativeLayout) a.this.a.getParent()).setBackgroundColor(0);
                        }
                    }
                });
            } else {
                TapjoyLog.e("TJAdUnitJSBridge", "Unable to present offerwall. No Activity context provided.");
            }
        }

        public a(WebView webView) {
            this.a = webView;
        }
    }

    public void onVideoReady(int videoDuration, int videoWidth, int videoHeight) {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_READY_EVENT);
        map.put(TJAdUnitConstants.String.VIDEO_DURATION, Integer.valueOf(videoDuration));
        map.put(TJAdUnitConstants.String.VIDEO_WIDTH, Integer.valueOf(videoWidth));
        map.put(TJAdUnitConstants.String.VIDEO_HEIGHT, Integer.valueOf(videoHeight));
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }

    public void onVideoStarted(int currentTime) {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_START_EVENT);
        map.put(TJAdUnitConstants.String.VIDEO_CURRENT_TIME, Integer.valueOf(currentTime));
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }

    public void onVideoProgress(int currentTime) {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_PROGRESS_EVENT);
        map.put(TJAdUnitConstants.String.VIDEO_CURRENT_TIME, Integer.valueOf(currentTime));
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }

    public void onVideoPaused(int currentTime) {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_PAUSE_EVENT);
        map.put(TJAdUnitConstants.String.VIDEO_CURRENT_TIME, Integer.valueOf(currentTime));
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }

    public void onVideoCompletion() {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_COMPLETE_EVENT);
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }

    public void onVideoInfo(String infoMessage) {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_INFO_EVENT);
        map.put(TJAdUnitConstants.String.VIDEO_INFO, infoMessage);
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }

    public void onVideoError(String errorMessage) {
        HashMap map = new HashMap();
        map.put(TJAdUnitConstants.String.VIDEO_EVENT_NAME, TJAdUnitConstants.String.VIDEO_ERROR_EVENT);
        invokeJSAdunitMethod(TJAdUnitConstants.String.VIDEO_EVENT, map);
    }
}
