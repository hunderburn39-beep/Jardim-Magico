package com.adcolony.sdk;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ax extends FrameLayout {
    public static final int ADCOLONY_IAP_ENGAGEMENT_END_CARD = 0;
    public static final int ADCOLONY_IAP_ENGAGEMENT_OVERLAY = 1;
    c a;
    c b;
    e c;
    String d;
    String e;
    boolean f;
    boolean g;
    boolean h;
    boolean i;
    ImageView j;
    String k;
    String l;
    String m;
    String n;
    float o;
    private ExecutorService p;

    ax(Context context, z zVar, e eVar) {
        super(context);
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = "";
        this.o = 1.0f;
        this.p = Executors.newSingleThreadExecutor();
        this.c = eVar;
        this.n = eVar.a;
        this.d = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        u.b.a("Retrieving container tied to ad session id: ").b(this.d);
        this.a = a.m.g.a.get(this.d);
        setLayoutParams(new FrameLayout.LayoutParams(this.a.j, this.a.k));
        addView(this.a);
        c();
    }

    public boolean destroy() {
        if (this.g) {
            u.e.b("Ignoring subsequent call to destroy()");
            return false;
        }
        this.g = true;
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.d);
        z zVar = this.h ? new z("AdSession.on_native_ad_view_destroyed", this.a.l, jSONObjectA) : new z("AdSession.on_ad_view_destroyed", this.a.l, jSONObjectA);
        if (a.m.x) {
            a.m.e(zVar);
        } else {
            zVar.b();
        }
        return true;
    }

    void a() {
        a.m.g.a(this.a);
        if (this.b != null) {
            a.m.g.a(this.b);
        }
        f fVarRemove = a.m.g.f.remove(this.d);
        if (fVarRemove != null) {
            for (MediaPlayer mediaPlayer : fVarRemove.c.c.values()) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            }
            fVarRemove.b.i.autoPause();
            fVarRemove.b.i.release();
        }
        a.m.g.e.remove(this.d);
        this.a = null;
        this.c = null;
        removeAllViews();
        this.p.shutdown();
    }

    public boolean setVolume(float volume) {
        if (volume < 0.0d || volume > 1.0d) {
            return false;
        }
        if (this.g) {
            u.e.b("Ignoring call to setVolume as view has been destroyed.");
            return false;
        }
        if (volume > 0.0d) {
            this.o = volume;
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.d);
        s.a(jSONObjectA, "volume", volume);
        new z(this.h ? "AdSession.on_native_ad_view_set_volume" : "AdSession.on_ad_view_destroyed", this.a.l, jSONObjectA).b();
        return true;
    }

    public boolean setMuted(boolean muted) {
        if (this.g) {
            u.e.b("Ignoring call to setMuted() as view has been destroyed");
            return false;
        }
        if (muted) {
            return setVolume(BitmapDescriptorFactory.HUE_RED);
        }
        return setVolume(this.o);
    }

    public String getZoneID() {
        if (!this.g) {
            return this.n;
        }
        u.e.b("Ignoring call to getZoneID() as view has been destroyed");
        return "";
    }

    public boolean pause() {
        if (this.g) {
            u.e.b("Ignoring call to pause() as view has been destroyed");
            return false;
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.d);
        new z("AdSession.on_manual_pause", this.a.l, jSONObjectA).b();
        return true;
    }

    public boolean resume() {
        if (this.g) {
            u.e.b("Ignoring call to resume() as view has been destroyed");
            return false;
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.d);
        new z("AdSession.on_manual_resume", this.a.l, jSONObjectA).b();
        return true;
    }

    boolean b() {
        if (this.e.equals("") || a.l == null) {
            return false;
        }
        this.j = new ImageView(a.l);
        this.j.setImageBitmap(BitmapFactory.decodeFile(this.e));
        return true;
    }

    private void c() {
        try {
            this.p.submit(new Runnable() { // from class: com.adcolony.sdk.ax.1
                @Override // java.lang.Runnable
                public void run() {
                    JSONObject jSONObjectA = s.a();
                    s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, ax.this.d);
                    while (!ax.this.g) {
                        Rect rect = new Rect();
                        Rect rect2 = new Rect();
                        ax.this.getLocalVisibleRect(rect);
                        ax.this.getGlobalVisibleRect(rect2);
                        ViewParent parent = ax.this.getParent();
                        if (parent != null) {
                            parent.getChildVisibleRect(ax.this, rect2, null);
                        }
                        boolean z = rect.bottom - rect.top > ax.this.a.k / 2;
                        boolean z2 = (rect2.bottom - rect2.top < ax.this.a.k / 2 || rect2.bottom - rect2.top >= ax.this.a.k) && ax.this.i;
                        boolean z3 = rect.bottom > ax.this.a.k || rect.bottom < 0 || rect.top < 0 || rect2.top <= 0;
                        if (!z3 && !z2 && z && !ax.this.f) {
                            ax.this.i = true;
                            ax.this.f = true;
                            new z(ax.this.h ? "AdSession.on_native_ad_view_visible" : "AdSession.on_ad_view_visible", ax.this.a.l, jSONObjectA).b();
                        } else if ((!z || (z && z3)) && ax.this.f) {
                            ax.this.f = false;
                            new z(ax.this.h ? "AdSession.on_native_ad_view_hidden" : "AdSession.on_ad_view_hidden", ax.this.a.l, jSONObjectA).b();
                            u.d.b("AdColonyAdView has been hidden.");
                        }
                        try {
                            Thread.sleep(50L);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            JSONObject jSONObjectA = s.a();
            s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.d);
            new z("AdSession.on_error", this.a.l, jSONObjectA).b();
        }
    }
}
