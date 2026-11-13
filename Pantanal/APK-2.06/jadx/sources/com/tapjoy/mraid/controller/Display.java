package com.tapjoy.mraid.controller;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.util.ConfigBroadcastReceiver;
import com.tapjoy.mraid.view.MraidView;
import java.util.List;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class Display extends Abstract {
    private WindowManager c;
    private boolean d;
    private int e;
    private int f;
    private ConfigBroadcastReceiver g;
    private float h;
    private Context i;

    public Display(MraidView adView, Context c) {
        super(adView, c);
        this.d = false;
        this.e = -1;
        this.f = -1;
        this.i = c;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.c = (WindowManager) c.getSystemService("window");
        this.c.getDefaultDisplay().getMetrics(displayMetrics);
        this.h = displayMetrics.density;
    }

    @JavascriptInterface
    public void open(String url, boolean back, boolean forward, boolean refresh) {
        TapjoyLog.i("MRAID Display", "open: url: " + url + " back: " + back + " forward: " + forward + " refresh: " + refresh);
        if (!URLUtil.isValidUrl(url)) {
            TapjoyLog.i("MRAID Display", "invalid URL");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            List<ResolveInfo> listQueryIntentActivities = this.i.getPackageManager().queryIntentActivities(intent, 0);
            if (listQueryIntentActivities.size() == 1) {
                this.i.startActivity(intent);
                return;
            } else if (listQueryIntentActivities.size() > 1) {
                ((Activity) this.i).startActivity(Intent.createChooser(intent, TJAdUnitConstants.SHARE_CHOOSE_TITLE));
                return;
            } else {
                this.a.raiseError("Invalid url", "open");
                return;
            }
        }
        this.a.open(url, back, forward, refresh);
    }

    @JavascriptInterface
    public void useCustomClose(boolean input) {
        if (!input) {
            if (!input) {
                this.a.showCloseImageButton();
                return;
            }
            return;
        }
        this.a.removeCloseImageButton();
    }

    @JavascriptInterface
    public void openMap(String url, boolean fullscreen) {
        TapjoyLog.d("MRAID Display", "openMap: url: " + url);
        this.a.openMap(url, fullscreen);
    }

    @JavascriptInterface
    public void setOrientationProperties(boolean allowOrientationChange, String forceOrientation) {
        TapjoyLog.d("MRAID Display", "setOrientationProperties: allowOrientationChange: " + Boolean.toString(allowOrientationChange) + " forceOrientation: " + forceOrientation);
        this.a.setOrientationProperties(allowOrientationChange, forceOrientation);
    }

    @JavascriptInterface
    public void playAudio(String url, boolean autoPlay, boolean controls, boolean loop, boolean position, String startStyle, String stopStyle) {
        TapjoyLog.d("MRAID Display", "playAudio: url: " + url + " autoPlay: " + autoPlay + " controls: " + controls + " loop: " + loop + " position: " + position + " startStyle: " + startStyle + " stopStyle: " + stopStyle);
        if (!URLUtil.isValidUrl(url)) {
            this.a.raiseError("Invalid url", "playAudio");
        } else {
            this.a.playAudio(url, autoPlay, controls, loop, position, startStyle, stopStyle);
        }
    }

    @JavascriptInterface
    public void playVideo(String url, boolean audioMuted, boolean autoPlay, boolean controls, boolean loop, int[] position, String startStyle, String stopStyle) {
        TapjoyLog.d("MRAID Display", "playVideo: url: " + url + " audioMuted: " + audioMuted + " autoPlay: " + autoPlay + " controls: " + controls + " loop: " + loop + " x: " + position[0] + " y: " + position[1] + " width: " + position[2] + " height: " + position[3] + " startStyle: " + startStyle + " stopStyle: " + stopStyle);
        Abstract.Dimensions dimensions = null;
        if (position[0] != -1) {
            dimensions = new Abstract.Dimensions();
            dimensions.x = position[0];
            dimensions.y = position[1];
            dimensions.width = position[2];
            dimensions.height = position[3];
            dimensions.width = (int) Math.ceil(this.h * dimensions.width);
            dimensions.height = (int) Math.ceil(this.h * dimensions.height);
            dimensions.x = (int) (dimensions.x * this.h);
            dimensions.y = (int) (dimensions.y * this.h);
            if (dimensions.height < 0) {
                dimensions.height = this.a.getHeight();
            }
            if (dimensions.width < 0) {
                dimensions.width = this.a.getWidth();
            }
            int[] iArr = new int[2];
            this.a.getLocationInWindow(iArr);
            if (dimensions.x < 0) {
                dimensions.x = iArr[0];
            }
            if (dimensions.y < 0) {
                dimensions.y = iArr[1] + 0;
            }
        }
        int i = 0;
        if (url.contains("android.resource")) {
            try {
                i = R.raw.class.getField(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."))).getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            url = "android.resource://" + this.b.getPackageName() + "/" + i;
        }
        this.a.playVideo(url, false, true, true, false, dimensions, Abstract.FULL_SCREEN, Abstract.EXIT);
    }

    @JavascriptInterface
    public void close() {
        TapjoyLog.d("MRAID Display", TJAdUnitConstants.String.CLOSE);
        this.a.close();
    }

    @JavascriptInterface
    public void show() {
        TapjoyLog.d("MRAID Display", "show");
        this.a.show();
    }

    public boolean isVisible() {
        return this.a.getVisibility() == 0;
    }

    public String dimensions() {
        return "{ \"top\" :" + ((int) (this.a.getTop() / this.h)) + ",\"left\" :" + ((int) (this.a.getLeft() / this.h)) + ",\"bottom\" :" + ((int) (this.a.getBottom() / this.h)) + ",\"right\" :" + ((int) (this.a.getRight() / this.h)) + "}";
    }

    public int getOrientation() {
        int i = -1;
        switch (this.c.getDefaultDisplay().getOrientation()) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 90;
                break;
            case 2:
                i = 180;
                break;
            case 3:
                i = 270;
                break;
        }
        TapjoyLog.d("MRAID Display", "getOrientation: " + i);
        return i;
    }

    public String getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.c.getDefaultDisplay().getMetrics(displayMetrics);
        return "{ width: " + ((int) Math.ceil(displayMetrics.widthPixels / displayMetrics.density)) + ", height: " + ((int) Math.ceil(displayMetrics.heightPixels / displayMetrics.density)) + "}";
    }

    public String getSize() {
        return this.a.getSize();
    }

    public String getMaxSize() {
        return this.d ? "{ width: " + this.e + ", height: " + this.f + "}" : getScreenSize();
    }

    public void setMaxSize(int w, int h) {
        TapjoyLog.d("MRAID Display", "setMaxSize: " + w + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + h);
        this.d = true;
        this.e = w;
        this.f = h;
    }

    public void onOrientationChanged(int orientation) {
        String str = "window.mraidview.fireChangeEvent({ orientation: " + orientation + "});";
        TapjoyLog.d("MRAID Display", str);
        this.a.injectMraidJavaScript(str);
    }

    public void logHTML(String html) {
        TapjoyLog.d("MRAID Display", html);
    }

    @Override // com.tapjoy.mraid.controller.Abstract
    public void stopAllListeners() {
        stopConfigurationListener();
        this.g = null;
    }

    public void stopConfigurationListener() {
        try {
            this.b.unregisterReceiver(this.g);
        } catch (Exception e) {
        }
    }

    public void startConfigurationListener() {
        try {
            if (this.g == null) {
                this.g = new ConfigBroadcastReceiver(this);
            }
            this.b.registerReceiver(this.g, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        } catch (Exception e) {
        }
    }
}
