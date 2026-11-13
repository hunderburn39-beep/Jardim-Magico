package com.tapjoy;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.tapjoy.internal.bd;
import com.tapjoy.internal.cf;
import com.tapjoy.internal.cr;
import com.tapjoy.internal.d;
import com.tapjoy.internal.eg;
import com.tapjoy.internal.eq;
import com.tapjoy.internal.eu;
import com.tapjoy.internal.fa;
import com.tapjoy.internal.fd;
import com.tapjoy.internal.fr;
import com.tapjoy.internal.fu;
import com.tapjoy.internal.gb;
import com.tapjoy.internal.gc;
import com.tapjoy.internal.gd;
import com.tapjoy.internal.gl;
import com.tapjoy.internal.gz;
import com.tapjoy.internal.y;
import com.tapjoy.mediation.TJCustomPlacement;
import com.tapjoy.mediation.TJCustomPlacementListener;
import com.tapjoy.mediation.TJMediatedPlacementData;
import com.tapjoy.mediation.TJMediationSettings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TJCorePlacement implements TJCustomPlacementListener {
    static final String a = TJCorePlacement.class.getSimpleName();
    TJPlacementData c;
    String d;
    long e;
    public boolean initiatedBySdk;
    TJCustomPlacement l;
    String m;
    String n;
    String o;
    String p;
    private Map r;
    private TJAdUnit s;
    private TJMediatedPlacementData w;
    private Handler x;
    private Runnable y;
    private bd q = bd.a();
    final eq f = new eq();
    boolean g = false;
    private boolean t = false;
    private gz u = null;
    gb h = null;
    private volatile boolean v = false;
    volatile boolean i = false;
    volatile boolean j = false;
    volatile boolean k = false;
    private TJAdUnit.TJAdUnitWebViewListener z = new TJAdUnit.TJAdUnitWebViewListener() { // from class: com.tapjoy.TJCorePlacement.1
        @Override // com.tapjoy.TJAdUnit.TJAdUnitWebViewListener
        public final void onContentReady() {
            TJCorePlacement.this.d();
        }

        @Override // com.tapjoy.TJAdUnit.TJAdUnitWebViewListener
        public final void onClosed() {
            if (TJCorePlacement.this.g) {
                TJPlacementManager.decrementPlacementCacheCount();
                TJCorePlacement.c(TJCorePlacement.this);
            }
            if (TJCorePlacement.this.t) {
                TJPlacementManager.decrementPlacementPreRenderCount();
                TJCorePlacement.this.t = false;
            }
        }
    };
    private TJAdUnit.TJAdUnitVideoListener A = new TJAdUnit.TJAdUnitVideoListener() { // from class: com.tapjoy.TJCorePlacement.4
        @Override // com.tapjoy.TJAdUnit.TJAdUnitVideoListener
        public final void onVideoStart() {
            TJPlacement tJPlacementA = TJCorePlacement.this.a("SHOW");
            if (tJPlacementA != null && tJPlacementA.getVideoListener() != null) {
                tJPlacementA.getVideoListener().onVideoStart(tJPlacementA);
            }
        }

        @Override // com.tapjoy.TJAdUnit.TJAdUnitVideoListener
        public final void onVideoCompleted() {
            TJPlacement tJPlacementA = TJCorePlacement.this.a("SHOW");
            if (tJPlacementA != null && tJPlacementA.getVideoListener() != null) {
                tJPlacementA.getVideoListener().onVideoComplete(tJPlacementA);
            }
        }

        @Override // com.tapjoy.TJAdUnit.TJAdUnitVideoListener
        public final void onVideoError(String errorMessage) {
            TJPlacement tJPlacementA = TJCorePlacement.this.a("SHOW");
            if (tJPlacementA != null && tJPlacementA.getVideoListener() != null) {
                tJPlacementA.getVideoListener().onVideoError(tJPlacementA, errorMessage);
            }
        }
    };
    Context b = d.c();

    static /* synthetic */ boolean c(TJCorePlacement tJCorePlacement) {
        tJCorePlacement.g = false;
        return false;
    }

    static /* synthetic */ boolean l(TJCorePlacement tJCorePlacement) {
        tJCorePlacement.v = false;
        return false;
    }

    TJCorePlacement(String placementName, String placementKey) {
        if (this.b == null) {
            TapjoyLog.d(a, "getVisibleActivity() is NULL. Activity can be explicitly set via `Tapjoy.setActivity(Activity)`");
        }
        this.c = new TJPlacementData(placementKey, getPlacementContentUrl());
        this.c.setPlacementName(placementName);
        this.d = UUID.randomUUID().toString();
        this.s = new TJAdUnit();
        this.s.setWebViewListener(this.z);
        this.s.setVideoListener(this.A);
        this.y = new Runnable() { // from class: com.tapjoy.TJCorePlacement.5
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    TapjoyLog.i(TJCorePlacement.a, "Custom placement adapter request timed out");
                    TJCorePlacement.this.i();
                } catch (TapjoyException e) {
                    TJCorePlacement.this.a(TapjoyErrorMessage.ErrorType.SERVER_ERROR, new TJError(TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL, e.getMessage() + " for placement " + TJCorePlacement.f(TJCorePlacement.this)));
                }
            }
        };
    }

    final synchronized void a() {
        String url = this.c.getUrl();
        if (cr.c(url)) {
            url = getPlacementContentUrl();
            if (cr.c(url)) {
                fa.b("TJPlacement.requestContent").a("TJPlacement is missing APP_ID").c();
                a(TapjoyErrorMessage.ErrorType.SDK_ERROR, new TJError(0, "TJPlacement is missing APP_ID"));
            } else {
                this.c.updateUrl(url);
                TapjoyLog.d(a, "sendContentRequest -- URL: " + url + " name: " + this.c.getPlacementName());
                a(url, (Map) null, true);
            }
        } else {
            TapjoyLog.d(a, "sendContentRequest -- URL: " + url + " name: " + this.c.getPlacementName());
            a(url, (Map) null, true);
        }
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.tapjoy.TJCorePlacement$6] */
    final synchronized void a(final String str, Map map, boolean z) {
        final eg egVar;
        final fd fdVarC;
        String strA = null;
        synchronized (this) {
            if (this.v) {
                TapjoyLog.i(a, "Placement " + this.c.getPlacementName() + " is already requesting content");
                fa.b("TJPlacement.requestContent").b("already doing").c();
            } else {
                this.c.resetPlacementRequestData();
                eq eqVar = this.f;
                eqVar.a = null;
                eqVar.c = null;
                this.v = false;
                this.i = false;
                this.j = false;
                this.k = false;
                this.v = true;
                final TJPlacement tJPlacementA = a("REQUEST");
                this.r = TapjoyConnectCore.getGenericURLParams();
                this.r.putAll(TapjoyConnectCore.getTimeStampAndVerifierParams());
                TapjoyUtil.safePut(this.r, TJAdUnitConstants.PARAM_PLACEMENT_NAME, this.c.getPlacementName(), true);
                TapjoyUtil.safePut(this.r, TJAdUnitConstants.PARAM_PLACEMENT_PRELOAD, "true", true);
                TapjoyUtil.safePut(this.r, TapjoyConstants.TJC_DEBUG, Boolean.toString(fr.a), true);
                fu fuVarA = fu.a();
                Map map2 = this.r;
                if (fuVarA.b != null) {
                    gd gdVar = fuVarA.b;
                    gdVar.b();
                    strA = gdVar.b.a();
                }
                TapjoyUtil.safePut(map2, TJAdUnitConstants.PARAM_ACTION_ID_EXCLUSION, strA, true);
                TapjoyUtil.safePut(this.r, TJAdUnitConstants.PARAM_PLACEMENT_BY_SDK, String.valueOf(this.initiatedBySdk), true);
                TapjoyUtil.safePut(this.r, TJAdUnitConstants.PARAM_PUSH_ID, tJPlacementA.pushId, true);
                TapjoyUtil.safePut(this.r, TapjoyConstants.TJC_MEDIATION_SOURCE, this.m, true);
                TapjoyUtil.safePut(this.r, TapjoyConstants.TJC_ADAPTER_VERSION, this.n, true);
                if (map != null) {
                    this.r.putAll(map);
                }
                this.s.setContext(this.b);
                if (z) {
                    egVar = new eg(eu.b().b("placement_request_content_retry_timeout"));
                    fdVarC = eu.b().c("placement_request_content_retry_backoff");
                } else {
                    egVar = eg.a;
                    fdVarC = fd.a;
                }
                final fa.a aVarD = fa.d("TJPlacement.requestContent");
                new Thread() { // from class: com.tapjoy.TJCorePlacement.6
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        fa.a("TJPlacement.requestContent", aVarD);
                        int i = 0;
                        while (!a()) {
                            i++;
                            TJCorePlacement.this.r.put(TapjoyConstants.TJC_RETRY, Integer.toString(i));
                            if (i == 1) {
                                aVarD.a("retry_timeout", Long.valueOf(egVar.b));
                            }
                            aVarD.a("retry_count", i);
                        }
                    }

                    private boolean a() {
                        TapjoyLog.i(TJCorePlacement.a, "Sending content request for placement " + TJCorePlacement.f(TJCorePlacement.this));
                        TJCorePlacement tJCorePlacement = TJCorePlacement.this;
                        fu fuVarA2 = fu.a();
                        String strF = TJCorePlacement.f(TJCorePlacement.this);
                        Context context = TJCorePlacement.this.b;
                        gc gcVar = fuVarA2.a;
                        gl.n nVarA = gcVar.a.a(false);
                        tJCorePlacement.u = new gz(gcVar.a, nVarA.c, nVarA.d, nVarA.e, strF, context);
                        TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(str, (Map) null, (Map) null, TJCorePlacement.this.r);
                        TJCorePlacement.this.c.setHttpStatusCode(responseFromURL.statusCode);
                        TJCorePlacement.this.c.setHttpResponse(responseFromURL.response);
                        if (!responseFromURL.getHeaderFieldAsString(TapjoyConstants.TAPJOY_PRERENDER_HEADER).equals("0")) {
                            TJCorePlacement.this.c.setPrerenderingRequested(true);
                        }
                        String headerFieldAsString = responseFromURL.getHeaderFieldAsString(TapjoyConstants.TAPJOY_DEBUG_HEADER);
                        if (headerFieldAsString != null) {
                            TapjoyLog.v(TJCorePlacement.a, "Tapjoy-Server-Debug: " + headerFieldAsString);
                        }
                        if (responseFromURL.expires <= 0) {
                            TJCorePlacement.this.e = 0L;
                        } else {
                            long jB = responseFromURL.expires - (responseFromURL.date > 0 ? responseFromURL.date : y.b());
                            if (jB > 0) {
                                TJCorePlacement.this.e = jB + SystemClock.elapsedRealtime();
                            }
                        }
                        if (!cr.c(responseFromURL.getHeaderFieldAsString(TapjoyConstants.TAPJOY_MEDIATION_HEADER))) {
                            try {
                                TJCorePlacement.this.w = new TJMediatedPlacementData(TJCorePlacement.this.c.getHttpResponse());
                                TJCorePlacement.j(TJCorePlacement.this);
                            } catch (TapjoyException e) {
                                String str2 = e.getMessage() + " for placement " + TJCorePlacement.f(TJCorePlacement.this);
                                fa.b("TJPlacement.requestContent").a("mediation error").c();
                                TJCorePlacement.this.a(tJPlacementA, TapjoyErrorMessage.ErrorType.SDK_ERROR, new TJError(TJCorePlacement.this.c.getHttpStatusCode(), str2));
                            }
                        } else if (responseFromURL != null && tJPlacementA.getListener() != null) {
                            switch (responseFromURL.statusCode) {
                                case 0:
                                    if (egVar.a(fdVarC.e)) {
                                        fa.b("TJPlacement.requestContent").a("network error").a("retry_timeout", Long.valueOf(egVar.b)).c();
                                        TJCorePlacement.this.a(tJPlacementA, TapjoyErrorMessage.ErrorType.NETWORK_ERROR, new TJError(responseFromURL.statusCode, responseFromURL.response));
                                    } else {
                                        fd fdVar = fdVarC;
                                        long j = fdVar.e;
                                        long j2 = (long) (fdVar.e * fdVar.d);
                                        if (j2 < fdVar.b) {
                                            j2 = fdVar.b;
                                        } else if (j2 > fdVar.c) {
                                            j2 = fdVar.c;
                                        }
                                        fdVar.e = j2;
                                        if (j > 0) {
                                            synchronized (fdVar) {
                                                try {
                                                    fdVar.wait(j);
                                                    break;
                                                } catch (InterruptedException e2) {
                                                }
                                                break;
                                            }
                                        }
                                        return false;
                                    }
                                    break;
                                case 200:
                                    String headerFieldAsString2 = responseFromURL.getHeaderFieldAsString("Content-Type");
                                    if (!cr.c(headerFieldAsString2) && headerFieldAsString2.contains("json")) {
                                        if (responseFromURL.getHeaderFieldAsString(TapjoyConstants.TAPJOY_DISABLE_PRELOAD_HEADER).equals("1")) {
                                            try {
                                                TJCorePlacement.a(TJCorePlacement.this, responseFromURL.response);
                                                fa.b("TJPlacement.requestContent").a("content_type", "ad").c();
                                                TJCorePlacement.this.j();
                                                TJCorePlacement.this.d();
                                            } catch (TapjoyException e3) {
                                                String str3 = e3.getMessage() + " for placement " + TJCorePlacement.f(TJCorePlacement.this);
                                                fa.b("TJPlacement.requestContent").a("server error").c();
                                                TJCorePlacement.this.a(tJPlacementA, TapjoyErrorMessage.ErrorType.SERVER_ERROR, new TJError(responseFromURL.statusCode, str3));
                                            }
                                        } else if (TJCorePlacement.this.b(responseFromURL.response)) {
                                            fa.b("TJPlacement.requestContent").a("content_type", "mm").c();
                                            TJCorePlacement.this.j();
                                            TJCorePlacement.this.d();
                                        } else {
                                            fa.b("TJPlacement.requestContent").a("asset error").c();
                                            TJCorePlacement.this.a(tJPlacementA, TapjoyErrorMessage.ErrorType.SERVER_ERROR, new TJError(responseFromURL.statusCode, responseFromURL.response));
                                        }
                                    } else {
                                        fa.b("TJPlacement.requestContent").a("content_type", "ad").c();
                                        TJCorePlacement.this.j();
                                        final TJCorePlacement tJCorePlacement2 = TJCorePlacement.this;
                                        final TJCacheListener tJCacheListener = new TJCacheListener() { // from class: com.tapjoy.TJCorePlacement.6.1
                                            @Override // com.tapjoy.TJCacheListener
                                            public final void onCachingComplete(int status) {
                                                TJCorePlacement.this.t = TJCorePlacement.this.getAdUnit().preload(TJCorePlacement.this.c);
                                            }
                                        };
                                        TapjoyLog.i(TJCorePlacement.a, "Checking if there is content to cache for placement " + tJCorePlacement2.c.getPlacementName());
                                        String headerFieldAsString3 = responseFromURL.getHeaderFieldAsString(TapjoyConstants.TAPJOY_CACHE_HEADER);
                                        try {
                                            if (!TJPlacementManager.canCachePlacement()) {
                                                TapjoyLog.i(TJCorePlacement.a, "Placement caching limit reached. No content will be cached for placement " + tJCorePlacement2.c.getPlacementName());
                                                tJCacheListener.onCachingComplete(2);
                                            } else {
                                                JSONArray jSONArray = new JSONArray(headerFieldAsString3);
                                                if (jSONArray.length() > 0) {
                                                    TapjoyLog.i(TJCorePlacement.a, "Begin caching content for placement " + tJCorePlacement2.c.getPlacementName());
                                                    TJPlacementManager.incrementPlacementCacheCount();
                                                    tJCorePlacement2.g = true;
                                                    TapjoyCache.getInstance().cacheAssetGroup(jSONArray, new TJCacheListener() { // from class: com.tapjoy.TJCorePlacement.9
                                                        @Override // com.tapjoy.TJCacheListener
                                                        public final void onCachingComplete(int status) {
                                                            tJCacheListener.onCachingComplete(status);
                                                        }
                                                    });
                                                } else {
                                                    tJCacheListener.onCachingComplete(1);
                                                }
                                            }
                                        } catch (JSONException e4) {
                                            tJCacheListener.onCachingComplete(2);
                                        }
                                    }
                                    break;
                                default:
                                    fa.b("TJPlacement.requestContent").a("content_type", "none").a("code", Integer.valueOf(responseFromURL.statusCode)).c();
                                    TJCorePlacement.this.a(tJPlacementA);
                                    break;
                            }
                        }
                        TJCorePlacement.l(TJCorePlacement.this);
                        return true;
                    }
                }.start();
            }
        }
    }

    final String b() {
        String str = this.o;
        if (str != null) {
            return str;
        }
        return this.initiatedBySdk ? "sdk" : "app";
    }

    private void h() {
        this.x.removeCallbacks(this.y);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() throws TapjoyException {
        TapjoyLog.i(a, "Custom placement call failed, retrying Tapjoy request");
        if (this.w == null) {
            throw new TapjoyException("Mediation data is null");
        }
        JSONObject nextCall = this.w.getNextCall();
        this.l = null;
        this.w = null;
        try {
            a(this.c.getUrl(), TapjoyUtil.jsonToStringMap(nextCall), false);
        } catch (JSONException e) {
            TapjoyLog.i(a, "Failed to load next call parameters for mediated placement " + this.c.getPlacementName());
            throw new TapjoyException("TJPlacement request failed due to custom placement fallback failure");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        try {
            gz.a aVar = (gz.a) this.u.a(URI.create(this.c.getUrl()), new ByteArrayInputStream(str.getBytes()));
            this.h = aVar.a;
            aVar.a.a();
            if (!aVar.a.b()) {
                TapjoyLog.e(a, "Failed to load fiverocks placement");
                return false;
            }
            return true;
        } catch (cf e) {
            TapjoyLog.e(a, e.toString());
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            TapjoyLog.e(a, e2.toString());
            e2.printStackTrace();
            return false;
        }
    }

    public Context getContext() {
        return this.b;
    }

    public void setContext(Context activityContext) {
        this.b = activityContext;
    }

    public TJAdUnit getAdUnit() {
        return this.s;
    }

    public TJPlacementData getPlacementData() {
        return this.c;
    }

    public boolean isContentReady() {
        return this.k;
    }

    public boolean isContentAvailable() {
        return this.j;
    }

    public String getPlacementContentUrl() {
        String appID = TapjoyConnectCore.getAppID();
        return !cr.c(appID) ? TapjoyConnectCore.getPlacementURL() + "v1/apps/" + appID + "/content?" : "";
    }

    final String c() {
        TJMediatedPlacementData tJMediatedPlacementData = this.w;
        if (this.l != null && tJMediatedPlacementData != null) {
            return tJMediatedPlacementData.getName();
        }
        if (this.h != null) {
            return "mm";
        }
        if (this.j) {
            return "ad";
        }
        return "none";
    }

    final void a(String str, TJPlacement tJPlacement) {
        synchronized (this.q) {
            this.q.put(str, tJPlacement);
            if (tJPlacement != null) {
                TapjoyLog.d(a, "Setting " + str + " placement: " + tJPlacement.getGUID());
            }
        }
    }

    final TJPlacement a(String str) {
        TJPlacement tJPlacement;
        synchronized (this.q) {
            tJPlacement = (TJPlacement) this.q.get(str);
            if (tJPlacement != null) {
                TapjoyLog.d(a, "Returning " + str + " placement: " + tJPlacement.getGUID());
            }
        }
        return tJPlacement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        this.j = true;
        a(a("REQUEST"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(TJPlacement tJPlacement) {
        eq eqVar = this.f;
        String placementName = this.c.getPlacementName();
        String strB = b();
        String strC = c();
        eqVar.b = 0;
        eqVar.a = fa.e("PlacementContent.funnel").a().a("placement", placementName).a("placement_type", strB).a("content_type", strC).a("state", Integer.valueOf(eqVar.b));
        eqVar.a.c();
        if (!"none".equals(strC)) {
            eqVar.d = fa.e("PlacementContent.ready").a().a("placement", placementName).a("placement_type", strB).a("content_type", strC);
        }
        if (tJPlacement != null && tJPlacement.getListener() != null) {
            TapjoyLog.i(a, "Content request delivered successfully for placement " + this.c.getPlacementName() + ", contentAvailable: " + isContentAvailable() + ", mediationAgent: " + this.o);
            tJPlacement.getListener().onRequestSuccess(tJPlacement);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(TapjoyErrorMessage.ErrorType errorType, TJError tJError) {
        a(a("REQUEST"), errorType, tJError);
    }

    final void a(TJPlacement tJPlacement, TapjoyErrorMessage.ErrorType errorType, TJError tJError) {
        TapjoyLog.e(a, new TapjoyErrorMessage(errorType, "Content request failed for placement " + this.c.getPlacementName() + "; Reason= " + tJError.message));
        if (tJPlacement != null && tJPlacement.getListener() != null) {
            tJPlacement.getListener().onRequestFailure(tJPlacement, tJError);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void d() {
        if (!this.i) {
            this.k = true;
            TapjoyLog.i(a, "Content is ready for placement " + this.c.getPlacementName());
            eq eqVar = this.f;
            fa.a aVar = eqVar.d;
            if (aVar != null) {
                eqVar.d = null;
                aVar.b().c();
            }
            TJPlacement tJPlacementA = a("REQUEST");
            if (tJPlacementA != null && tJPlacementA.getListener() != null) {
                tJPlacementA.getListener().onContentReady(tJPlacementA);
                this.i = true;
            }
        }
    }

    final void e() {
        TJPlacement tJPlacementA = a("SHOW");
        if (tJPlacementA == null || tJPlacementA.getListener() == null) {
            return;
        }
        TapjoyLog.i(a, "Content dismissed for placement " + this.c.getPlacementName());
        if (tJPlacementA == null || tJPlacementA.a == null) {
            return;
        }
        tJPlacementA.a.onContentDismiss(tJPlacementA);
    }

    final void f() {
        TapjoyLog.i(a, "Content shown for placement " + this.c.getPlacementName());
        this.f.a();
        TJPlacement tJPlacementA = a("SHOW");
        if (tJPlacementA != null && tJPlacementA.getListener() != null) {
            tJPlacementA.getListener().onContentShow(tJPlacementA);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.tapjoy.TJCorePlacement$2] */
    @Override // com.tapjoy.mediation.TJCustomPlacementListener
    public void onCustomPlacementLoad() {
        if (this.l != null) {
            h();
            this.j = true;
            if (this.w != null && this.w.getFillURL() != null) {
                final String fillURL = this.w.getFillURL();
                new Thread() { // from class: com.tapjoy.TJCorePlacement.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        TapjoyLog.d(TJCorePlacement.a, "onCustomPlacementLoad -- fillUrl=" + fillURL);
                        new TapjoyURLConnection().getResponseFromURL(fillURL);
                    }
                }.start();
            }
            fa.b("TJPlacement.requestContent").a("content_type", c()).c();
            j();
            d();
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.tapjoy.TJCorePlacement$3] */
    @Override // com.tapjoy.mediation.TJCustomPlacementListener
    public void onCustomPlacementFailure(Error error) {
        if (this.l != null) {
            h();
            try {
                if (this.w != null && this.w.getNoFillURL() != null) {
                    final String noFillURL = this.w.getNoFillURL();
                    new Thread() { // from class: com.tapjoy.TJCorePlacement.3
                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            TapjoyLog.d(TJCorePlacement.a, "onCustomPlacementFailure -- noFillUrl=" + noFillURL);
                            new TapjoyURLConnection().getResponseFromURL(noFillURL);
                        }
                    }.start();
                }
                i();
            } catch (TapjoyException e) {
                a(TapjoyErrorMessage.ErrorType.SERVER_ERROR, new TJError(TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL, e.getMessage() + " for placement " + this.c.getPlacementName()));
            }
        }
    }

    @Override // com.tapjoy.mediation.TJCustomPlacementListener
    public void onCustomPlacementContentShown() {
        f();
    }

    @Override // com.tapjoy.mediation.TJCustomPlacementListener
    public void onCustomPlacementContentDismiss() {
        this.l = null;
        this.w = null;
        e();
    }

    @Override // com.tapjoy.mediation.TJCustomPlacementListener
    public void onCustomPlacementReward(final String type, int amount) {
        TJPlacement tJPlacementA = a("SHOW");
        if (tJPlacementA == null || tJPlacementA.getListener() == null) {
            return;
        }
        tJPlacementA.getListener().onRewardRequest(tJPlacementA, new TJActionRequest() { // from class: com.tapjoy.TJCorePlacement.11
            @Override // com.tapjoy.TJActionRequest
            public final String getRequestId() {
                return type;
            }

            @Override // com.tapjoy.TJActionRequest
            public final String getToken() {
                return null;
            }

            @Override // com.tapjoy.TJActionRequest
            public final void completed() {
            }

            @Override // com.tapjoy.TJActionRequest
            public final void cancelled() {
            }
        }, type, amount);
    }

    static /* synthetic */ String f(TJCorePlacement tJCorePlacement) {
        return tJCorePlacement.c.getPlacementName();
    }

    static /* synthetic */ void j(TJCorePlacement tJCorePlacement) throws TapjoyException {
        try {
            Class<?> cls = Class.forName(tJCorePlacement.w.getClassname());
            if (TJCustomPlacement.class.isAssignableFrom(cls)) {
                tJCorePlacement.l = (TJCustomPlacement) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
                if (tJCorePlacement.x == null) {
                    tJCorePlacement.x = new Handler(Looper.getMainLooper());
                }
                tJCorePlacement.x.postDelayed(tJCorePlacement.y, TJMediationSettings.getInstance().getTimeout());
                final fa.a aVarD = fa.d("TJPlacement.requestContent");
                tJCorePlacement.l.requestContentWithCustomPlacementParams(tJCorePlacement.b, new TJCustomPlacementListener() { // from class: com.tapjoy.TJCorePlacement.10
                    @Override // com.tapjoy.mediation.TJCustomPlacementListener
                    public final void onCustomPlacementLoad() {
                        fa.a("TJPlacement.requestContent", aVarD);
                        TJCorePlacement.this.onCustomPlacementLoad();
                    }

                    @Override // com.tapjoy.mediation.TJCustomPlacementListener
                    public final void onCustomPlacementFailure(Error error) {
                        fa.a("TJPlacement.requestContent", aVarD);
                        TJCorePlacement.this.onCustomPlacementFailure(error);
                    }

                    @Override // com.tapjoy.mediation.TJCustomPlacementListener
                    public final void onCustomPlacementContentShown() {
                        TJCorePlacement.this.onCustomPlacementContentShown();
                    }

                    @Override // com.tapjoy.mediation.TJCustomPlacementListener
                    public final void onCustomPlacementContentDismiss() {
                        TJCorePlacement.this.onCustomPlacementContentDismiss();
                    }

                    @Override // com.tapjoy.mediation.TJCustomPlacementListener
                    public final void onCustomPlacementReward(String type, int amount) {
                        TJCorePlacement.this.onCustomPlacementReward(type, amount);
                    }
                }, tJCorePlacement.w.getExtras());
                return;
            }
            TapjoyLog.e(a, tJCorePlacement.w.getClassname() + " does not implement TJCustomPlacement.");
            tJCorePlacement.i();
        } catch (Exception e) {
            TapjoyLog.e(a, new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Failed to load custom class " + tJCorePlacement.w.getClassname() + " for placement " + tJCorePlacement.c.getPlacementName()));
            tJCorePlacement.i();
        }
    }

    static /* synthetic */ void a(TJCorePlacement tJCorePlacement, String str) throws TapjoyException {
        if (str != null) {
            try {
                TapjoyLog.d(a, "Disable preload flag is set for placement " + tJCorePlacement.c.getPlacementName());
                tJCorePlacement.c.setRedirectURL(new JSONObject(str).getString(TapjoyConstants.TJC_REDIRECT_URL));
                tJCorePlacement.c.setPreloadDisabled(true);
                tJCorePlacement.c.setHasProgressSpinner(true);
                TapjoyLog.d(a, "redirect_url:" + tJCorePlacement.c.getRedirectURL());
                return;
            } catch (JSONException e) {
                throw new TapjoyException("TJPlacement request failed, malformed server response");
            }
        }
        throw new TapjoyException("TJPlacement request failed due to null response");
    }
}
