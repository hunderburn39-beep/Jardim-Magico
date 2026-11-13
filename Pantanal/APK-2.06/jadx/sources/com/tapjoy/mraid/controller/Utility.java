package com.tapjoy.mraid.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.webkit.JavascriptInterface;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.view.MraidView;

/* JADX INFO: loaded from: classes.dex */
@TargetApi(14)
public class Utility extends Abstract {
    private Assets c;
    private Display d;
    private MraidLocation e;
    private Network f;
    private MraidSensor g;

    public Utility(MraidView adView, Context context) {
        super(adView, context);
        this.c = new Assets(adView, context);
        this.d = new Display(adView, context);
        this.e = new MraidLocation(adView, context);
        this.f = new Network(adView, context);
        this.g = new MraidSensor(adView, context);
        adView.addJavascriptInterface(this.c, "MRAIDAssetsControllerBridge");
        adView.addJavascriptInterface(this.d, "MRAIDDisplayControllerBridge");
        adView.addJavascriptInterface(this.e, "MRAIDLocationControllerBridge");
        adView.addJavascriptInterface(this.f, "MRAIDNetworkControllerBridge");
        adView.addJavascriptInterface(this.g, "MRAIDSensorControllerBridge");
    }

    public void init(float density) {
        StringBuilder sbAppend = new StringBuilder("window.mraidview.fireChangeEvent({ state: 'default', network: '").append(this.f.getNetwork()).append("', size: ").append(this.d.getSize()).append(", placement: '").append(this.a.getPlacementType()).append("', maxSize: ").append(this.d.getMaxSize()).append(",expandProperties: ").append(this.d.getMaxSize()).append(", screenSize: ").append(this.d.getScreenSize()).append(", defaultPosition: { x:").append((int) (this.a.getLeft() / density)).append(", y: ").append((int) (this.a.getTop() / density)).append(", width: ").append((int) (this.a.getWidth() / density)).append(", height: ").append((int) (this.a.getHeight() / density)).append(" }, orientation:").append(this.d.getOrientation()).append(",");
        String str = (((this.e.allowLocationServices() && (this.b.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0 || this.b.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) ? "supports: [ 'level-1', 'level-2', 'screen', 'orientation', 'network', 'location'" : "supports: [ 'level-1', 'level-2', 'screen', 'orientation', 'network'") + ", 'video'") + ", 'audio'") + ", 'map' ]";
        TapjoyLog.d("MRAID Utility", "getSupports: " + str);
        String string = sbAppend.append(str).append(",viewable:true });").toString();
        TapjoyLog.d("MRAID Utility", "init: injection: " + string);
        this.a.injectMraidJavaScript(string);
        fireReadyEvent();
        fireViewableChange(true);
    }

    public void fireReadyEvent() {
        this.a.injectMraidJavaScript("mraid.signalReady();");
    }

    public void fireViewableChange(boolean visible) {
        this.a.injectMraidJavaScript("window.mraidview.fireChangeEvent({viewable:" + visible + "});");
    }

    public String copyTextFromJarIntoAssetDir(String alias, String source) {
        return this.c.copyTextFromJarIntoAssetDir(alias, source);
    }

    public void setMaxSize(int w, int h) {
        this.d.setMaxSize(w, h);
    }

    @JavascriptInterface
    public void activate(String event) {
        TapjoyLog.d("MRAID Utility", "activate: " + event);
        if (event.equalsIgnoreCase(Defines.Events.NETWORK_CHANGE)) {
            this.f.startNetworkListener();
            return;
        }
        if (this.e.allowLocationServices() && event.equalsIgnoreCase(Defines.Events.LOCATION_CHANGE)) {
            this.e.startLocationListener();
            return;
        }
        if (event.equalsIgnoreCase(Defines.Events.SHAKE)) {
            this.g.startShakeListener();
            return;
        }
        if (event.equalsIgnoreCase(Defines.Events.TILT_CHANGE)) {
            this.g.startTiltListener();
        } else if (event.equalsIgnoreCase(Defines.Events.HEADING_CHANGE)) {
            this.g.startHeadingListener();
        } else if (event.equalsIgnoreCase(Defines.Events.ORIENTATION_CHANGE)) {
            this.d.startConfigurationListener();
        }
    }

    @JavascriptInterface
    public void deactivate(String event) {
        TapjoyLog.d("MRAID Utility", "deactivate: " + event);
        if (event.equalsIgnoreCase(Defines.Events.NETWORK_CHANGE)) {
            this.f.stopNetworkListener();
            return;
        }
        if (event.equalsIgnoreCase(Defines.Events.LOCATION_CHANGE)) {
            this.e.stopAllListeners();
            return;
        }
        if (event.equalsIgnoreCase(Defines.Events.SHAKE)) {
            this.g.stopShakeListener();
            return;
        }
        if (event.equalsIgnoreCase(Defines.Events.TILT_CHANGE)) {
            this.g.stopTiltListener();
        } else if (event.equalsIgnoreCase(Defines.Events.HEADING_CHANGE)) {
            this.g.stopHeadingListener();
        } else if (event.equalsIgnoreCase(Defines.Events.ORIENTATION_CHANGE)) {
            this.d.stopConfigurationListener();
        }
    }

    public void deleteOldAds() {
        this.c.deleteOldAds();
    }

    @Override // com.tapjoy.mraid.controller.Abstract
    public void stopAllListeners() {
        try {
            this.c.stopAllListeners();
            this.d.stopAllListeners();
            this.e.stopAllListeners();
            this.f.stopAllListeners();
            this.g.stopAllListeners();
        } catch (Exception e) {
        }
    }

    @JavascriptInterface
    public void showAlert(String message) {
        TapjoyLog.e("MRAID Utility", message);
    }
}
