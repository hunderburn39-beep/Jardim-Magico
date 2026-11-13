package com.adcolony.sdk;

import android.content.Intent;
import android.view.View;
import com.google.android.gms.plus.PlusShare;
import com.tapjoy.mraid.controller.Abstract;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class d {
    HashMap<String, c> a;
    ConcurrentHashMap<String, AdColonyInterstitial> b;
    HashMap<String, ay> c;
    HashMap<String, AdColonyNativeAdViewListener> d;
    HashMap<String, ax> e;
    HashMap<String, f> f;

    d() {
    }

    boolean a(final z zVar) {
        final String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        final ay ayVarRemove = this.c.remove(strB);
        final AdColonyNativeAdViewListener adColonyNativeAdViewListenerRemove = this.d.remove(strB);
        if (ayVarRemove == null && adColonyNativeAdViewListenerRemove == null) {
            a(zVar.a, strB);
            return false;
        }
        if (a.l == null || a.l == null) {
            return false;
        }
        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.1
            @Override // java.lang.Runnable
            public void run() {
                ax adColonyNativeAdView;
                if (ayVarRemove != null) {
                    adColonyNativeAdView = new ax(a.l, zVar, ayVarRemove);
                    d.this.e.put(strB, adColonyNativeAdView);
                } else {
                    adColonyNativeAdView = new AdColonyNativeAdView(a.l, zVar, adColonyNativeAdViewListenerRemove);
                    d.this.e.put(strB, adColonyNativeAdView);
                }
                adColonyNativeAdView.k = s.b(zVar.b, "name");
                adColonyNativeAdView.l = s.b(zVar.b, "title");
                adColonyNativeAdView.m = s.b(zVar.b, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
                adColonyNativeAdView.e = s.b(zVar.b, "thumb_filepath");
                adColonyNativeAdView.b();
                if (ayVarRemove != null) {
                    ayVarRemove.a(adColonyNativeAdView);
                } else {
                    adColonyNativeAdViewListenerRemove.onRequestFilled((AdColonyNativeAdView) adColonyNativeAdView);
                }
            }
        });
        return true;
    }

    boolean b(z zVar) {
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        final ay ayVarRemove = this.c.remove(strB);
        final AdColonyNativeAdViewListener adColonyNativeAdViewListenerRemove = this.d.remove(strB);
        if (ayVarRemove == null && adColonyNativeAdViewListenerRemove == null) {
            a(zVar.a, strB);
            return false;
        }
        if (a.l == null) {
            return false;
        }
        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.12
            @Override // java.lang.Runnable
            public void run() {
                boolean z = ayVarRemove == null;
                String str = z ? adColonyNativeAdViewListenerRemove.a : ayVarRemove.a;
                AdColonyZone adColonyZone = a.m.z.get(str);
                if (adColonyZone == null) {
                    adColonyZone = new AdColonyZone(str);
                    adColonyZone.l = 6;
                }
                if (z) {
                    adColonyNativeAdViewListenerRemove.onRequestNotFilled(adColonyZone);
                } else {
                    ayVarRemove.a(adColonyZone);
                }
            }
        });
        return true;
    }

    void a() {
        this.a = new HashMap<>();
        this.b = new ConcurrentHashMap<>();
        this.c = new HashMap<>();
        this.d = new HashMap<>();
        this.e = new HashMap<>();
        this.f = new HashMap<>();
        a.a("AdContainer.create", new ac() { // from class: com.adcolony.sdk.d.23
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.j(zVar);
            }
        });
        a.a("AdContainer.destroy", new ac() { // from class: com.adcolony.sdk.d.26
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.k(zVar);
            }
        });
        a.a("AdContainer.move_view_to_index", new ac() { // from class: com.adcolony.sdk.d.27
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.l(zVar);
            }
        });
        a.a("AdContainer.move_view_to_front", new ac() { // from class: com.adcolony.sdk.d.28
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.m(zVar);
            }
        });
        a.a("AdSession.finish_fullscreen_ad", new ac() { // from class: com.adcolony.sdk.d.29
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.i(zVar);
            }
        });
        a.a("AdSession.start_fullscreen_ad", new ac() { // from class: com.adcolony.sdk.d.30
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.h(zVar);
            }
        });
        a.a("AdSession.native_ad_view_available", new ac() { // from class: com.adcolony.sdk.d.31
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.a(zVar);
            }
        });
        a.a("AdSession.native_ad_view_unavailable", new ac() { // from class: com.adcolony.sdk.d.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.b(zVar);
            }
        });
        a.a("AdSession.expiring", new ac() { // from class: com.adcolony.sdk.d.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.c(zVar);
            }
        });
        a.a("AudioPlayer.create", new ac() { // from class: com.adcolony.sdk.d.4
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.n(zVar);
            }
        });
        a.a("AudioPlayer.destroy", new ac() { // from class: com.adcolony.sdk.d.5
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (d.this.g(zVar)) {
                    d.this.o(zVar);
                }
            }
        });
        a.a("AudioPlayer.play", new ac() { // from class: com.adcolony.sdk.d.6
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (d.this.g(zVar)) {
                    d.this.p(zVar);
                }
            }
        });
        a.a("AudioPlayer.pause", new ac() { // from class: com.adcolony.sdk.d.7
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (d.this.g(zVar)) {
                    d.this.q(zVar);
                }
            }
        });
        a.a("AudioPlayer.stop", new ac() { // from class: com.adcolony.sdk.d.8
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (d.this.g(zVar)) {
                    d.this.r(zVar);
                }
            }
        });
        a.a("AdSession.interstitial_available", new ac() { // from class: com.adcolony.sdk.d.9
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.e(zVar);
            }
        });
        a.a("AdSession.interstitial_unavailable", new ac() { // from class: com.adcolony.sdk.d.10
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.f(zVar);
            }
        });
        a.a("AdSession.has_audio", new ac() { // from class: com.adcolony.sdk.d.11
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                d.this.d(zVar);
            }
        });
        a.a("WebView.prepare", new ac() { // from class: com.adcolony.sdk.d.13
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                JSONObject jSONObjectA = s.a();
                s.a(jSONObjectA, "success", true);
                zVar.a(jSONObjectA).b();
            }
        });
        a.a("AdSession.iap_event", new ac() { // from class: com.adcolony.sdk.d.14
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                switch (s.c(zVar.b, "type")) {
                    case 2:
                        ax axVar = d.this.e.get(s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        JSONObject jSONObjectF = s.f(zVar.b, "v4iap");
                        JSONArray jSONArrayG = s.g(jSONObjectF, "product_ids");
                        if (axVar != null && jSONObjectF != null && jSONArrayG.length() > 0) {
                            ((AdColonyNativeAdViewListener) axVar.c).onIAPEvent((AdColonyNativeAdView) axVar, s.b(jSONArrayG, 0), s.c(jSONObjectF, "engagement_type"));
                            break;
                        }
                        break;
                }
            }
        });
        a.a("AdSession.native_ad_view_finished", new ac() { // from class: com.adcolony.sdk.d.15
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                au.a(new Runnable() { // from class: com.adcolony.sdk.d.15.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ax axVar = d.this.e.get(s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        if (axVar != null && axVar.c != null && (axVar instanceof AdColonyNativeAdView)) {
                            ((AdColonyNativeAdViewListener) axVar.c).onNativeVideoFinished((AdColonyNativeAdView) axVar);
                        }
                    }
                });
            }
        });
        a.a("AdSession.native_ad_view_started", new ac() { // from class: com.adcolony.sdk.d.16
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                au.a(new Runnable() { // from class: com.adcolony.sdk.d.16.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ax axVar = d.this.e.get(s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        if (axVar != null && axVar.c != null && (axVar instanceof AdColonyNativeAdView)) {
                            ((AdColonyNativeAdViewListener) axVar.c).onNativeVideoStarted((AdColonyNativeAdView) axVar);
                        }
                    }
                });
            }
        });
        a.a("AdSession.destroy_native_ad_view", new ac() { // from class: com.adcolony.sdk.d.17
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                au.a(new Runnable() { // from class: com.adcolony.sdk.d.17.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ax axVar = d.this.e.get(s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        if (axVar != null) {
                            axVar.a();
                            zVar.a(zVar.b).b();
                        }
                    }
                });
            }
        });
        a.a("AdSession.expanded", new ac() { // from class: com.adcolony.sdk.d.18
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                au.a(new Runnable() { // from class: com.adcolony.sdk.d.18.1
                    @Override // java.lang.Runnable
                    public void run() {
                        zVar.a(zVar.b).b();
                    }
                });
            }
        });
        a.a("AdSession.native_ad_muted", new ac() { // from class: com.adcolony.sdk.d.19
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                au.a(new Runnable() { // from class: com.adcolony.sdk.d.19.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ax axVar = d.this.e.get(s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID));
                        boolean zD = s.d(zVar.b, "muted");
                        if ((axVar instanceof AdColonyNativeAdView) && axVar.c != null) {
                            if (zD) {
                                ((AdColonyNativeAdViewListener) axVar.c).onMuted((AdColonyNativeAdView) axVar);
                                return;
                            } else {
                                ((AdColonyNativeAdViewListener) axVar.c).onUnmuted((AdColonyNativeAdView) axVar);
                                return;
                            }
                        }
                        if (axVar == null || axVar.c == null) {
                        }
                    }
                });
            }
        });
    }

    boolean c(z zVar) {
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        switch (s.c(zVar.b, "type")) {
            case 0:
                u.b.b("Removing ad 1");
                final AdColonyInterstitial adColonyInterstitialRemove = this.b.remove(strB);
                if (adColonyInterstitialRemove == null || adColonyInterstitialRemove.a == null) {
                    a(zVar.a, strB);
                    return false;
                }
                if (a.l == null) {
                    return false;
                }
                a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.20
                    @Override // java.lang.Runnable
                    public void run() {
                        adColonyInterstitialRemove.h = true;
                        adColonyInterstitialRemove.a.onExpiring(adColonyInterstitialRemove);
                        if (a.m != null && a.m.h != null && a.m.h.b != null) {
                            a.m.h.b.dismiss();
                            a.m.h.b = null;
                        }
                    }
                });
                break;
            default:
                return true;
        }
    }

    boolean d(z zVar) {
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, strB);
        if (a.l == null) {
            s.a(jSONObjectA, "has_audio", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
        boolean zA = au.a(au.a(a.l));
        s.a(jSONObjectA, "has_audio", zA);
        zVar.a(jSONObjectA).b();
        return zA;
    }

    boolean e(z zVar) {
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        final AdColonyInterstitial adColonyInterstitial = this.b.get(strB);
        if (adColonyInterstitial == null || adColonyInterstitial.a == null) {
            a(zVar.a, strB);
            return false;
        }
        if (a.l == null) {
            return false;
        }
        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.21
            @Override // java.lang.Runnable
            public void run() {
                adColonyInterstitial.a.onRequestFilled(adColonyInterstitial);
            }
        });
        return true;
    }

    boolean f(z zVar) {
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        u.b.b("Removing ad 2");
        final AdColonyInterstitial adColonyInterstitialRemove = this.b.remove(strB);
        if (adColonyInterstitialRemove == null || adColonyInterstitialRemove.a == null) {
            a(zVar.a, strB);
            return false;
        }
        if (a.l == null) {
            return false;
        }
        a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.22
            @Override // java.lang.Runnable
            public void run() {
                AdColonyZone adColonyZone = a.m.z.get(adColonyInterstitialRemove.getZoneID());
                if (adColonyZone == null) {
                    adColonyZone = new AdColonyZone(adColonyInterstitialRemove.getZoneID());
                    adColonyZone.l = 6;
                }
                adColonyInterstitialRemove.a.onRequestNotFilled(adColonyZone);
            }
        });
        return true;
    }

    boolean g(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        c cVar = this.a.get(strB);
        f fVar = this.f.get(strB);
        if (cVar != null && fVar != null) {
            return true;
        }
        u.g.b("Invalid AudioPlayer message!");
        return false;
    }

    void a(String str, AdColonyNativeAdViewListener adColonyNativeAdViewListener, AdColonyAdSize adColonyAdSize, AdColonyAdOptions adColonyAdOptions) {
        float fJ = a.m.m.j();
        String strB = au.b();
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "zone_id", str);
        s.b(jSONObjectA, "type", 2);
        s.b(jSONObjectA, "width", (int) (adColonyAdSize.a * fJ));
        s.b(jSONObjectA, "height", (int) (fJ * adColonyAdSize.b));
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, strB);
        adColonyNativeAdViewListener.a = str;
        this.d.put(strB, adColonyNativeAdViewListener);
        new z("AdSession.on_request", 1, jSONObjectA).b();
    }

    void a(String str, ay ayVar, AdColonyAdSize adColonyAdSize) {
    }

    void a(String str, AdColonyInterstitialListener adColonyInterstitialListener, AdColonyAdOptions adColonyAdOptions) {
        String strB = au.b();
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "zone_id", str);
        s.a(jSONObjectA, Abstract.FULL_SCREEN, true);
        s.b(jSONObjectA, "width", a.m.m.l());
        s.b(jSONObjectA, "height", a.m.m.m());
        s.b(jSONObjectA, "type", 0);
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, strB);
        u.b.a("AdSession request with id = ").b(strB);
        AdColonyInterstitial adColonyInterstitial = new AdColonyInterstitial(strB, adColonyInterstitialListener, str);
        this.b.put(strB, adColonyInterstitial);
        if (adColonyAdOptions != null && adColonyAdOptions.d != null) {
            adColonyInterstitial.c = adColonyAdOptions;
            s.a(jSONObjectA, "options", adColonyAdOptions.d);
        }
        u.a.b("Requesting AdColony interstitial advertisement.");
        new z("AdSession.on_request", 1, jSONObjectA).b();
    }

    boolean h(z zVar) {
        if (a.l == null) {
            return false;
        }
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        AdColonyInterstitial adColonyInterstitial = this.b.get(strB);
        ax axVar = this.e.get(strB);
        int iA = s.a(zVar.b, "orientation", -1);
        boolean z = axVar != null;
        if (adColonyInterstitial == null && !z) {
            a(zVar.a, strB);
            return false;
        }
        s.a(s.a(), TMXConstants.TAG_TILE_ATTRIBUTE_ID, strB);
        if (adColonyInterstitial != null) {
            adColonyInterstitial.f = s.c(zVar.b, "module_id");
            adColonyInterstitial.d = iA;
            adColonyInterstitial.a();
        } else if (z) {
            a.m.q = axVar.b;
            a.m.r = axVar;
            a.l.startActivity(new Intent(a.l, (Class<?>) AdColonyAdViewActivity.class));
        }
        return true;
    }

    boolean i(z zVar) {
        int iC = s.c(zVar.b, "status");
        if (iC == 5 || iC == 1 || iC == 0 || iC == 6) {
            return false;
        }
        String strB = s.b(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID);
        u.b.b("Removing ad 3");
        final AdColonyInterstitial adColonyInterstitialRemove = this.b.remove(strB);
        if (adColonyInterstitialRemove == null) {
            a(zVar.a, strB);
            return false;
        }
        final AdColonyInterstitialListener adColonyInterstitialListener = adColonyInterstitialRemove.a;
        u.b.b("Ad attempt finished. Attempting to contact ad listener.");
        if (adColonyInterstitialListener != null && a.l != null) {
            a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.24
                @Override // java.lang.Runnable
                public void run() {
                    a.m.D = false;
                    adColonyInterstitialListener.onClosed(adColonyInterstitialRemove);
                }
            });
        }
        adColonyInterstitialRemove.b = null;
        return true;
    }

    boolean j(z zVar) {
        if (a.l == null) {
            return false;
        }
        String strB = s.b(zVar.b, "ad_session_id");
        c cVar = new c(a.l, strB);
        cVar.b(zVar);
        if (this.a.containsKey(strB)) {
            ax axVar = this.e.get(strB);
            if (axVar == null) {
                return false;
            }
            axVar.b = cVar;
            return true;
        }
        u.b.a("Inserting container into hash map tied to ad session id: ").b(strB);
        this.a.put(strB, cVar);
        if (s.c(zVar.b, "width") == 0) {
            if (this.b.get(strB) == null) {
                a(zVar.a, strB);
                return false;
            }
            this.b.get(strB).a(cVar);
        } else {
            cVar.q = false;
        }
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, "success", true);
        zVar.a(jSONObjectA).b();
        return true;
    }

    boolean k(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        c cVar = this.a.get(strB);
        if (cVar == null) {
            a(zVar.a, strB);
            return false;
        }
        a(cVar);
        return true;
    }

    void a(final c cVar) {
        if (a.l != null) {
            a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.d.25
                @Override // java.lang.Runnable
                public void run() {
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= cVar.o.size()) {
                            break;
                        }
                        a.b(cVar.p.get(i2), cVar.o.get(i2));
                        i = i2 + 1;
                    }
                    cVar.p.clear();
                    cVar.o.clear();
                    cVar.removeAllViews();
                    cVar.u = null;
                    cVar.t = null;
                    u.d.a("Destroying container tied to ad_session_id = ").b(cVar.m);
                    Iterator<o> it = cVar.c.values().iterator();
                    while (it.hasNext()) {
                        it.next().b();
                    }
                    for (aw awVar : cVar.d.values()) {
                        if (!awVar.b) {
                            a.m.a(awVar.a);
                            awVar.loadUrl("about:blank");
                            awVar.clearCache(true);
                            awVar.removeAllViews();
                            awVar.c = true;
                        }
                    }
                    u.d.a("Stopping and releasing all media players associated with VideoViews tied to ad_session_id = ").b(cVar.m);
                    for (av avVar : cVar.a.values()) {
                        avVar.c = true;
                        avVar.d = null;
                    }
                    cVar.a.clear();
                    cVar.b.clear();
                    cVar.d.clear();
                    cVar.c.clear();
                    cVar.g.clear();
                    cVar.i.clear();
                    cVar.f.clear();
                    cVar.h.clear();
                }
            });
            ax axVar = this.e.get(cVar.m);
            if (axVar == null || axVar.g) {
                u.b.b("Removing ad 4");
                this.a.remove(cVar.m);
                cVar.t = null;
            }
        }
    }

    void a(String str, String str2) {
        u.g.a("Message '").a(str).a("' sent with invalid id: ").b(str2);
    }

    boolean l(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        int iC = s.c(zVar.b, "view_id");
        c cVar = this.a.get(strB);
        View view = cVar.i.get(Integer.valueOf(iC));
        if (cVar == null) {
            a(zVar.a, strB);
            return false;
        }
        if (view == null) {
            a(zVar.a, "" + iC);
            return false;
        }
        view.bringToFront();
        return true;
    }

    boolean m(z zVar) {
        ax axVar;
        String strB = s.b(zVar.b, "ad_session_id");
        int iC = s.c(zVar.b, "view_id");
        c cVar = this.a.get(strB);
        if (cVar == null) {
            a(zVar.a, strB);
            return false;
        }
        c cVar2 = (cVar.n != 0 || s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID) != 1 || (axVar = this.e.get(strB)) == null || axVar.b == null) ? cVar : axVar.b;
        View view = cVar2.i.get(Integer.valueOf(iC));
        if (view == null) {
            a(zVar.a, "" + iC);
            return false;
        }
        cVar2.removeView(view);
        cVar2.addView(view, view.getLayoutParams());
        return true;
    }

    boolean n(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        c cVar = this.a.get(strB);
        if (cVar == null) {
            a(zVar.a, strB);
            return false;
        }
        f fVar = this.f.get(strB);
        if (fVar == null) {
            fVar = new f(strB, cVar.l);
            this.f.put(strB, fVar);
        }
        fVar.a(zVar);
        return true;
    }

    boolean o(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        f fVar = this.f.get(strB);
        if (fVar == null) {
            a(zVar.a, strB);
            return false;
        }
        fVar.d(zVar);
        return true;
    }

    boolean p(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        f fVar = this.f.get(strB);
        if (fVar == null) {
            a(zVar.a, strB);
            return false;
        }
        fVar.c(zVar);
        return true;
    }

    boolean q(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        f fVar = this.f.get(strB);
        if (fVar == null) {
            a(zVar.a, strB);
            return false;
        }
        fVar.b(zVar);
        return true;
    }

    boolean r(z zVar) {
        String strB = s.b(zVar.b, "ad_session_id");
        f fVar = this.f.get(strB);
        if (fVar == null) {
            a(zVar.a, strB);
            return false;
        }
        fVar.e(zVar);
        return true;
    }
}
