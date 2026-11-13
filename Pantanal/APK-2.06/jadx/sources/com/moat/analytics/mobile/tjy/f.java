package com.moat.analytics.mobile.tjy;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class f {
    protected static final MoatAdEventType[] b = {MoatAdEventType.AD_EVT_FIRST_QUARTILE, MoatAdEventType.AD_EVT_MID_POINT, MoatAdEventType.AD_EVT_THIRD_QUARTILE};
    protected boolean a;
    protected final Map c;
    protected final Handler d;
    protected Map e;
    protected WeakReference f;
    protected WeakReference g;
    protected final a h;
    protected final ap i;
    private boolean j;
    private WeakReference k;
    private ad l;

    public f(String str, a aVar, ap apVar) {
        this.i = apVar;
        this.h = aVar;
        a("Initializing.");
        this.l = new ad(str, apVar, aVar);
        this.c = new HashMap();
        this.d = new Handler();
        this.k = new WeakReference(aVar.c());
        this.j = false;
        this.a = false;
    }

    private int a(AudioManager audioManager) {
        return audioManager.getStreamVolume(3);
    }

    private MoatAdEvent a(Map map) {
        return new MoatAdEvent(MoatAdEventType.fromString((String) map.get("type")), map.containsKey("playHead") ? (Integer) map.get("playHead") : MoatAdEvent.TIME_UNAVAILABLE, map.containsKey(MoatAdEvent.EVENT_AD_VOLUME) ? (Double) map.get(MoatAdEvent.EVENT_AD_VOLUME) : MoatAdEvent.VOLUME_UNAVAILABLE);
    }

    private void b(MoatAdEvent moatAdEvent) {
        JSONObject jSONObjectA = a(moatAdEvent);
        a(String.format("Received event: %s", jSONObjectA.toString()));
        this.l.a(jSONObjectA);
        MoatAdEventType moatAdEventType = moatAdEvent.eventType;
        if (moatAdEventType == MoatAdEventType.AD_EVT_COMPLETE || moatAdEventType == MoatAdEventType.AD_EVT_STOPPED || moatAdEventType == MoatAdEventType.AD_EVT_SKIPPED) {
            this.c.put(moatAdEventType, 1);
            c();
        }
    }

    protected abstract Map a();

    protected JSONObject a(MoatAdEvent moatAdEvent) {
        if (Double.isNaN(moatAdEvent.adVolume.doubleValue())) {
            try {
                moatAdEvent.adVolume = Double.valueOf(d());
            } catch (Exception e) {
                moatAdEvent.adVolume = Double.valueOf(1.0d);
            }
        }
        return new JSONObject(moatAdEvent.toMap());
    }

    protected void a(String str) {
        if (this.i.b() || this.a) {
            Log.d("MoatVideoTracker", str);
        }
    }

    protected boolean a(Integer num, Integer num2) {
        return ((double) (num2.intValue() - num.intValue())) <= Math.min(750.0d, ((double) num2.intValue()) * 0.05d);
    }

    public boolean a(Map map, Object obj, View view) {
        boolean z = true;
        boolean z2 = false;
        if (map == null) {
            try {
                a("trackVideoAd received null adIds object. Not tracking.");
                z = false;
            } catch (Exception e) {
                com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            }
        }
        if (view == null) {
            a("trackVideoAd received null video view instance");
        }
        if (obj == null) {
            a("trackVideoAd received null ad instance. Not tracking.");
            z = false;
        }
        if (z) {
            Object[] objArr = new Object[3];
            objArr[0] = new JSONObject(map).toString();
            objArr[1] = obj.toString();
            objArr[2] = view != null ? view.getClass().getSimpleName() + "@" + view.hashCode() : "null";
            a(String.format("trackVideoAd tracking ids: %s | ad: %s | view: %s", objArr));
            this.e = map;
            this.f = new WeakReference(obj);
            this.g = new WeakReference(view);
            b();
        }
        z2 = z;
        a("Attempt to start tracking ad was " + (z2 ? "" : "un") + "successful.");
        return z2;
    }

    protected void b() {
        Map mapA = a();
        Integer num = (Integer) mapA.get("width");
        Integer num2 = (Integer) mapA.get("height");
        Integer num3 = (Integer) mapA.get("duration");
        a(String.format("Player metadata: height = %d, width = %d, duration = %d", num2, num, num3));
        this.l.a((View) this.g.get(), this.e, num, num2, num3);
    }

    protected void c() {
        if (this.j) {
            return;
        }
        this.d.postDelayed(new g(this), 500L);
        this.j = true;
    }

    public void changeTargetView(View view) {
        if (this.i.b()) {
            Log.d("MoatVideoTracker", "changing view to " + (view != null ? view.getClass().getSimpleName() + "@" + view.hashCode() : "null"));
        }
        this.g = new WeakReference(view);
        this.l.a(view);
    }

    protected double d() {
        AudioManager audioManager = (AudioManager) ((Context) this.k.get()).getSystemService("audio");
        return ((double) a(audioManager)) / ((double) audioManager.getStreamMaxVolume(3));
    }

    public void dispatchEvent(MoatAdEvent moatAdEvent) {
        try {
            b(moatAdEvent);
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }

    public void dispatchEvent(Map map) {
        try {
            b(a(map));
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
    }

    protected boolean e() {
        return this.c.containsKey(MoatAdEventType.AD_EVT_COMPLETE) || this.c.containsKey(MoatAdEventType.AD_EVT_STOPPED) || this.c.containsKey(MoatAdEventType.AD_EVT_SKIPPED);
    }

    public void setDebug(boolean z) {
        this.a = z;
    }
}
